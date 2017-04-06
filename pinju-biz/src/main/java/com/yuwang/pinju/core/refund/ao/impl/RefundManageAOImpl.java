package com.yuwang.pinju.core.refund.ao.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.LeftDate;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;

public class RefundManageAOImpl implements RefundManageAO {
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private VouchQueryManager vouchQueryManager;
	private RefundManager refundManager;
	private RefundCheckManager refundCheckManager;
	
	private OrderQueryManager orderQueryManager;
	private OrderBusinessManager orderBusinessManager;
	private OrderUpDateManager orderUpDateManager;


	/**
     * 根据 订单编号 查询支付记录
     */
    public Result selectOrderPayByOrderId(Long orderId){
		Result result = new ResultSupport();
		try {
			//支付订单信息
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);
			
			result.setModel("vouchPayDO", vouchPayDO);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#selectOrderPayByOrderId] 根据 订单编号 查询支付记录", e);
		}catch(Exception e){
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#selectOrderPayByOrderId] error", e);
		}
		
		return result;
    }
    
    /**
     * 根据 订单编号 查询退款总额
     */
    public Result getRefundFee(Long orderId){
		Result result = new ResultSupport();
		try {
			Long sum = refundManager.getRefundFee(orderId);
			
			result.setModel("refundFee", sum);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#getRefundFee] 根据 订单编号 查询退款总额", e);
		}catch(Exception e){
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#getRefundFee] error", e);
		}
		
		return result;
    }
	
    /**
     * 根据 订单编号 查询平台退款信息
     */
    public Result getPlatformRefundParam(Long orderId){
		Result result = new ResultSupport();
		try {
			//支付信息
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);

			String orderPayId = vouchPayDO.getOrderPayId();
			
			Long refundFee = refundManager.getRefundFee(orderId);
			
			String transactionId = vouchPayDO.getOutPayId();
			Long totalFee = vouchPayDO.getRealPayMentamount();
			
			PlatformRefundParamDO sendParamDO = PlatformRefundParamDO.createRefundParamDO(transactionId, orderPayId, refundFee ,totalFee);
			
			result.setModel("platformRefundParam", sendParamDO);
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#getPlatformRefundParam] 根据 订单编号 查询平台退款信息", e);
		}catch(Exception e){
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#getPlatformRefundParam] error", e);
		}
		
		return result;
    }
    
	/**
	 * 发起退款，更新订单确认收货时间和订单的退款状态
	 */
	@Override
	public Result startRefund(RefundDO refundDO) {
		Result result = new ResultSupport();
		final long _orderId = refundDO.getOrderId();
		final long refundId = refundDO.getId();

		try {
			// 1.查询订单物流记录表：订单是否已发货
			OrderLogisticsDO orderLogisticsDO = orderBusinessManager
					.queryOrderLogisticsByOrderId(_orderId);

			if (orderLogisticsDO.getLogisticsState().compareTo(
					OrderLogisticsDO.LOGISTICS_STATE_2) != 0) {
				
				//发货前申请退款、维护trade_order.is_refund字段
				if(orderUpDateManager.updateOrderStartConfirmTime(_orderId,null, null)>0){
					result.setSuccess(true);
					return result;
				}
				
				result.setSuccess(false);
				return result;
			}

			// 2. 查询退款记录表：同一个主订单下是否有未处理成功的退款记录
			boolean flag = refundCheckManager.getRefundingCount(_orderId, refundId) > 0;

			
			if (flag) {
				result.setSuccess(false);
				return result;
			}

			// 3. 查询主订单表 获取当前的确认收货的开始时间,剩余时间/小时
			OrderDO orderDO = orderQueryManager.getOrderDOById(_orderId);

			if (EmptyUtil.isBlank(orderDO.getResidueTimeHour())
					|| orderDO.getConfirmStartTime() == null) {
				result.setSuccess(false);
				return result;
			}

			// 倒计时暂停时间
			Date newConfirmStartTime = refundDO.getGmtCreate();

			LeftDate leftDate = com.yuwang.pinju.core.util.DateUtil.getLeftDate(orderDO
					.getConfirmStartTime(),newConfirmStartTime);
			
			// 倒计时剩余时间（小时）
			Long newconfirmTimeHour = orderDO.getResidueTimeHour() - (leftDate.getDay()*24 + leftDate.getHour());
			
			log.debug("newconfirmTimeHour = "+ newconfirmTimeHour);
			// 4. 执行更新
			flag = orderUpDateManager.updateOrderStartConfirmTime(_orderId,
					newConfirmStartTime, newconfirmTimeHour.intValue()) > 0;

			if (!flag) {
				result.setSuccess(false);
				return result;
			}

			result.setSuccess(true);

		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error(
					"Event=[RefundManageAOImpl#startRefund] 发起退款，更新订单确认收货时间和订单的退款状态",
					e);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[RefundManageAOImpl#startRefund] error", e);
		}

		return result;
	}

	/**
	 * 结束退款，更新订单确认收货时间和订单的退款状态
	 */
	@Override
	public Result endRefund(RefundDO refundDO) {

		Result result = new ResultSupport();
		try {
			long _orderId = refundDO.getOrderId();
			final long refundId = refundDO.getId();
			
			// 1. 查询退款记录表：同一个主订单下是否有未处理成功的退款记录
			boolean flag = refundCheckManager.getRefundingCount(_orderId, refundId) > 0;

			if (flag) {
				result.setSuccess(false);
				return result;
			}

			// 2. 更新对应的的主订单表记录
			// 倒计时开始时间
			Date newConfirmStartTime = refundDO.getStateModified();

			flag = orderUpDateManager.updateOrderEndConfirmTime(_orderId,
					newConfirmStartTime) > 0;

			if (!flag) {
				result.setSuccess(false);
				return result;
			}

			result.setSuccess(true);

		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error(
					"Event=[RefundManageAOImpl#endRefund] 结束退款，更新订单确认收货时间和订单的退款状态",
					e);
		}

		return result;
	}
    
    public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setRefundCheckManager(RefundCheckManager refundCheckManager) {
		this.refundCheckManager = refundCheckManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderBusinessManager(OrderBusinessManager orderBusinessManager) {
		this.orderBusinessManager = orderBusinessManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	
}
