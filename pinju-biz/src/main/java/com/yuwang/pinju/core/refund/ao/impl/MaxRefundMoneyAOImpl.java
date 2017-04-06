package com.yuwang.pinju.core.refund.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.refund.ao.MaxRefundMoneyAO;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

public class MaxRefundMoneyAOImpl implements MaxRefundMoneyAO{
	private VouchQueryManager vouchQueryManager;
	private OrderQueryManager orderQueryManager;
	private RefundManager refundManager;

	//取得退款总金额，去掉退款关闭状态和当前退款
	private Long getRefundingMoney(Long orderId, Long orderItemId)throws ManagerException{

		Long sum = 0L;
		List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderId);
		for(RefundDO refundDO:refundList){
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED) != 0 &&
					refundDO.getOrderItemId().compareTo(orderItemId) != 0){
				sum += refundDO.getApplySum();
			}
		}

		return sum;

	}
	
	//取得退款数,去掉退款关闭状态和当前退款
	private Integer getRefundCount(Long orderId, Long orderItemId) throws ManagerException{
		
		Integer refundCount = 0;
		List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderId);
		if(refundList != null){
			refundCount = refundList.size();
			
			for(RefundDO refundDO:refundList){
				if(refundDO.getOrderItemId().compareTo(orderItemId) == 0 || 
						refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED) == 0){
					refundCount--;
				}
			}
		}
		
		return refundCount;
	}
	
	private Integer getOrderItemCount(Long orderId)throws ManagerException{
		Integer orderItemCount = 0;
		List<OrderItemDO> orderItemList = orderQueryManager.queryOrderItemList(orderId);
		if(orderItemList != null)
			orderItemCount = orderItemList.size();
		
		return orderItemCount;
	}

	/**
	 * 当前子订单可以申请退款的最大值
	 * 
	 * @param orderId
	 * @param orderItemId
	 * 
	 * @return 可以申请退款的最大值，单位为分
	 */
	public Long getMaxRefundMoney(Long orderId, Long orderItemId) throws ManagerException{
		//获得实付款金额
		VouchPayDO query = new VouchPayDO();
		query.setOrderId(orderId);
		VouchPayDO vouchPay = vouchQueryManager.selectOrderPay(query);
		
		if(vouchPay == null)
			return 0L;
		
		//实付款金额
		Long realPayMentamount =  vouchPay.getRealPayMentamount();
		
		//已经申请的退款金额
		Long refundingMoney = getRefundingMoney(orderId, orderItemId);
		
		//可以申请退款的最大值
		Long maxRefundMoney = realPayMentamount - refundingMoney;
		
		if(maxRefundMoney.compareTo(0L) == 0 || maxRefundMoney.compareTo(0L) < 0)
			return 0L;
		
		//计算子订单价格
		
		OrderItemDO orderItem = orderQueryManager.getOrderItemDOById(orderItemId);
		//子订单价格
		Long orderItemPrice = orderItem.getTotalAmount();
		
		//可以申请退款的最大值 <= 子订单价格，直接返回
		if(maxRefundMoney.compareTo(orderItemPrice) <= 0){
			return maxRefundMoney;
		}
		
		//可以申请退款的最大值 > 子订单价格, 判断数量
		Integer refundCount = getRefundCount(orderId, orderItemId);
		Integer orderItemCount = getOrderItemCount(orderId);
		
		//修改,或者最后一笔
		if(refundCount.compareTo(orderItemCount - 1) == 0){
			return maxRefundMoney;
		}

		//不是最后一笔，返回子订单金额
		return orderItemPrice;
		
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
	
}
