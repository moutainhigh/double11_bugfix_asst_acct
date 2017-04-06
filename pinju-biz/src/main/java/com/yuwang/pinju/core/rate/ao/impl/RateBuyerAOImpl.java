package com.yuwang.pinju.core.rate.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.rate.RateKeyConstant;
import com.yuwang.pinju.core.constant.rate.RateResultConstant;
import com.yuwang.pinju.core.executor.PinjuExecutor;
import com.yuwang.pinju.core.jms.manager.JmsManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.rate.ao.RateBuyerAO;
import com.yuwang.pinju.core.rate.json.DsrOrderCommentDO;
import com.yuwang.pinju.core.rate.manager.RateManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.rate.DsrCommentDO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;
import com.yuwang.pinju.domain.rate.DsrResultDO;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel.Dimension;
import com.yuwang.pinju.domain.rate.comment.RateOrderModel.DsrComments;

public class RateBuyerAOImpl extends BaseAO implements RateBuyerAO, RateResultConstant, RateKeyConstant {

	private final static Log log = LogFactory.getLog(RateBuyerAOImpl.class);
	private MemberManager memberManager;
	private RateManager rateManager;
	private JmsManager jmsManager;
	private OrderQueryManager orderQueryManager;
	private OrderUpDateManager orderUpDateManager;
	private PinjuExecutor pinjuEveryThreadExecutor;

	@Override
	public Result rateOrder(long buyerMemberId, final RateOrderModel model) {
		try {
			if (model == null) {
				log.warn("rateOrder, dsrResults is null or empty");
				return ResultSupportExt.createError(RESULT_RATE_SUBMIT_DATA_ERROR);
			}

			// get all comments of buyer
			List<DsrComments> comments = model.getComment();
			if (EmptyUtil.isBlank(comments)) {
				log.warn("rateOrder, dsrResults is null or empty");
				return ResultSupportExt.createError(RESULT_RATE_SUBMIT_DATA_ERROR);
			}

			// validating order status again
			Result result = queryOrderForRate(buyerMemberId, model.getOrderId());
			if (!result.isSuccess()) {
				return result;
			}

			// encapsulate order, order items, buyer member, seller member and DSR dimimension
			final DsrOrderInfo info = new DsrOrderInfo(result);

			// select DSR rate score from comments
			final List<DsrResultDO> dsrResults = new LinkedList<DsrResultDO>();

			if (!EmptyUtil.isBlank(model.getIdsr())) {
				for (DsrComments comment : comments) {
					OrderItemDO item = info.getOrderItem(comment.getItem());
					if (item == null) {
						log.error("rateOrder, member submit item id isnot in the order, item id: " + comment.getItem());
						return ResultSupportExt.createError(RESULT_RATE_SUBMIT_DATA_ERROR);
					}
					for (Dimension idsr : model.getIdsr()) {
						int rate = idsr.getRateScore();
						if (rate < DsrDimensionDO.RATE_MIN || rate > DsrDimensionDO.RATE_MAX) {
							log.warn("dsr rate need ignore, itemId: " + comment.getItem() + ", dimensionId: " + idsr.getDimen() + ", rate: " + rate);
							continue;
						}
						if (!comment.getItem().equals(idsr.getItem())) {
							log.debug("dsr rate can ignore, itemId: " + comment.getItem() + "dsrItemId: " + idsr.getItem());
							continue;
						}
						DsrDimensionDO dimension = info.getItemDimension(idsr.getDimen());
						if (dimension == null) {
							log.warn("dsr rate can not find dimension id using dimension id in comment rate, itemId: " + comment.getItem() +
									", comment dimension id: " + idsr.getDimen() +
									", rate: " + rate);
							continue;
						}
						DsrResultDO dsrResult = createDsrResult(info, item, comment, dimension, rate);
						dsrResults.add(dsrResult);
					}
				}
			}

			if (!EmptyUtil.isBlank(model.getSdsr())) {
				for (DsrComments comment : comments) {
					OrderItemDO item = info.getOrderItem(comment.getItem());
					if (item == null) {
						log.error("rateOrder, member submit item id isnot in the order, item id: " + comment.getItem());
						return ResultSupportExt.createError(RESULT_RATE_SUBMIT_DATA_ERROR);
					}
					for (Dimension idsr : model.getSdsr()) {
						int rate = idsr.getRateScore();
						if (rate < DsrDimensionDO.RATE_MIN || rate > DsrDimensionDO.RATE_MAX) {
							continue;
						}
						DsrDimensionDO dimension = info.getShopDimension(idsr.getDimen());
						if (dimension == null) {
							continue;
						}
						DsrResultDO dsrResult = createDsrResult(info, item, comment, dimension, rate);
						dsrResults.add(dsrResult);
					}
				}
			}

			// update order buyer-rate status
			String executeResult = executeTransactiono(String.class, new TransactionCallback<String>() {
				public String doInTransaction(TransactionStatus transactionstatus) {
					try {
						orderUpDateManager.upRate(1, info.getBuyer().getMemberId(), model.getOrderId());
						return ResultSupportExt.SUCCESS;
					} catch (ManagerException e) {
						String message = "execute update order rate status, transaction error, order id: " + model.getOrderId();
						log.error(message, e);
						throw new RuntimeException(message, e);
					}
				}
			});

			if(EmptyUtil.isBlank(executeResult)) {
				log.error("execute update order rate status, result unsuccess");
				return ResultSupportExt.createError(RESULT_RATE_INNER_ERROR);
			}

			// add buyer DSR rate and item comments
			final List<DsrCommentDO> dsrComments = info.createDsrComments(comments, dsrResults);
			String executeMysqlResult = executeMysqlTransaction(String.class, new TransactionCallback<String>() {
				public String doInTransaction(TransactionStatus transactionstatus) {
					try {
						rateManager.saveDsrResults(dsrResults);
						rateManager.saveDsrComments(dsrComments);
						return ResultSupportExt.SUCCESS;
					} catch (ManagerException e) {
						log.error("execute rateOrder mysql transaction error", e);
						throw new RuntimeException("save dsr results error", e);
					}
				}
			});

			if(EmptyUtil.isBlank(executeMysqlResult)) {
				log.error("execute submit buyer rate dsr info, result unsuccess");
				return ResultSupportExt.createError(RESULT_RATE_INNER_ERROR);
			}

			// send buyer comments to SNS message queue
			pinjuEveryThreadExecutor.execute(new Runnable() {
				public void run() {
					sendComments(info, dsrComments);
				}
			});

			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("rateOrder error", e);
			return ResultSupportExt.createError(RESULT_RATE_INNER_ERROR);
		}
	}

