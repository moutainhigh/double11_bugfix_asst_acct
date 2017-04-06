package com.yuwang.pinju.core.refund.ao.impl;

import java.util.List;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.refund.ao.RefundCheckAO;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;

public class RefundCheckAOImpl extends BaseAO implements RefundCheckAO{
	private RefundCheckManager refundCheckManager;
	private OrderQueryManager orderQueryManager;
	private RefundManager refundManager;
	/**
	 * 是否需要执行平台退款
	 * <p>全额退款，并且所有的退款都协议达成</p>
	 * 
	 * @param refundId 退款id
	 * @return true 全额退款，并且所有退款为协议达成状态
	 */
	public Result needPlatRefund(Long refundId){
		Result result = new ResultSupport();
		try {
			Boolean _needPlatRefund = refundCheckManager.needPlatRefund(refundId);
			result.setModel("needPlatRefund", _needPlatRefund);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundCheckAOImpl#needPlatRefund] 是否需要执行平台退款", e);
		}catch(Exception e){
			result.setSuccess(false);
			log.error("Event=[RefundCheckAOImpl#needPlatRefund] error", e);
		}
		return result;
	}

	
	/**
	 * 判断是否是最后一个退款订单 
	 * @return 如果是最后一个退款  申请金额 + 运费(true) , 否则最大金额是商品的价格 (false)
	 * 
	 */
	@Override
	public boolean isLastRefundOrder(Long orderId,RefundDO _refundDO) {
		// TODO Auto-generated method stub
		 Integer order_item_num=0;
		 Integer refund_item_num=0;
		 boolean flag = false;
		try {
			List<OrderItemDO> order_list=orderQueryManager.queryOrderItemList(orderId);
			if(order_list != null){
				order_item_num=order_list.size();
			}
			//如果只有一个子订单  说明是最后一个
			if(order_item_num == 1) return true;
			
			List<RefundDO> refund_list=refundManager.queryRefundByOrderId(orderId);
			if(refund_list !=null){
				refund_item_num = refund_list.size();
			}
			//如果退款记录比子订单 > = 2  不是最后一个
			if(order_item_num - refund_item_num > 1){
				return false;
			}
			/**
			 * 如果剩下最后一个子订单:
			 * 1.判断其它的退款单是否是全额退款: 
			 * 2.全额并且不是关闭的       return true
			 * 3.不是全额  		     return false
			 * 4.全额有关闭的退款单         return false
			 * 5.不是全额有关闭		 return false
			 */
			Long refundId = null;
			if (_refundDO != null) {
				refundId = _refundDO.getId();
			}
			if(order_item_num - refund_item_num == 1){
				flag = checkISAllRefund(refund_list,refundId);
			}
			/**
			 *  1.判断其它的退款单是否是全额退款: 
			 * 2.全额并且不是关闭的       return true
			 * 3.不是全额  		     return false
			 * 4.全额有关闭的退款单         return false
			 * 5.不是全额有关闭		 return false
			 */
			if(order_item_num - refund_item_num == 0){
				flag = checkISExits(refund_list,refundId);
			}
			
		} catch (ManagerException e) {
			flag = false;
			log.error("Event=[RefundApplyAOImpl#isLastRefundOrder] ###判断是否只最后一个退款订单失败", e);
		}
		return flag;
	}
	
	/**
	 * <p>Description: 所有退款都申请完，修改申请退款金额是的判断</p>
	 * @param refund_list
	 * @param refundId
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean checkISExits(List<RefundDO> refund_list, Long refundId) throws ManagerException{
		if(refundId != null){
			for(RefundDO refundDO : refund_list){
				if(refundDO.getId().compareTo(refundId) != 0){
					if(refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED)== 0){
						return false;
					}
					OrderItemDO orderItem = orderQueryManager.getOrderItemDOById(refundDO.getOrderItemId());
					Long ItemOrderSum =MoneyUtil.getDollarToCent(orderItem.getItemTotalPriceByYuan());
					if(refundDO.getApplySum().compareTo(ItemOrderSum) != 0){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * <p>Description:只剩下一个OrderItem没有申请退款的判断</p></p>
	 * @param refund_list
	 * @param refundId
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean checkISAllRefund(List<RefundDO> refund_list,Long refundId) throws ManagerException{
		if(refundId == null){
			for(RefundDO refundDO : refund_list){
				if(refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED)== 0){
					return false;
				}
				OrderItemDO orderItem = orderQueryManager.getOrderItemDOById(refundDO.getOrderItemId());
				Long ItemOrderSum =MoneyUtil.getDollarToCent(orderItem.getItemTotalPriceByYuan());
				if(refundDO.getApplySum().compareTo(ItemOrderSum) != 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public long getNeedPaySellerSum(Long orderId) {
		long sum = 0L;
		try {
			List<RefundDO> refund_list=refundManager.queryRefundByOrderId(orderId);
			if(refund_list == null || refund_list.size() ==0){
				return sum;
			}else{
				for(RefundDO ref:refund_list){
					if(ref.getRefundState().compareTo(RefundDO.STATUS_CLOSED) !=0){
						sum += ref.getApplySum();
					}
				}
			}
		} catch (ManagerException e) {
			log.error("RefundCheckAOImpl.getNeedPaySellerSum() error", e);
			sum = 0;
		}
		return sum;
	}
	
	public void setRefundCheckManager(RefundCheckManager refundCheckManager) {
		this.refundCheckManager = refundCheckManager;
	}


	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}


	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
}
