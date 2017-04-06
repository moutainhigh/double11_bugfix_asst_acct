package com.yuwang.pinju.core.order.manager.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderTimeEnum;
import com.yuwang.pinju.Constant.OrderttributesEnum;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.CouponDAO;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.dao.OrderUpDdateDAO;
import com.yuwang.pinju.core.order.manager.OrderLogManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.order.manager.check.OrderStateManager;
import com.yuwang.pinju.core.order.manager.helper.impl.OrderUtilMothed;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.trade.dao.VouchCreateDAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-7-30
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderUpDateManagerImpl extends BaseManager implements
		OrderUpDateManager {

	private OrderUpDdateDAO orderUpDdateDAO;

	private OrderQueryDAO orderQueryDAO;
	
	private OrderStateManager orderStateManager;
	
	private OrderLogManager orderLogManager;
	
	private ItemManager itemManager;
	
	private VouchCreateDAO vouchCreateDAO ;
	
	private RefundCheckManager  refundCheckManager;
	
	private CouponDAO couponDAO;
	
	private MemberAsstLog memberAsstLog;
	
	
	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}



	public void setCouponDAO(CouponDAO couponDAO) {
		this.couponDAO = couponDAO;
	}



	public void setVouchCreateDAO(VouchCreateDAO vouchCreateDAO) {
		this.vouchCreateDAO = vouchCreateDAO;
	}

	
	
	public void setRefundCheckManager(RefundCheckManager refundCheckManager) {
		this.refundCheckManager = refundCheckManager;
	}



	public void setOrderLogManager(OrderLogManager orderLogManager) {
		this.orderLogManager = orderLogManager;
	}

	public void setOrderUpDdateDAO(OrderUpDdateDAO orderUpDdateDAO) {
		this.orderUpDdateDAO = orderUpDdateDAO;
	}

	public void setOrderStateManager(OrderStateManager orderStateManager) {
		this.orderStateManager = orderStateManager;
	}

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}
	@Override
	public boolean orderItemClose(final long orderItemId,final String failReason) throws ManagerException {
		boolean flag = false;
		try {
			flag = getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							try {
								
								OrderItemDO orderItem = orderQueryDAO.queryOrderItemDO(orderItemId);
								orderItem.setFailReason(failReason);
								orderItem.setOrderItemState(OrderItemDO.ORDER_ITEM_STATE_6);
								flag = orderUpDdateDAO
											.upOrderItem(orderItem) > 0;
								if(flag){
									Long itemSkuId= null;
									if(orderItem.getItemSkuId()!=null){
										itemSkuId = orderItem.getItemSkuId();
									}
									//团购或限时折扣换库存
									if(OrderUtilMothed.isGroupOrDicountRate(orderItem.getBussinessType())){
										itemManager.cutStock(orderItem.getItemId(), itemSkuId, -orderItem.getBuyNum());
									}
								}
											
											
								Long orderId = orderItem.getOrderId();			
								List<OrderItemDO> list = orderQueryDAO.queryOrderItemList(orderId);
								if(list == null || list.isEmpty())
									return flag;
								int orderStateFlag = 0;
								for(OrderItemDO obj: list)	{
									 if (obj.getOrderItemState().compareTo(OrderItemDO.ORDER_ITEM_STATE_6) != 0){
										 orderStateFlag += 1;
									 }
 								}
								
								if(orderStateFlag == 0){
									OrderDO orderDO = new OrderDO(orderId,
											OrderDO.ORDER_STATE_6, failReason);
									flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
								}

								//插入日志
								if(flag){
									OrderDO ODO = orderQueryDAO.queryOrder(orderId);
									OrderLogDO orderLogDO = OrderLogDO.newOrderLog(ODO.getBuyerId(), 
											ODO.getBuyerId().toString(), failReason, ODO.getBuyIp(),
											OrderConstant.OPERATE_LOG_CLOSE, orderId, ODO.getSellerId(),orderItemId,OrderDO.ORDER_STATE_6);
									flag =  orderLogManager.createOrderLog(orderLogDO) > 0 ;
								}		
								
						
								
								
								// 出错回滚
								if (!flag)
									throw new DaoException();
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		} catch (Exception e) {
			throw new ManagerException("Event=[OrderUpDateManagerImpl#close] 子订单关闭出错:".concat(String.valueOf(orderItemId)), e);
		}
		return flag;
	}

	@Override
	public boolean orderClose(final long orderId, final String failReason)
			throws ManagerException {
		boolean flag = false;
		try {
			flag = getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							try {
								OrderDO orderDO = new OrderDO(orderId,
										OrderDO.ORDER_STATE_6, failReason);
								flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
								
								if (!flag)
									return flag;
								
								OrderItemDO orderItemDO = OrderItemDO.upAllOrderItemDO(
										OrderItemDO.ORDER_ITEM_STATE_6,
										orderId, failReason);
								
								flag = orderUpDdateDAO
											.upAllOrderItemState(orderItemDO) > 0;
											
						
								//插入日志
								if(flag){	
									OrderDO ODO = orderQueryDAO.queryOrder(orderId);
									List<OrderItemDO> list = orderQueryDAO.queryOrderItemList(orderId);
									for(OrderItemDO obj : list){
										OrderLogDO orderLogDO = OrderLogDO.newOrderLog(ODO.getBuyerId(), 
												ODO.getBuyerId().toString(), "取消订单", ODO.getBuyIp(),
												OrderConstant.OPERATE_LOG_CLOSE, orderId, ODO.getSellerId(),obj.getOrderItemId(),OrderDO.ORDER_STATE_6);
										orderLogManager.createOrderLog(orderLogDO);
									}
								}		
								
								List<OrderItemDO> list= orderQueryDAO.queryOrderItemList(Long.valueOf(orderId));
								
								for(OrderItemDO obj:list)
								{
									Long itemSkuId= null;
									if(obj.getItemSkuId()!=null){
										itemSkuId = obj.getItemSkuId();
									}
									//团购或限时折扣换库存
									if(OrderUtilMothed.isGroupOrDicountRate(obj.getBussinessType())){
										itemManager.cutStock(obj.getItemId(), itemSkuId, -obj.getBuyNum());
									}
								}
								
								
								// 出错回滚
								if (!flag)
									throw new DaoException();
							} catch (Exception e) {
								arg0.setRollbackOnly();
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		} catch (Exception e) {
			throw new ManagerException(
					"Event=[OrderUpDateManagerImpl#close] 主订单关闭出错", e);
		}
		return flag;
	}

	
	

	@Override
	public boolean confirmDelivery(final SendDeliveryVO sendDelivery)
			throws ManagerException {
		boolean flag = false;
		try {
			flag = getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							try {
								OrderLogisticsDO orderLogisticsDO = new OrderLogisticsDO(
										sendDelivery.getOrderId(), sendDelivery.getOutLogisticsId(),
										sendDelivery.getSendAddress(),sendDelivery.getSendName(),
										sendDelivery.getConsignmentMode(),sendDelivery.getLogisticsType(),
										sendDelivery.getGmtModified(),sendDelivery.getConsingTime(),
										sendDelivery.getSendPhone(),sendDelivery.getSendPost(),
										sendDelivery.getSendMobilePhone(),OrderLogisticsDO.LOGISTICS_STATE_2,
										sendDelivery.getLogisticsName());
								
								flag = orderUpDdateDAO.upOrderLogistics(orderLogisticsDO) > 0;
								
								if (flag) {
									OrderDO orderDO = new OrderDO(sendDelivery.getOrderId(),OrderDO.ORDER_STATE_3,"物流发货");

									flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
									
									int refundnum = refundCheckManager.getRefundingCount(sendDelivery.getOrderId());
									
									if(refundnum > 0){
										orderDO.setIsRefund(OrderConstant.IS_REFUND_YES);
									}else{
										orderDO.setIsRefund(OrderConstant.IS_REFUND_NO);
									}
									String consignmentMode = sendDelivery.getConsignmentMode();
								
									int residueTimeHour = Integer.parseInt(OrderTimeEnum.getValueByType(Integer.parseInt(consignmentMode)).getFeatureName());
								
									orderDO.setResidueTimeHour(residueTimeHour);
								
									orderDO.setConfirmStartTime(sendDelivery.getConsingTime());
									
									orderUpDdateDAO.updateOrderConfirmTime(orderDO);
								}
								if (flag) {
									OrderItemDO orderItemDO = OrderItemDO.upConfirmTimeOrderItemDO(
											OrderDO.ORDER_STATE_3, sendDelivery.getOrderId(),
											sendDelivery.getAutoConfirmTime(sendDelivery.getConsignmentMode()));
					
									flag = orderUpDdateDAO.upAllOrderItemState(orderItemDO) > 0;
								}
								
								//插入日志
								if(flag){
									OrderDO ODO = orderQueryDAO.queryOrder( sendDelivery
											.getOrderId());
									List<OrderItemDO> orderItemDOs = orderQueryDAO.queryOrderItemList(ODO.getOrderId());
									Long senderMemberId = sendDelivery.getSenderMemberId();
									if(senderMemberId==null){
										senderMemberId = ODO.getSellerId();
									}
									for(OrderItemDO orderItemdo : orderItemDOs){
										OrderLogDO orderLogDO = OrderLogDO.newOrderLog(ODO.getBuyerId(), 
												String.valueOf(senderMemberId),"卖家发货", sendDelivery.getSendIp(),
												getLogOptType(OrderDO.ORDER_STATE_3), ODO.getOrderId(), 
												ODO.getSellerId(),orderItemdo.getOrderItemId(),OrderDO.ORDER_STATE_3);
										
										 orderLogManager.createOrderLog(orderLogDO);
										 
									}
								
								}	

								// 出错回滚
								if (!flag)
									throw new DaoException();
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		} catch (Exception e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderItemDONum] 卖家发货数据操作错误",e);
		}
		return flag;
	}

	@Override
	public boolean upRefundState(long orderItemId, int orderRefundState)
			throws ManagerException {
		try {
			OrderItemDO orderItemDO = OrderItemDO.createRefundStateOrderItemDO(orderRefundState,orderItemId);
			return orderUpDdateDAO.upRefundOrderItem(orderItemDO)>0;
		} catch (DaoException e) {
			log.error("OrderCustomerManager.upRefundState error", e);
			throw new ManagerException(
					"OrderCustomerManager.upRefundState error", e);
		}
	}

	private String getLogOptType (int optOrderState){
		String returString = OrderConstant.OPERATE_LOG_CREATION;
		switch (optOrderState) {
		case OrderDO.ORDER_STATE_2:
			returString = OrderConstant.OPERATE_LOG_PAY;
			break;
		case OrderDO.ORDER_STATE_3:
			returString = OrderConstant.OPERATE_LOG_Receving;
			break;
		case OrderDO.ORDER_STATE_5:
			returString = OrderConstant.OPERATE_LOG_RECEIVE;
			break;
		case OrderDO.ORDER_STATE_6:
			returString = OrderConstant.OPERATE_LOG_CLOSE;
			break;
		default:
			break;
		}
		return returString;
	}

	@Override
	public boolean upOrderState(Long optMemberId,final Long orderId,final Integer orderState,final String failReason)
			throws ManagerException {

		boolean flag = false;
		try {
			if (!orderStateManager.checkOrderState(optMemberId, orderId,
					orderState) && orderState!=OrderDO.ORDER_STATE_2)
				return flag;
			flag =  getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							try {
								OrderDO orderDO = new OrderDO(orderId,orderState,failReason);
								flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
								
								if (!flag)
									return flag;
								
								OrderItemDO orderItemDO =OrderItemDO.upAllOrderItemDO(orderState,orderId,failReason);
								if (flag)
									flag = orderUpDdateDAO
											.upAllOrderItemState(orderItemDO) > 0;
											
								
								//插入日志
								if(flag){
									OrderDO ODO = orderQueryDAO.queryOrder(orderId);
									List<OrderItemDO> orderItemDOs = orderQueryDAO.queryOrderItemList(ODO.getOrderId());
									for(OrderItemDO orderItemdo : orderItemDOs){
										OrderLogDO orderLogDO = OrderLogDO.newOrderLog(ODO.getBuyerId(), 
												ODO.getBuyerId().toString(), failReason , ODO.getBuyIp(),
												getLogOptType(orderState), orderId, ODO.getSellerId(),orderItemdo.getOrderItemId(),orderState);
										 orderLogManager.createOrderLog(orderLogDO);
									}
								
								}	
											
								// 出错回滚
								if (!flag)
									throw new DaoException();
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		} catch (Exception e) {
			throw new ManagerException(
					"Event=[OrderUpDateManagerImpl#upOrderState] 订单状态更新出错", e);
		}
		return flag;

	}


	@Override
	public boolean upOrderItemState(final long optMemberId,final Long orderId,final Long orderItemId,
			final Integer orderItemState,final String failReason) throws ManagerException {
		boolean flag = false;
		try {
			if (!orderStateManager.checkOrderState(optMemberId, orderId,
					orderItemState)&& orderItemState!=OrderItemDO.ORDER_ITEM_STATE_2)
				return flag;
			flag =  getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							try {
								OrderItemDO orderItemDO = new OrderItemDO(orderItemId,orderItemState,failReason);
								flag = orderUpDdateDAO.upOrderItem(orderItemDO) > 0;
								boolean isOrderDOUP = orderItemState.compareTo(OrderItemDO.ORDER_ITEM_STATE_5)==0;
								
								QueryOrderItem queryOrderItem = new QueryOrderItem(orderId,
										new int[]{OrderItemDO.ORDER_ITEM_STATE_3});
								
								long num =  orderQueryDAO.queryOrderItemNum(queryOrderItem);
								
								if (flag && isOrderDOUP && num==0){
									OrderDO orderDO = new OrderDO(orderId,
											orderItemState,failReason);
									flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
								}
								
								OrderLogDO orderLogDO = OrderLogDO.newOrderLog(orderItemDO.getBuyerId(), 
										orderItemDO.getBuyerId().toString(), failReason ,"1",
										getLogOptType(orderItemState), orderId, orderItemDO.getSellerId(),orderItemDO.getOrderItemId(),orderItemState);
								 orderLogManager.createOrderLog(orderLogDO);
								// 出错回滚
								if (!flag)
									throw new DaoException();
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		} catch (Exception e) {
			throw new ManagerException(
					"Event=[OrderUpDateManagerImpl#upOrderItemState] 子订单订单出错", e);
		}
		return flag;
	}

	
	@Override
	public void upRate(int rateType, Long memberId, Long orderId)
			throws ManagerException {
		try {
			OrderDO order = orderQueryDAO.queryOrder(orderId);
			// 订单与买家关系
			if (order.getBuyerId().compareTo(memberId) != 0){
				log.error("Event=[OrderUpDateManagerImpl#upRate],当前登陆会员无权评论次订单");
				log.error("登陆会员ID-->".concat(String.valueOf(memberId)));
				log.error("当前订单ID--->".concat(String.valueOf(orderId)));
				throw new ManagerException();
			}
			// 订单是否已评价
			if (order.getIsBuyerRate().equals(OrderDO.IS_RATE_YES)){
				log.error("Event=[OrderUpDateManagerImpl#upRate],当前订单已评价");
				log.error("登陆会员ID-->".concat(String.valueOf(memberId)));
				log.error("当前订单ID--->".concat(String.valueOf(orderId)));
				throw new ManagerException();
			}
			OrderDO orderDO =OrderDO.initRateOrderDO(orderId,null,OrderDO.IS_RATE_YES);
			orderUpDdateDAO.updateOrderBuyerRate(orderDO);
		} catch (DaoException e) {
			log.error("OrderCustomerManager.upRate error", e);
			throw new ManagerException("OrderCustomerManager.upRate error", e);
		}
	}


	@Override
	public boolean sellerMarginPrice(final long orderItemId[],
			final long sellerMarginPrice[],
			final long sellerLogisticMarginPrice, final String operateIp,final Long[] modifyBefore)
			throws ManagerException {

		boolean flag = false;
		try {
			flag = getZizuTransactionTemplate().execute(
					new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus arg0) {
							boolean flag = true;
							try {
								long totalPrice = 0;
								long orderId = 0;
								OrderItemDO orderItemDO = null;
								for (int i = 0; i < orderItemId.length; i++) {
									orderItemDO = orderQueryDAO
											.queryOrderItemDO(orderItemId[i]);
									OrderDO orderDO = orderQueryDAO
											.queryOrder(orderItemDO
													.getOrderId());
									orderId = orderDO.getOrderId();
									if (sellerMarginPrice != null) {
										//orderItemDO.setOrderId(0L);
										final Long _sellerMarginPrice = sellerMarginPrice[i];
										
										//操作日志
										memberAsstLog.log("trade", "price", "修改"+orderId+"订单价格"+MoneyUtil.getCentToDollar(orderItemDO.getOrderItemPrice()*orderItemDO.getBuyNum())+"元为 "+MoneyUtil.getCentToDollar(orderItemDO.getOrderItemPrice()*orderItemDO.getBuyNum()+_sellerMarginPrice)+"元");
										
										
										//多次调整价格的差额
										Long _modifyPriceToTatle = _sellerMarginPrice;
										if(!EmptyUtil.isBlank(modifyBefore[i])){
											_modifyPriceToTatle =  _sellerMarginPrice - modifyBefore[i] ;
										}
										
										orderItemDO.setTotalAmount(orderItemDO.getTotalAmount() + _modifyPriceToTatle);
										
										orderItemDO
												.setSellerMarginPrice(_sellerMarginPrice);
										/*totalPrice = totalPrice
												+ _sellerMarginPrice
												+ orderItemDO
														.getOrderItemPrice()
												* orderItemDO.getBuyNum();*/
										totalPrice = totalPrice + orderItemDO.getTotalAmount();
										
										flag = orderUpDdateDAO
												.upOrderItem(orderItemDO) > 0;
										if (flag) {
											OrderLogDO orderLogDO = new OrderLogDO(
													orderItemDO.getBuyerId(),
													orderItemDO.getSellerId()
															.toString(),
													"卖家修改价格",
													operateIp,
													0,
													OrderConstant.OPERATE_LOG_UPDATE_AMOUNT,
													orderId,
													orderItemDO
															.getOrderItemState(),
													orderItemDO.getSellerId(),
													_sellerMarginPrice,
													orderItemDO
															.getOrderItemId());
											flag = orderLogManager
													.createOrderLog(orderLogDO) > 0;
										}

									}
								}
								final long _sellerLogisticMarginPrice = sellerLogisticMarginPrice;
								if (flag) {
									totalPrice = totalPrice
											+ _sellerLogisticMarginPrice;
									OrderLogisticsDO orderLogisticsDO = orderQueryDAO
											.queryOrderLogisticsByOrderId(orderId);
									
									if(orderLogisticsDO.getPostPrice().compareTo(_sellerLogisticMarginPrice) != 0){
										//操作日志
										memberAsstLog.log("trade", "price", "修改订单"+orderId+"订单运费由  "+MoneyUtil.getCentToDollar(orderLogisticsDO.getPostPrice())+"元为 "+MoneyUtil.getCentToDollar(sellerLogisticMarginPrice)+"元");
									}
									orderLogisticsDO
											.setPostPrice(_sellerLogisticMarginPrice);

									// 修改运费
									flag = orderUpDdateDAO
											.upOrderLogistics(orderLogisticsDO) > 0;

									// 插入日志
									if (flag) {
										List<OrderItemDO> orderItemDOs = orderQueryDAO
												.queryOrderItemList(orderId);
										for (OrderItemDO OIDO : orderItemDOs) {
											OrderLogDO orderLogDO = new OrderLogDO(
													OIDO.getBuyerId(),
													OIDO.getSellerId()
															.toString(),
													"卖家修改运费",
													operateIp,
													0,
													OrderConstant.OPERATE_LOG_UPDATE_POST_PRICE,
													orderId,
													OIDO.getOrderItemState(),
													OIDO.getSellerId(),
													_sellerLogisticMarginPrice,
													OIDO.getOrderItemId());
											flag = orderLogManager
													.createOrderLog(orderLogDO) > 0;
											if (!flag)
												break;
										}
									}
								}

								/*//判断订单是否有使用优惠券
								TradeCouponDO couponDO = couponDAO.getTradeCouponDOByOrderId(orderId);
								if(couponDO != null){
									if(!EmptyUtil.isBlank(couponDO.getCouponMoney())){
										totalPrice = totalPrice - couponDO.getCouponMoney();
									}
								}*/
								
								
								if (flag) {
									OrderDO orderDO = orderQueryDAO
											.queryOrder(orderId);
									orderDO.setPriceAmount(totalPrice);

									// 修改主订单
									flag = orderUpDdateDAO.updateOrder(orderDO) > 0;

									// 修改支付表支付金额
									if (flag) {
										VouchPayDO orderPayDO = new VouchPayDO();
										orderPayDO.setOrderId(orderId);
										orderPayDO.setOrderAmount(totalPrice);
										flag = vouchCreateDAO
												.updateOrderPay(orderPayDO) > 0;
									}

								}

								// 出错回滚
								if (!flag)
									throw new DaoException();

							} catch (Exception e) {
								log.error(
										"[OrderUpDateManagerImpl#sellerMarginPrice]修改订单价格错误:",
										e);
								arg0.setRollbackOnly();
								throw new RuntimeException();							
							}
							return flag;
						}
					});
		} catch (Exception e) {
			log.error("[OrderUpDateManagerImpl#sellerMarginPrice]修改订单价格错误:", e);
		}
		return flag;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	@Override
	public int updateOrderEndConfirmTime(Long orderId, Date confirmStartTime)
			throws ManagerException {
		int refundNum = 0;
		try {
			
			OrderDO orderDO = orderQueryDAO.queryOrder(orderId);
			if(orderDO==null){
				log.error("[OrderUpDateManagerImpl#updateOrderEndConfirmTime] 退款结束修改订单收货时间出错,无次订单!orderId--".concat(String.valueOf(orderId)));
				return refundNum;
			}
			orderDO.setConfirmStartTime(confirmStartTime);
			orderDO.setIsRefund(OrderConstant.IS_REFUND_NO);
			refundNum = orderUpDdateDAO.updateOrderConfirmTime(orderDO);
		} catch (DaoException e) {
			log.error("[OrderUpDateManagerImpl#updateOrderEndConfirmTime] error parms orderId--".concat(String.valueOf(orderId)),e);
			throw new ManagerException (e);
		}
		return refundNum;
	}

	@Override
	public int updateOrderStartConfirmTime(Long orderId, Date confirmStartTime,
			Integer residueTimeHour) throws ManagerException {
		int refundNum = 0;
		try {
			
			OrderDO orderDO = orderQueryDAO.queryOrder(orderId);
			if(orderDO==null){
				log.error("[OrderUpDateManagerImpl#updateOrderEndConfirmTime] 退款开始修改订单收货时间出错,无次订单!orderId--".concat(String.valueOf(orderId)));
				return refundNum;
			}
			orderDO.setResidueTimeHour(residueTimeHour);
			orderDO.setConfirmStartTime(confirmStartTime);
			orderDO.setIsRefund(OrderConstant.IS_REFUND_YES);
			refundNum = orderUpDdateDAO.updateOrderConfirmTime(orderDO);
		} catch (DaoException e) {
			log.error("[OrderUpDateManagerImpl#updateOrderEndConfirmTime] error parms orderId--".concat(String.valueOf(orderId)),e);
			throw new ManagerException (e);
		}
		return refundNum;
	}

	@Override
	public boolean upRefundPriceAndRefundStatus(long orderItemId,
			int orderRefundState, Long refundPrice) throws ManagerException {
		try {
			OrderItemDO orderItemDO = OrderItemDO.createRefundPriceOrderItemDO(orderRefundState,refundPrice,orderItemId);
			return orderUpDdateDAO.upRefundOrderItem(orderItemDO)>0;
		} catch (DaoException e) {
			log.error("OrderCustomerManager.upRefundState error", e);
			throw new ManagerException(
					"OrderCustomerManager.upRefundState error", e);
		}
	}



	@Override
	public int updateOrderPostPayState(Long[] orderId,String orderAttributes) throws ManagerException{
		int intResult = 0;
		try {
			String attributes= null;
			if(StringUtil.isBlank(orderAttributes)){
				attributes = OrderttributesEnum.IS_POST_PAY.getFeatureName().concat(OrderConstant.SPLITATTRIBUTES).concat("1");
			}else{
				attributes = orderAttributes.concat(OrderConstant.SPLITKEY).concat(OrderttributesEnum.IS_POST_PAY.getFeatureName().concat(OrderConstant.SPLITATTRIBUTES).concat("1"));
			}
			 
			Map map = new HashMap();
			map.put("orderId", orderId);
			map.put("orderAttributes", attributes);
			intResult = orderUpDdateDAO.updateOrderAttribute(map);
		} catch (DaoException e) {
			log.error("[Event OrderCustomerManager#updateOrderPostPayState ] error:", e);
			throw new ManagerException("OrderCustomerManager.updateOrderPostPayState", e);
		}
		return intResult;
	}
}
