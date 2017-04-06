package com.yuwang.pinju.core.order.manager.impl.httpLogsisticsServce;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class LogisticsServiceHundDO {
	/**
	 * 调用状态  1 为成功，其它失败
	 */
	public static final String STATUS_1 = "1";
	
	private String status;
	private String message;
	private List<HashMap<String,String>> step;
	private String state;
	
	/**
	 * 物流公司URL
	 */
	private String logisticsUrl;
	/**
	 * 调用是否成功
	 */
	public boolean isSuccess() {
		return STATUS_1.equals(status);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<HashMap<String, String>> getStep() {
		return step;
	}

	public void setStep(List<HashMap<String, String>> step) {
		this.step = step;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLogisticsUrl() {
		return logisticsUrl;
	}
	public void setLogisticsUrl(String logisticsUrl) {
		this.logisticsUrl = logisticsUrl;
	}
	
	public String toString (){
		StringBuffer bf = new StringBuffer("");
		for (HashMap<String,String> hash: step){
			Iterator<Entry<String, String>> itor = hash.entrySet().iterator();  
			  bf.append("{");
			while(itor.hasNext()){
				  Entry<String, String> e = itor.next();  
				  bf.append(e.getKey()+":");
				  bf.append(e.getValue());
				  bf.append(",");
			}
			bf.append("},\n");
		}
		return "{message:"+message+",status:"+status+",state:"+state+",data:["+bf.toString()+"]}";
	}
}
