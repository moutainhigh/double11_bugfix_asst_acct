package com.yuwang.pinju.domain.margin;

import com.yuwang.pinju.domain.BaseDO;

/**  
 * @Project: pinju-model
 * @Description: 请求盛通.请求体
 * @author 石兴 shixing@zba.com
 * @date 2011-8-1 下午04:30:57
 * @update 2011-8-1 下午04:30:57
 * @version V1.0  
 */
public class MarginQueryReq extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4812126381498151875L;

	private String memberId;
	
	private String memberIdType;
	
	private String marginAmount;
	
	private String currency;

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberIdType(String memberIdType) {
		this.memberIdType = memberIdType;
	}

	public String getMemberIdType() {
		return memberIdType;
	}

	public void setMarginAmount(String marginAmount) {
		this.marginAmount = marginAmount;
	}

	public String getMarginAmount() {
		return marginAmount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}
	
	
}
