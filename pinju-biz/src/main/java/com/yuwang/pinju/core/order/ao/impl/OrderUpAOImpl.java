package com.yuwang.pinju.core.order.ao.impl;


import java.util.List;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderttributesEnum;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.ao.OrderUpAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.check.OrderStateManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;

/**
 * Created on 2011-8-19
 * @see
 * <p>Discription: 订单相关更新业务</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderUpAOImpl extends BaseAO implements OrderUpAO {
	/**
	 * 订单更新
	 */
	private OrderUpDateManager orderUpDateManager;
	/**
	 * 订单查询
	 */
	private OrderQueryManager orderQueryManager;

	/**
	 * 订单效验管理
	 */
	private OrderCheckManager orderCheckManager;
	
	/**
	 * 订单状态管理
	 */
	private OrderStateManager orderStateManager;

	@Override
	public Result cancelOrder(Long orderId, Long userId, String failReason) {
		Result result = new ResultSupport();
		if (orderId == null || userId == null || failReason == null) {
			result.setSuccess(false);
			result.setResultCode(OrderConstant.PARAMETERRROR);
		}
		try {
			// 效验订单是否属于用户
			if (!orderCheckManager.isBuyerOrder(orderId, userId)) {
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
			
			if (orderUpDateManager.orderClose(orderId, failReason)) {
				result.setSuccess(true);
				result.setResultCode(OrderConstant.OPERATESUCCED);
			}
		} catch (ManagerException e) {
			log.error("OrderCustomerAOImpl.cancelOrder error:", e);
			return this.setResult(false, result, OrderConstant.EXCEPTION);
		}
		return result;
	}

	
	@Override
	public void upOrderRefundState(long orderItemId, int orderRefundState) {
		try {
			orderUpDateManager.upRefundState(orderItemId, orderRefundState);
		} catch (ManagerException e) {
			log.error("OrderCustomerAOImpl.upOrderRefundState error:", e);
		}
	}
	
	@Override
	public Result sellerUpOrderPrice(long[] orderItemId, long[] sellerMarginPrice,
			long logisticsMarginPrice , Long userId ,String operateIp,Long[] modifyBefore) {
		Result result = new ResultSupport();
		try {
			if(orderItemId == null){
				result.setSubResultCode(OrderConstant.PARAMETERRROR);
				result.setSuccess(false);
				return result;
			}
			
			OrderItemDO orderItemDO = orderQueryManager.getOrderItemDOById(orderItemId[0]);
			// 效验订单是否属于用户
			if (!orderCheckManager.isSellerOrder(orderItemDO.getOrderId(), userId)) {
				result.setSuccess(false);
				result.setResultCode(OrderConstant.CHECKERROR);
			}
			
			if(orderUpDateManager.sellerMarginPrice(orderItemId, sellerMarginPrice, logisticsMarginPrice,operateIp,modifyBefore)){
				result.setSubResultCode(OrderConstant.OPERATESUCCED);
				result.setSuccess(true);
			}	
			
		} catch (Exception e) {
			log.error("OrderUpAoImpl.sellerUpOrderPrice",e);
			return this.setResult(false, result, OrderConstant.EXCEPTION);
		}
		return result;
		
		//效验订单与卖家的关系
		//效验不成功返回Result false
		//		result.setResultCode(参数错误)
		//效验参数非空
		//效验不成功返回Result false
		//		result.setResultCode(参数错误)
		//调用manager修改
		//根据manager返回判断处理失败或成功
		//result.setResultCode(异常)
	}
	
	@Override
	public Result updateOrderPostPayState(Long[] orderId) {
		Result result = new ResultSupport();
		try {
			List<OrderDO> list = orderQueryManager.queryOrderListByOrderId(orderId);
			if(EmptyUtil.isBlank(list)){
				result.setSuccess(false);
				return result;
			}
			int intResult = 0;
			for (OrderDO orderDO : list) {
				//判断当前订单是否向到了财付通收银台
				if(!StringUtil.contains(orderDO.getOrderAttributes(), OrderttributesEnum.IS_POST_PAY.getFeatureName())){
					intResult = orderUpDateManager.updateOrderPostPayState(orderId,orderDO.getOrderAttributes());
				}
			}
			if(intResult > 0){
				result.setSuccess(true);
			}
		} catch (Exception e) {
			log.error("OrderUpAoImpl.updateOrderPostPayState",e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @see 封装返回信息
	 * @return
	 */
	private Result setResult(boolean flag, Result result, String resultCode) {
		result.setSuccess(flag);
		result.setResultCode(resultCode);
		return result;
	}
	
	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setOrderStateManager(OrderStateManager orderStateManager) {
		this.orderStateManager = orderStateManager;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}


	@Override
	public Result cancelOrderCheck(Long orderId, Long userId) {
		Result result = new ResultSupport();
		
		try {
			// 效验订单是否属于用户
			if (!orderCheckManager.isBuyerOrder(orderId, userId)) {
				result.setSuccess(false);
				result.setResultCode(OrderConstant.CHECKERROR);
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
			log.error("OrderUpAoImpl.updateOrderPostPayState",e);
			result.setSuccess(false);
			result.setResultCode(OrderConstant.EXCEPTION);
			return result;
		}		
	}
}

