package com.yuwang.pinju.core.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.api.ApiException;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.logistics.manager.LogisticsCorpManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.service.OpenQueryOrderService;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.OpenOrderQuery;
import com.yuwang.pinju.domain.order.query.OrderStateEnum;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;

/**
 * Created on 2011-9-27
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OpenQueryOrderServiceImpl extends BaseAO implements
		OpenQueryOrderService {

	private OrderQueryManager orderQueryManager;

	private OrderBusinessManager orderBusinessManager;

	private OrderCheckManager orderCheckManager;
	
	private OrderBusinessAO orderBusinessAO;
	
	private MemberManager memberManager;
	
	private LogisticsCorpManager logisticsCorpManager;

	@Override
	public List<OrderDO> queryOrder(OpenOrderQuery openOrderQuery) {
		List<OrderDO> result = new ArrayList<OrderDO>();
		try {
			int size = openOrderQuery.getOrderStateEnum().length;
			Integer[] orderState = new Integer[size];
			int i = 0;
			for (OrderStateEnum orderStateEnum : openOrderQuery
					.getOrderStateEnum()) {
				orderState[i] = orderStateEnum.getFeatureName();
				i++;
			}
			List<OrderDO> list = orderQueryManager.queryOrder(openOrderQuery
					.getSellerId(), null, orderState, openOrderQuery
					.getStart_date(), openOrderQuery.getEnd_date(), null, null,
					null, openOrderQuery.getPage(), openOrderQuery
							.getItemsPerPage());
			for (OrderDO orderDO : list) {
				orderDO.setOrderItemlist(orderQueryManager
						.queryOrderItemList(orderDO.getOrderId()));
				orderDO.setOrderLogisticsDO(orderBusinessManager
						.queryOrderLogisticsByOrderId(orderDO.getOrderId()));
				result.add(orderDO);
			}

		} catch (ManagerException e) {
			log.error("Event=[OpenQueryOrderServiceImpl#queryOrder]:", e);
		}
		return result;

	}

	@Override
	public OrderDO queryOrderInfo(Long orderId, Long sellerId) {
		OrderDO orderDO = null;

		try {
			if (!orderCheckManager.isSellerOrder(orderId, sellerId))
				return orderDO;
			orderDO = orderQueryManager.getOrderDOById(orderId);
			orderDO.setOrderItemlist(orderQueryManager
					.queryOrderItemList(orderDO.getOrderId()));
			orderDO.setOrderLogisticsDO(orderBusinessManager
					.queryOrderLogisticsByOrderId(orderDO.getOrderId()));
		} catch (ManagerException e) {
			log.error("Event=[OpenQueryOrderServiceImpl#queryOrderInfo]:", e);
		}
		return orderDO;

	}

	@Override
	public boolean sellerDelivery(Long orderId, String outLogisticsId,
			String logisticsCode, Long sellerId,String sendAddress,String sendName,String sendPost,String sendMobilePhone,String sendPhone) throws ApiException {
		boolean flag = false;
		SendDeliveryVO sendDeliveryVO = new SendDeliveryVO();
		try {
			
		
			if (!orderCheckManager.isSellerOrder(orderId, sellerId)){
				log.error("Event=[OpenQueryOrderServiceImpl#sellerDelivery] exception 该订单不属于该卖家更新非法!");
				log.error("error parms orderId-->".concat(String.valueOf(orderId)));
				log.error("error parms sellerId-->".concat(String.valueOf(sellerId)));
				throw new ApiException("该订单不属于该卖家更新非法!");
			}
			List<MemberDeliveryDO> list = memberManager.findMemberDeliveries(sellerId);
			
			flag = StringUtil.isNotEmpty(sendName) && StringUtil.isNotEmpty(sendAddress) && StringUtil.isNotEmpty(sendPost);
			if (!flag) {
				if (list != null && !list.isEmpty()) {
					log.warn("seller 无发货地址,取默认发货地址");
					log.warn("orderId--->".concat(String.valueOf(orderId)));
					log.error("error parms sellerId-->".concat(String.valueOf(sellerId)));
					sendDeliveryVO.setMemberDeliveriesId(list.get(0).getId());
				} else {
					throw new ApiException("您还没有设置默认地址，请填写发货地址。");
				}
			}
			sendDeliveryVO.setOrderId(orderId);
			sendDeliveryVO.setSenderMemberId(sellerId);
			sendDeliveryVO.setOutLogisticsId(outLogisticsId);
			sendDeliveryVO.setLogisticsType(logisticsCode);
			sendDeliveryVO
					.setConsignmentMode(OrderLogisticsDO.CONSIGNMENT_MODE_EXPRESS);
			sendDeliveryVO.setSendAddress(sendAddress);
			sendDeliveryVO.setSendName(sendName);
			sendDeliveryVO.setSendPost(sendPost);
			sendDeliveryVO.setSendMobilePhone(sendMobilePhone);
			sendDeliveryVO.setSendPhone(sendPhone);
			sendDeliveryVO.setSendIp("9999999999");
			Result result = orderBusinessAO.confirmDelivery(sendDeliveryVO);
			flag = result.isSuccess();
			if(!flag){
				throw new ApiException((String)result.getModel("errorMessage"));
			}
		} catch (ManagerException e) {
			log.error("Event=[OpenQueryOrderServiceImpl#sellerDelivery]:", e);
			log.error("error parms -->".concat(sendDeliveryVO.toString()));
			throw new ApiException("系统异常！");
		}
		return flag;
	}

	@Override
	public Long sellerOrderSize(OpenOrderQuery openOrderQuery) {
		Long num = 0L;
		try {
			int size = openOrderQuery.getOrderStateEnum().length;
			Integer[] orderState = new Integer[size];
			int i = 0;
			for (OrderStateEnum orderStateEnum : openOrderQuery
					.getOrderStateEnum()) {
				orderState[i] = orderStateEnum.getFeatureName();
				i++;
			}
			num = orderQueryManager.queryOrderNum(openOrderQuery.getSellerId(),
					null, orderState, openOrderQuery.getStart_date(),
					openOrderQuery.getEnd_date(), null, null, null);
		} catch (ManagerException e) {
			log.error("Event=[OpenQueryOrderServiceImpl#sellerOrderSize]:", e);
		}
		return num;
	}

	@Override
	public Map<Long, String> queryOrderItemDiscountDesc(Long orderId) {
		Map<Long, String> map = new HashMap<Long, String>();
		try {
			List<OrderItemDO> list = orderQueryManager
					.queryOrderItemList(orderId);
			if (list == null || list.isEmpty()) {
				log
						.error("Event=[OrderQueryAOImpl#queryOrderItemDiscountDesc]父子订单参数错误。错误主订单编号:" + orderId);
				throw new ManagerException();
			}
			String discountKey = OrderItemAttributesEnum.ITEM_LIMIT_TIME_DISCOUNT
					.getFeatureName();
			for (OrderItemDO orderItemDO : list) {
				String attributes = orderItemDO.getOrderItemAttributes();
				String resultString = "";
				Long discountAmount = 0L;
				// 限时折扣优惠信息
				if (StringUtil.contains(attributes, discountKey)) {
					discountAmount = discountAmount(orderItemDO);
					resultString = resultString.concat(
							OrderItemAttributesEnum.ITEM_LIMIT_TIME_DISCOUNT
									.getDescription()).concat(
							VouchPayConstant.SPLITATTRIBUTES);
					resultString = resultString.concat(MoneyUtil
							.getCentToDollar(discountAmount));
					resultString = resultString
							.concat(VouchPayConstant.SPLITKEY);
				}
				// 卖家优惠
				Long sellerMarginPrice = orderItemDO.getSellerMarginPrice();
				if (sellerMarginPrice != null
						&& sellerMarginPrice.compareTo(0L) != 0) {
					resultString = resultString.concat("卖家调整").concat(
							VouchPayConstant.SPLITATTRIBUTES).concat(
							MoneyUtil.getCentToDollar(sellerMarginPrice));
				}
				map.put(orderItemDO.getOrderItemId(), resultString);
			}
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#queryOrderItemDiscountDesc]", e);
		} catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#queryOrderItemDiscountDesc]", e);
		}
		return map;
	}

	@Override
	public String queryOrderItemDiscountPrice(Long orderItemId) {
		Long discountAmount = 0L;
		try {
			OrderItemDO orderItemDO = orderQueryManager
					.getOrderItemDOById(orderItemId);
			if (orderItemDO == null
					|| orderItemDO.getOrderItemId().compareTo(0L) <= 0) {
				log
						.error("Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]子订单参数错误。错误子订单编号:" + orderItemId);
				throw new ManagerException();
			}
			discountAmount = discountAmount(orderItemDO);
			// 卖家优惠
			Long sellerMarginPrice = orderItemDO.getSellerMarginPrice();
			if (sellerMarginPrice != null
					&& sellerMarginPrice.compareTo(0L) != 0) {
				discountAmount += sellerMarginPrice;
			}
		} catch (ManagerException e) {
			log
					.error(
							"Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]",
							e);
		} catch (Exception e) {
			log
					.error(
							"Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]",
							e);
		}
		return MoneyUtil.getCentToDollar(discountAmount);
	}

	@Override
	public String queryOrderItemDiscountPrice(OrderItemDO orderItemDO) {
		Long discountAmount = 0L;
		try {
			boolean isNull = orderItemDO == null
					|| orderItemDO.getOrderItemId().compareTo(0L) <= 0;
			if (isNull) {
				log
						.error("Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]子订单参数错误。" );
				throw new ManagerException();
			}
			discountAmount = discountAmount(orderItemDO);
			// 卖家优惠
			Long sellerMarginPrice = orderItemDO.getSellerMarginPrice();
			if (sellerMarginPrice != null
					&& sellerMarginPrice.compareTo(0L) != 0) {
				discountAmount += sellerMarginPrice;
			}
		} catch (ManagerException e) {
			log
					.error(
							"Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]",
							e);
		} catch (Exception e) {
			log
					.error(
							"Event=[OrderQueryAOImpl#queryOrderItemDiscountPrice]",
							e);
		}
		return MoneyUtil.getCentToDollar(discountAmount);
	}

	/**
	 * 
	 * Created on 2011-10-8
	 * <p>
	 * Discription: [子订单优惠后金额]
	 * </p>
	 * 
	 * @param orderItemDO
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long discountAmount(OrderItemDO orderItemDO) {

		String attributes = orderItemDO.getOrderItemAttributes();
		if (StringUtil.isEmpty(attributes)) {
			return 0L;
		}

		Long discountAmount = 0L;

		// 限时折扣 标记
		String discountKey = OrderItemAttributesEnum.ITEM_LIMIT_TIME_DISCOUNT
				.getFeatureName();
		String[] parms = StringUtil
				.split(attributes, VouchPayConstant.SPLITKEY);
		for (String parm : parms) {
			if (StringUtil.contains(parm, discountKey)) {
				String value = "";
				if(StringUtil.contains(parm, VouchPayConstant.SPLITATTRIBUTES)){
					if(StringUtil.split(parm,
							VouchPayConstant.SPLITVALUES).length>1){
						value = StringUtil.split(parm,
								VouchPayConstant.SPLITATTRIBUTES)[1];
					}
				}
				// 下单时活动的折扣率
				String dicountRate = "";
				if(StringUtil.contains(value,
						VouchPayConstant.SPLITVALUES)){
					if( StringUtil.split(value,
							VouchPayConstant.SPLITVALUES).length > 1)
						dicountRate = StringUtil.split(value,
								VouchPayConstant.SPLITVALUES)[1];
				}
				if(StringUtil.isNotEmpty(dicountRate)){
					// 折扣后金额
					discountAmount = orderItemDO.getOriginalPrice()
						* Long.valueOf(dicountRate)
						/ OrderConstant.ITEM_LIMIT_TIME_DISCOUNT_RATE;
				}
				if(discountAmount.compareTo(0l)!=0){
					discountAmount = (orderItemDO.getOriginalPrice() - discountAmount)
						* orderItemDO.getBuyNum();
				}
			}
		}

		return discountAmount;
	}
	
	/**
	 * @Description: 判断物流公司是否存在
	 * @author [贺泳]
	 * @date 2011-10-19 下午06:32:06
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param corpCode ：物流公司代码
	 * @return
	 */
	public boolean checkTradeLogisticsCorp(String corpCode) {
		boolean bool = false;
		try {
			LogisticsCorpDO corpDo = new LogisticsCorpDO();
			corpDo.setCorpCode(corpCode);
			corpDo.setLogisticsStatus(1L);
			List<LogisticsCorpDO> list = logisticsCorpManager.getLogisticsCorpByStatus(corpDo);
			if(list.size() != 0){
				bool = true;
			}
		} catch (ManagerException e) {
			log.error("Event=[OrderQueryAOImpl#checkTradeLogisticsCorp]",e);
		}catch (Exception e) {
			log.error("Event=[OrderQueryAOImpl#checkTradeLogisticsCorp]",e);
		}
		return bool;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderBusinessManager(
			OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setOrderBusinessAO(OrderBusinessAO orderBusinessAO) {
		this.orderBusinessAO = orderBusinessAO;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}
	
	public void setLogisticsCorpManager(LogisticsCorpManager logisticsCorpManager) {
		this.logisticsCorpManager = logisticsCorpManager;
	}
}
