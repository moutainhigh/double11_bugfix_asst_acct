package com.yuwang.pinju.domain.order;

import java.util.HashMap;
import java.util.List;

public class LogisticsServiceDO {
	/**
	 * 调用状态  1 为成功，其它失败
	 */
	//public static final String STATUS_1 = "1";
	
	//private String status;
	//private String msg;
	private List<HashMap<String,String>> step;
	
	private String result;
	private String resultCode;
	private String resultInfo;
	private String reason;
	private String billcode;

	/**
	 * 物流公司url
	 */
	private String logisticsUrl;
	/**
	 * 调用是否成功
	 */
	public boolean isSuccess() {
		return result.compareTo("true")==0;
		//return STATUS_1.equals(status);
	}
	
	public List<HashMap<String, String>> getStep() {
		return step;
	}
	public void setStep(List<HashMap<String, String>> step) {
		this.step = step;
	}
	public String getLogisticsUrl() {
		return logisticsUrl;
	}
	public void setLogisticsUrl(String logisticsUrl) {
		this.logisticsUrl = logisticsUrl;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
}
