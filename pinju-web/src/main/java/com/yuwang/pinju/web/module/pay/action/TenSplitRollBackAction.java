package com.yuwang.pinju.web.module.pay.action;

import java.util.SortedMap;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.trade.ao.TenSplitRollBackAO;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;

public class TenSplitRollBackAction extends TenPayNotifyBaseAction {

	private static final long serialVersionUID = 1L;
	
	private TenSplitRollBackAO tenSplitRollBackAO;
	
	
//	private TenSplitRollBackTimingAO tenSplitRollBackTimingAO;
//	public void setTenSplitRollBackTimingAO(
//			TenSplitRollBackTimingAO tenSplitRollBackTimingAO) {
//		this.tenSplitRollBackTimingAO = tenSplitRollBackTimingAO;
//	}
//
//	public String test() {
//		tenSplitRollBackTimingAO.splitRollbackRefund();
//		return Action.SUCCESS;
//	}
	
	public String tenSplitRollBack() {
		try {
			TenSplitRollBackDO tenSplitRollBackDO = new TenSplitRollBackDO();
			tenSplitRollBackDO.setOrderId("1900000107201110310000570653");
			tenSplitRollBackDO.setTransaction_id("1900000107201110310000570653");
			Result rlt = tenSplitRollBackAO.tenSplitRollBack(tenSplitRollBackDO);
			
		} catch (Exception e) {
			log.error( e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Override
	protected SortedMap<String, String> setParameters(SortedMap<String, String> parameters) {
		parameters.put("cmdno", getString("cmdno"));
		parameters.put("pay_result", getString("pay_result"));
		parameters.put("pay_info", getString("pay_info"));
		parameters.put("bargainor_id", getString("bargainor_id"));
		parameters.put("transaction_id", getString("transaction_id"));
		parameters.put("sp_billno", getString("sp_billno"));
		parameters.put("sign", getString("sign"));
		parameters.put("refund_id", getString("refund_id"));
		parameters.put("bus_type", getString("bus_type"));
		parameters.put("bus_args", getString("bus_args"));
		parameters.put("version", getString("version"));
		return parameters;
		
	}

	@Override
	protected Integer getBizType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean notifyDelivery() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setTenSplitRollBackAO(TenSplitRollBackAO tenSplitRollBackAO) {
		this.tenSplitRollBackAO = tenSplitRollBackAO;
	}

	@Override
	protected boolean verifySign() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isTenState() {
		// TODO Auto-generated method stub
		return false;
	}


}