	private DsrResultDO createDsrResult(DsrOrderInfo info, OrderItemDO item, DsrComments comment, DsrDimensionDO dimension, int rate) {
		DsrResultDO dsrResult = new DsrResultDO();
		dsrResult.setSenderId(info.getBuyer().getMemberId());
		dsrResult.setSenderNick(info.getBuyer().getNickname());
		dsrResult.setReceiverId(info.getSeller().getMemberId());
		dsrResult.setReceiverNick(info.getSeller().getNickname());
		dsrResult.setOrderId(info.getOrder().getOrderId());
		dsrResult.setItemId(item.getItemId());
		dsrResult.setItemTitle(item.getItemTitle());
		dsrResult.setDimensionId(dimension.getId());
		dsrResult.setDimensionName(dimension.getName());
		dsrResult.setRate(rate);
		dsrResult.setRateTime(info.getCurrent());
		dsrResult.setAnonymous(comment.getAnonymousStatus());
		dsrResult.setGmtCreate(info.getCurrent());
		dsrResult.setGmtModified(info.getCurrent());
		return dsrResult;
	}

	@Override
	public Result queryOrderForRate(long buyerMemberId, long orderId) {
		try {
			MemberDO buyerMember = memberManager.findMember(buyerMemberId);

			// 无法获取买家会员数据
			if (buyerMember == null) {
				log.warn("queryOrderForRate, cannot find buyer member for member id: " + buyerMemberId);
				return ResultSupportExt.createError(RESULT_RATE_ORDER_ACCESS_DENIED);
			}

			Map<OrderDO, List<OrderItemDO>> orders = orderQueryManager.queryRateOrderList(orderId);

			// 订单数据不存在
			if (EmptyUtil.isBlank(orders) || orders.size() < 1) {
				log.warn("queryOrderForRate, accroding order id: " + orderId
						+ ", query orders empty or orders count greater than 1");
				return ResultSupportExt.createError(RESULT_RATE_ORDER_INVALID);
			}

			// 获取第一个 Map.Entry
			Map.Entry<OrderDO, List<OrderItemDO>> firstEntry = orders.entrySet().iterator().next();

			OrderDO order = firstEntry.getKey();

			if (order == null) {
				log.warn("queryOrderForRate, can not find order, order id: " + orderId);
				return ResultSupportExt.createError(RESULT_RATE_ORDER_ACCESS_DENIED);
			}

			if (log.isDebugEnabled()) {
				log.debug("queryOrderForRate, current order: " + order);
			}

			if (order.getOrderState().compareTo(OrderDO.ORDER_STATE_5) != 0) {
				log.warn("queryOrderForRate, order state invalid, order id: " + orderId + ", state: "
						+ order.getOrderState());
				return ResultSupportExt.createError(RESULT_RATE_ORDER_INVALID);
			}

			if (order.getIsBuyerRate().compareTo(OrderDO.IS_RATE_NO) != 0) {
				log.warn("queryOrderForRate, current order has rated, order id: " + orderId);
				return ResultSupportExt.createError(RESULT_RATE_ORDER_RATE_DUPLICATE);
			}

			// 订单的买家不是当前会员
			if (!order.getBuyerId().equals(buyerMemberId) || !buyerMember.getNickname().equals(order.getBuyerNick())) {
				log.warn("queryOrderForRate, order buyer id: " + order.getBuyerId() + "[" + order.getBuyerNick() + "], current member id: "
						+ buyerMemberId + "[" + buyerMember.getNickname() + "] isnot same");
				return ResultSupportExt.createError(RESULT_RATE_ORDER_ACCESS_DENIED);
			}

			if (DateUtil.isIndate(order.getStateModifyTime(), OrderDO.ORDER_RATE_TIME_LIMIT)) {
				log.warn("queryOrderForRate, order is overtime, order id: " + orderId + ", order end time: "
						+ DateUtil.formatDatetime(order.getStateModifyTime()));
				return ResultSupportExt.createError(RESULT_RATE_ORDER_RATE_EXPIRED);
			}

			MemberDO sellerMember = memberManager.findMember(order.getSellerId());

			// 订单的卖家找不到
			if (sellerMember == null) {
				log.warn("queryOrderForRate, cannot find seller member info, order id: " + orderId
						+ ", seller id(in order): " + order.getSellerId());
				return ResultSupportExt.createError(RESULT_RATE_ORDER_INVALID);
			}

			List<OrderItemDO> orderItems = firstEntry.getValue();

			// 订单中的商品为空
			if (EmptyUtil.isBlank(orderItems)) {
				log.warn("queryOrderForRate, current order has not any items, order id: " + orderId);
				return ResultSupportExt.createError(RESULT_RATE_ORDER_INVALID);
			}

			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_RATE_SELLER_MEMBER, sellerMember);
			result.setModel(KEY_RATE_ORDER, order);
			result.setModel(KEY_RATE_ORDER_ITEMS, orderItems);
			result.setModel(KEY_RATE_BUYER_MEMBER, buyerMember);
			putDsrDimensions(result);
			return result;
		} catch (Exception e) {
			log.error("queryOrderForRate, queryOrderForRate error", e);
			return ResultSupportExt.createError(RESULT_RATE_INNER_ERROR);
		}
	}

	private void putDsrDimensions(Result result) throws ManagerException {
		List<DsrDimensionDO> dsrs = rateManager.getValidDsrDimension();
		result.setModel(KEY_RATE_ITEM_DSRS, rateManager.filterItemDsrDimension(dsrs));
		result.setModel(KEY_RATE_SHOP_DSRS, rateManager.filterRateDsrDimension(dsrs));
	}

	private void sendComments(final DsrOrderInfo info, final List<DsrCommentDO> dsrComments) {
		pinjuEveryThreadExecutor.execute(new Runnable() {
			@Override
			public void run() {
				DsrOrderCommentDO orderComment = null;
				try {			
					orderComment = new DsrOrderCommentDO(info);
					for(DsrCommentDO dsrComment : dsrComments) {
						if(StringUtils.isBlank(dsrComment.getBuyerComment())) {
							continue;
						}
						orderComment.addDsrItemComment(dsrComment);
					}
					jmsManager.sendDsrComment(orderComment);
				} catch (Exception e) {
					log.error("send item comments error, data: " + orderComment, e);
				}
			}
		});
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setRateManager(RateManager rateManager) {
		this.rateManager = rateManager;
	}

	public void setJmsManager(JmsManager jmsManager) {
		this.jmsManager = jmsManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setPinjuEveryThreadExecutor(PinjuExecutor pinjuEveryThreadExecutor) {
		this.pinjuEveryThreadExecutor = pinjuEveryThreadExecutor;
	}

	public static class DsrOrderInfo {

		private MemberDO seller;
		private MemberDO buyer;
		private OrderDO order;
		private List<OrderItemDO> items;
		private List<DsrDimensionDO> itemDimensions;
		private List<DsrDimensionDO> shopDimensions;
		private Date current;

		@SuppressWarnings("unchecked")
		DsrOrderInfo(Result result) {
			seller = result.getModel(KEY_RATE_SELLER_MEMBER, MemberDO.class);
			buyer = result.getModel(KEY_RATE_BUYER_MEMBER, MemberDO.class);
			order = result.getModel(KEY_RATE_ORDER, OrderDO.class);
			items = (List<OrderItemDO>)result.getModel(KEY_RATE_ORDER_ITEMS);

			itemDimensions = (List<DsrDimensionDO>)result.getModel(KEY_RATE_ITEM_DSRS);
			if (itemDimensions == null) {
				itemDimensions = new ArrayList<DsrDimensionDO>(0);
			}

			shopDimensions = (List<DsrDimensionDO>)result.getModel(KEY_RATE_SHOP_DSRS);
			if (shopDimensions == null) {
				shopDimensions = new ArrayList<DsrDimensionDO>(0);
			}
			current = new Date();
		}

		public List<DsrCommentDO> createDsrComments(List<DsrComments> comments, List<DsrResultDO> dsrResults) {
			List<DsrCommentDO> dsrComments = new ArrayList<DsrCommentDO>(itemSize());
			for (DsrComments comment : comments) {
				OrderItemDO item = getOrderItem(comment.getItem());
				dsrComments.add(new DsrCommentDO(order, item, comment, current, dsrResults));
			}
			return dsrComments;
	    }

		public MemberDO getSeller() {
			return seller;
		}

		public MemberDO getBuyer() {
			return buyer;
		}

		public OrderDO getOrder() {
			return order;
		}

		public int itemSize() {
			if (items == null) {
				return 0;
			}
			return items.size();
		}

		public OrderItemDO getOrderItem(String itemId) {
			if (items == null) {
				return null;
			}
			for (OrderItemDO item : items) {
				if (String.valueOf(item.getItemId()).equals(itemId)) {
					return item;
				}
			}
			return null;
		}

		public DsrDimensionDO getItemDimension(String dimensionId) {
			return getDimension(itemDimensions, dimensionId);
		}

		public DsrDimensionDO getShopDimension(String dimensionId) {
			return getDimension(shopDimensions, dimensionId);
		}

		public Date getCurrent() {
			return current;
		}

		private DsrDimensionDO getDimension(List<DsrDimensionDO> dimensions, String dimensionId) {
			if (itemDimensions == null) {
				return null;
			}
			for (DsrDimensionDO dimension : dimensions) {
				if (String.valueOf(dimension.getId()).equals(dimensionId)) {
					return dimension;
				}
			}
			return null;
		}
	}
}
