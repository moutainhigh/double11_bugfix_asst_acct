package com.yuwang.pinju.domain.trade;

import java.util.List;

/**
 * @Project: pinju-model
 * @Description: 财付通合并支付发送参数DO
 * @author shihongbo
 */
public class TenpayMergepayParamDO {
	/**
	 * 请求笔数
	 */
	private Integer requestNo;
	
	/**
	 * 商户号
	 */
	private String spid;
	
	/**
	 * 支付请求明细
	 */
	private List<String> request;

	/**
	 * 页面跳转URL
	 */
	private String returnUrl;
	
	/**
	 * MD5加密摘要
	 */
	private String sign;

	public Integer getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(Integer requestNo) {
		this.requestNo = requestNo;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	public List<String> getRequest() {
		return request;
	}

	public void setRequest(List<String> request) {
		this.request = request;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
