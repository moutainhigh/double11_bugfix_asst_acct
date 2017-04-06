package com.yuwang.pinju.core.order.ao.impl;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.check.OrderStateManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.query.SendDeliveryVO;
import com.yuwang.pinju.domain.refund.RefundDO;


/**
 *
 * @author 杜成
 * @date   2011-6-4
 * @version 1.0
 */
public class OrderBusinessAOImpl implements OrderBusinessAO {
	
	private final static Log log = LogFactory.getLog(OrderBusinessAOImpl.class);

	private OrderBusinessManager orderBusinessManager;
	
	private OrderUpDateManager orderUpDateManager;
	
	private OrderQueryManager orderQueryManager;
	
	private OrderCheckManager orderCheckManager;
	
	private OrderStateManager orderStateManager;
	
	private RefundManager refundManager;
	/**
	 * 会员管理
	 */
	private MemberManager memberManager;

	/**
	 * 根据订单ID查询物流信息
	 * @return OrderLogisticsDO
	 * @author lixin
	 */
	@Override
	public Result queryOrderLogisticsByOrderId(Long orderId) {
		Result result = new ResultSupport();
		try {
			Object object = orderBusinessManager.queryOrderLogisticsByOrderId(orderId);
			if(object==null){
				result.setSuccess(false);
			}else {
				result.setModel("obj",object);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("OrderBusinessAOImpl.queryOrderLogisticsByOrderId:" + e);
		}
		return result;
	}




	/**
	 * 更新订单
	 * @return 成功 true
	 */
	@Override
	public Result sellCloseOrder(Map<String,Object> map) {
		Result result = new ResultSupport();
		try {
			long orderId = Long.valueOf((String)map.get("orderId"));
			String failReason = (String)map.get("failReason");
			
			// 效验订单是否属于用户
			if (!orderCheckManager.isSellerOrder(orderId,(Long)map.get("userid"))) {
				result.setSuccess(false);
				result.setResultCode(OrderConstant.PARAMETERRROR);
				return result;
			}
			
			//判断订单状态是否为“等待买家付款状态”
			if(!orderStateManager.checkOrderState((Long)map.get("userid"), orderId, OrderDO.ORDER_STATE_6)){
				result.setSuccess(false);
				result.setResultCode(OrderConstant.ORDERSTATEEERROR);
				return result;
			}
			
			if(orderUpDateManager.orderClose(orderId,failReason)){
				result.setSuccess(true);
				result.setResultCode("0");
			}
			//return orderUpDateManager.orderClose(orderId,failReason);
		} catch (ManagerException e) {
			log.error("OrderBusinessAOImpl error, " +  e);
		}
		return result;
	}
	
	/**
	 * 延长发贷时间
	 * @return 成功 true
	 */
	public Result updateRecevingDate(Long orderID, Integer day , long userId){
		Result result = new ResultSupport();
		try {
			// 效验订单是否属于用户
			if (!orderCheckManager.isSellerOrder(orderID, userId)) {
				result.setResultCode(OrderConstant.SELLERMEMBERERROR);
				result.setSuccess(false);
				return result;
			}
			
			//校验订单状态
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderID);
			if(orderDO != null){
				if(orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_3) != 0){
					result.setResultCode(OrderConstant.ORDERSTATEEERROR);
					result.setSuccess(false);
					return result;
				}
			}else {
				result.setResultCode(OrderConstant.ORDERISNO);
				result.setSuccess(false);
				return result;
			}
			
			boolean flag =  orderBusinessManager.updateRecevingDate(orderID, day);
			if(flag){
				result.setResultCode(OrderConstant.OPERATESUCCED);
				result.setSuccess(true);
				return result;
			}else {
				result.setResultCode(OrderConstant.PARAMETERRROR);
				result.setSuccess(false);
				return result;
			}
		} catch (ManagerException e) {
			log.error("OrderBusinessAOImpl error, " + e);
			result.setResultCode(OrderConstant.EXCEPTION);
			result.setSuccess(false);
			return result;
		}
	}



	@Override
	public Result inSellerDelivery(long orderId, long sellerId) {
		Result result = new ResultSupport();
			
		try {
			// 效验订单是否属于用户
			if (!orderCheckManager.isSellerOrder(orderId,sellerId)) {
				result.setSuccess(false);
				result.setResultCode(OrderConstant.PARAMETERRROR);
				return result;
			}
			
			//判断订单状态是否为“等待卖家发货状态”
			if(!orderStateManager.checkOrderState(sellerId, orderId, OrderDO.ORDER_STATE_3)){
				result.setSuccess(false);
				result.setResultCode(OrderConstant.ORDERSTATEEERROR);
				return result;
			}
			
			
			result.getModels().put("orderMap", orderQueryManager.queryOrderList(orderId));
			result.getModels().put("memberDeliveryDOList", memberManager.findMemberDeliveries(sellerId));
			result.getModels().put("logistics", orderBusinessManager.queryOrderLogisticsByOrderId(orderId));
		} catch (ManagerException e) {
			log.error("inSellerDelivery error!",e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public Result setDufDelivery(long memberId, long memberDeliveryId) {
		Result result = new ResultSupport();
		try {
			if(memberManager.setDefaultDelivery(memberDeliveryId, memberId)<= 0)
				result.setSuccess(false);
		} catch (ManagerException e) {
			log.error("setDufDelivery error!",e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	@Transactional
	public Result confirmDelivery(SendDeliveryVO sendDelivery) {
		Result result = new ResultSupport();
		try {
			if (!orderStateManager.checkOrderState(sendDelivery.getSenderMemberId(), sendDelivery.getOrderId(), OrderDO.ORDER_STATE_3)){
				log.error("Event=[OpenQueryOrderServiceImpl#confirmDelivery] 无权修改订单状态！");
				log.error("error parms OrderId-->".concat(String.valueOf(sendDelivery.getOrderId())));
				log.error("error parms optMemberId-->".concat(String.valueOf(sendDelivery.getSenderMemberId())));
				log.error(sendDelivery.toString());
				result.setSuccess(false);
				result.setModel("errorMessage","订单状态异常！");
				return result;
			}		
			if(sendDelivery.getMemberDeliveriesId()>0){
				MemberDeliveryDO memberDelivery = memberManager.getMemberDeliveryById(sendDelivery.getMemberDeliveriesId());
				sendDelivery.setSendName(memberDelivery.getReceiverName());
				sendDelivery.setSendAddress(memberDelivery.getProvince() + " " + memberDelivery.getCity() + " " + memberDelivery.getDistrict() + " " + memberDelivery.getAddress());
				sendDelivery.setSendPost(memberDelivery.getZipCode());
				sendDelivery.setSendPhone(memberDelivery.getPhone());
				sendDelivery.setSendMobilePhone(memberDelivery.getMobile());
			}
			boolean flag = orderUpDateManager.confirmDelivery(sendDelivery);
			result.setSuccess(flag);
		} catch (ManagerException e) {
			log.error("Event=[OpenQueryOrderServiceImpl#confirmDelivery]:",e);
			log.error(sendDelivery.toString());
			result.setSuccess(false);
			result.setModel("errorMessage","系统异常！");
		}
		return result;
	}
	
	
	@Override
	public Result saveMemberDelivery(MemberDeliveryDO memberDelivery) {
		Result result = new ResultSupport();
		try {
			// 地址总数是否超过10条
			final boolean flag = memberManager.countMemberDeliveries(memberDelivery
					.getMemberId()) >= MemberDeliveryDO.MAX_DELIVERY_COUNT;
			// 是否是更新
			final boolean isNew = memberDelivery.isNew();
			// 超过10条
			if (isNew && flag)
				result.setSuccess(false);
			else if (isNew)
				memberDelivery = memberManager
						.saveMemberDelivery(memberDelivery);
			else
				memberManager.updateMemberDelivery(memberDelivery);
			// 针对新增回传(新增的ID)
			result.getModels().put("memberDelivery", memberDelivery);
		} catch (ManagerException e) {
			log.error("OrderCustomerAOImpl.saveMemberDelivery error:", e);
			return this.setResult(false, result, OrderConstant.EXCEPTION);
		}
		return result;
	}
	
	/**
	 * @see 封装返回信息
	 * @param flag
	 * @param result
	 * @param resultCode
	 * @return
	 */
	private Result setResult(boolean flag, Result result, String resultCode) {
		result.setSuccess(flag);
		result.setResultCode(resultCode);
		return result;
	}
	
	@Override
	public Result isOrderRefund(Long orderId) {
		Result result = new ResultSupport();
		try {
			List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderId);
			result.setModel("refundList",refundList);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("OrderCustomerAOImpl.isOrderRefund error:", e);
		}
		return result;
	}
	
	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}


	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderStateManager(OrderStateManager orderStateManager) {
		this.orderStateManager = orderStateManager;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}




	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
   

	@Override
	public Result prolongOrderCheck(Long orderId, Long userId) {
		Result result = new ResultSupport();
		try {
			//判断订单时候属于卖家
			if(!orderCheckManager.isSellerOrder(orderId, userId)){
				result.setResultCode(OrderConstant.SELLERMEMBERERROR);
				result.setSuccess(false);
				return result;
			}
			
			//判断订单状态是否为“已发货”状态
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
			if(orderDO != null){
				if(orderDO.getOrderState().compareTo(OrderDO.ORDER_STATE_3) != 0){
					result.setResultCode(OrderConstant.ORDERSTATEEERROR);
					result.setSuccess(false);
					return result;
				}
			}else {
				result.setResultCode(OrderConstant.PARAMETERRROR);
				result.setSuccess(false);
				return result;
			}
		} catch (ManagerException e) {
			log.error("OrderCustomerAOImpl.prolongOrderCheck error:", e);
			result.setResultCode(OrderConstant.EXCEPTION);
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		return result;
	}




	@Override
	public Result sellerCloseOrderCheck(Long orderId, Long userId) {
		Result result = new ResultSupport();
		try {
			// 效验订单是否属于用户
			if (!orderCheckManager.isSellerOrder(orderId,userId)) {
				result.setSuccess(false);
				result.setResultCode(OrderConstant.PARAMETERRROR);
				return result;
			}
			
			//判断订单状态是否为“等待买家付款状态”
			if(!orderStateManager.checkOrderState(userId, orderId, OrderDO.ORDER_STATE_6)){
				result.setSuccess(false);
				result.setResultCode(OrderConstant.ORDERSTATEEERROR);
				return result;
			}
			
			result.setSuccess(true);
			return result;
		} catch (ManagerException e) {
			log.error("OrderBusinessAOImpl.sellerCloseOrderCheck error:", e);
			result.setResultCode(OrderConstant.EXCEPTION);
			result.setSuccess(false);
			return result;
		}
		
	}

}
