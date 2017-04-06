package com.yuwang.pinju.web.module.pay.action;

import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.trade.ao.TenpayMergepayAO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * <p>财付通合并支付Action</p>
 * 
 * @author:shihongbo
 */
public class MergePayAction extends BaseAction {

	private static final long serialVersionUID = -3875222488017592621L;

	private List<TenpaySinglepayParamDO> payList;

	private TenpayMergepayAO tenpayMergepayAO;
	
	//支付网关
	private String postUrl;
	
	//是否超过10笔
	private Boolean requestNoError;
	
	//请求笔数
	private Integer requestNO;

	//商户号
	private String spid;
	
	//支付请求明细
	private List<String> requestList;
	
	//支付请求明细
	List<TenpaySinglepayParamDO> paramList;
	
	//页面跳转url
	private String returnUrl;
	
	//总金额
	private String totalFee;

	//签名
	private String sign;
	
	
	
	public String inputSingleOrder(){
		return SUCCESS;
	}
	
	public String tenpayMergepaySendOrder(){
		if(payList == null){
			return ERROR;
		}
		
		Result result = tenpayMergepayAO.createMergepayParam(payList);
		
		//支付网关
		postUrl = (String)result.getModel("postUrl");
		
		//支付请求明细
		paramList = (List<TenpaySinglepayParamDO>)result.getModel("paramList");
		
		//是否超过10笔
		requestNoError = (Boolean)result.getModel("requestNoError"); 
		
		//请求笔数
		requestNO = (Integer)result.getModel("request_no");
		
		//商户号
		spid = (String)result.getModel("spid");
		
		//支付请求明细
		requestList = (List<String>)result.getModel("requestList");
		
		//页面跳转url
		returnUrl = (String)result.getModel("return_url");
		
		//总金额
		totalFee = (String)result.getModel("totalFee");
		
		//签名
		sign = (String)result.getModel("sign");
		
		return SUCCESS;
	}
	
	public List<TenpaySinglepayParamDO> getParamList() {
		return paramList;
	}

	public String getPostUrl() {
		return postUrl;
	}
	
	public Boolean getRequestNoError() {
		return requestNoError;
	}
	
	public List<TenpaySinglepayParamDO> getPayList() {
		return payList;
	}

	public void setPayList(List<TenpaySinglepayParamDO> payList) {
		this.payList = payList;
	}

	public void setTenpayMergepayAO(TenpayMergepayAO tenpayMergepayAO) {
		this.tenpayMergepayAO = tenpayMergepayAO;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public List<String> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<String> requestList) {
		this.requestList = requestList;
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
	
	public Integer getRequestNO() {
		return requestNO;
	}

	public void setRequestNO(Integer requestNO) {
		this.requestNO = requestNO;
	}
	
	public String getTotalFee() {
		return totalFee;
	}

}
