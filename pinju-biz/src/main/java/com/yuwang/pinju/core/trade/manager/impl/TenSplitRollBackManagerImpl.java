package com.yuwang.pinju.core.trade.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.core.trade.manager.RefundLogManager;
import com.yuwang.pinju.core.trade.manager.TenSplitRollBackManager;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;


public class TenSplitRollBackManagerImpl implements TenSplitRollBackManager {
	
	private DataSourceTransactionManager zizuTransactionManager;
	private RightsWorkOrderManager rightsWorkOrderManager;
	private VouchCreateManager vouchCreateManager;
	private OrderUpDateManager orderUpDateManager;
	private RefundManager refundManager;
	private OrderQueryManager orderQueryManager;
	private RefundLogManager refundLogManager;
	
	@Override
	public boolean updateSplitRollbackStatus(VouchPayDO orderPay, TenSplitRollBackDO tenSplitRollbackDO, String CMDNO) throws ManagerException {
		boolean flag = false;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		
		try {
			Long refundid = new Long(0);
			
			//插入回调日志表
			PayBackLogDO payBackLogDO = new PayBackLogDO();
			payBackLogDO.setSendType(PinjuConstant.TENPAY_SPLITREFUND_CMDNO);
			payBackLogDO.setOrderPayId(orderPay.getOrderPayId());
			payBackLogDO.setOutPayId(orderPay.getOutPayId());
			payBackLogDO.setBackInfo(tenSplitRollbackDO.getPayResult()+tenSplitRollbackDO.getPayInfo());
			payBackLogDO.setPayType(PinjuConstant.TENPAY_SPLITREFUND_CMDNO);
			payBackLogDO.setCreationTime(new Date());
			vouchCreateManager.insertTradePayBackLog(payBackLogDO);
			
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderPay.getOrderId());
			//查询工单表是否已存在记录
			FinanceWorkOrderQuery rightsWorkOrderQuery = new FinanceWorkOrderQuery();
			rightsWorkOrderQuery.setOrderId(orderDO.getOrderId());
			rightsWorkOrderQuery.setBizType(RightsConstant.BIZ_TYPE_RREFUND);
			FinanceWorkOrderDO financewoDO = rightsWorkOrderManager.getFinanceWorkOrderDOByOrderId(rightsWorkOrderQuery);
			
			//如果工单表中不存在该笔退款失败记录则 1、更新退款状态  2、插入工单表  3、更新退款日志表
			if (financewoDO == null) {
				List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderPay.getOrderId());
				for (RefundDO rd : refundList) {
					if(rd.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0) {
						rd.setRefundState(RefundDO.STATUS_REFUND_FAIL);
						//更新退款表状态
						refundManager.updateRefundApplyInfo(rd);
						refundid = rd.getId();
						
						//更新子订单表退款状态
						orderUpDateManager.upRefundState(rd.getOrderItemId(), RefundDO.STATUS_REFUND_FAIL);
					}
				}
				
				FinanceWorkOrderDO financeWorkOrderDO = new FinanceWorkOrderDO();
				financeWorkOrderDO.setSellerNick(orderDO.getSellerNick());
				financeWorkOrderDO.setSellerId(orderDO.getSellerId());
				financeWorkOrderDO.setShopId(orderDO.getShopId());
				financeWorkOrderDO.setShopName(orderDO.getShopName());
				financeWorkOrderDO.setDeMargin(tenSplitRollbackDO.getRefundTotalSum()); //退给买家的总额 包含商品的钱和平台服务费从保证金扣
				
				String pinjuAccountInfo = PinjuConstant.TENPAY_PAY_PINJU_ACCOUNT.concat(":0;");
				String features = tenSplitRollbackDO.getAccountRefund();
				features = features.substring(features.indexOf("|")+1);
				features = features.replaceAll("\\^", ":");
				
				financeWorkOrderDO.setFeatures(pinjuAccountInfo.concat(features));
				financeWorkOrderDO.setBuyerId(orderDO.getBuyerId());
				financeWorkOrderDO.setBuyerNick(orderDO.getBuyerNick());
				financeWorkOrderDO.setOrderId(orderDO.getOrderId());
				financeWorkOrderDO.setOperType(RightsConstant.OPER_TYPE_REFUND);
				financeWorkOrderDO.setStatus(RightsConstant.STATUS_HANDLE_NOT);
				financeWorkOrderDO.setBizId(refundid);
				financeWorkOrderDO.setBizType(RightsConstant.BIZ_TYPE_RREFUND);
				financeWorkOrderDO.setGmtCreate(new Date());
				rightsWorkOrderManager.insertRightsWorkOrderDO(financeWorkOrderDO);
				
				//更新退款日志表状态为手工单失败
				RefundLogDO refundLogDO = refundLogManager.queryRefundLogByRefundAndCmdnoId(tenSplitRollbackDO.getRefundId(), Integer.parseInt(CMDNO));
				refundLogDO.setRefundState(RefundLogDO.REDUND_LOG_BY_HAND);
				refundLogManager.updateTradeRefundLog(refundLogDO);
			}
			
			zizuTransactionManager.commit(status);
			flag = true;
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			throw new ManagerException("execute TenSplitRollBackManagerImpl.updateSplitRollbackStatus faild:",e);
		}
		return flag;
	}

	public DataSourceTransactionManager getZizuTransactionManager() {
		return zizuTransactionManager;
	}

	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}

	public RightsWorkOrderManager getRightsWorkOrderManager() {
		return rightsWorkOrderManager;
	}

	public void setRightsWorkOrderManager(
			RightsWorkOrderManager rightsWorkOrderManager) {
		this.rightsWorkOrderManager = rightsWorkOrderManager;
	}

	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setRefundLogManager(RefundLogManager refundLogManager) {
		this.refundLogManager = refundLogManager;
	}

}
