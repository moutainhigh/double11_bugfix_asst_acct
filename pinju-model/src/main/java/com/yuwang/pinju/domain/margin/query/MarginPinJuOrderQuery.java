package com.yuwang.pinju.domain.margin.query;

import com.yuwang.pinju.domain.Paginator;

/**
 * 品聚操作流水Query
 * @author Administrator
 *
 */
public class MarginPinJuOrderQuery extends Paginator{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 涉及会员ID
	 */
	private Long invMemberId;
	/**
	 * 涉及会员昵称
	 */
	private Long invMemberNick;
	/**
	 * 维权编号
	 */
	private Long rightsId; 
	/**
	 * 退款编号
	 */
	private Long refundId; 

	public Long getInvMemberId() {
		return invMemberId;
	}

	public void setInvMemberId(Long invMemberId) {
		this.invMemberId = invMemberId;
	}

	public Long getInvMemberNick() {
		return invMemberNick;
	}

	public void setInvMemberNick(Long invMemberNick) {
		this.invMemberNick = invMemberNick;
	}

	public Long getRightsId() {
		return rightsId;
	}

	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}
	
}
