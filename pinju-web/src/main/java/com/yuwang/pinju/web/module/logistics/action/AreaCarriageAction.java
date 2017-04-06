package com.yuwang.pinju.web.module.logistics.action;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.logistics.ao.LogisticsAreaCarriageAO;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public class AreaCarriageAction implements Action {
	
	private Result result;
	private List<LogisticsAreaCarriageDO> lacList;

	
	

	@SuppressWarnings("unchecked")
	public String execute() {
		LogisticsAreaCarriageDO ldo = new LogisticsAreaCarriageDO();
		result = logisticsAreaCarriageAO.selectLogisticsAreaCarriageList(ldo);
		lacList = (List<LogisticsAreaCarriageDO>) result.getModel("list");
		
		return SUCCESS;
	}
	
	private LogisticsAreaCarriageAO logisticsAreaCarriageAO;
	public void setLogisticsAreaCarriageAO(
			LogisticsAreaCarriageAO logisticsAreaCarriageAO) {
		this.logisticsAreaCarriageAO = logisticsAreaCarriageAO;
	}
}
