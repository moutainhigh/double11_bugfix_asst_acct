package com.yuwang.pinju.domain.trade;

import java.util.List;

/**
 * @Project: pinju-model
 * @Description: 财付通合并支付接收参数DO
 * @author shihongbo
 */
public class TenpayMergepayRecvParamDO {

	/**
	 * 请求笔数
	 */
	private Integer returnNo;
	
	/**
	 * 商户号
	 */
	private String spid;

	/**
	 * 支付请求明细
	 */
	private List<String> request;
	
	/**
	 * MD5加密摘要
	 */
	private String sign;
	
	public String getRecvContent(){
		String s = "return_no=" + returnNo + "&spid=" + spid;
		
		int i = 0;
		for(String rs:request){
			if(i == 0)
				s += "&request=" + rs;
			else
				s += "&request=" + i + rs;
			
			i++;
		}
		
		s += "&sign=" + sign;
		
		return s;
	}
	
	public List<String> getRequest() {
		return request;
	}

	public void setRequest(List<String> request) {
		this.request = request;
	}

	public Integer getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(Integer returnNo) {
		this.returnNo = returnNo;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
