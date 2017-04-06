
package com.yuwang.pinju.domain.margin.query;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;

/**
 * 卖家保证金流水记录查询Query
 * @author Administrator
 */
public class MarginSellerOrderQuery extends Paginator{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 会员保证金账户ID
	 */
	private Long memberId;  
	
	/**
	 * 会员保证金账户昵称
	 */
	private String memberNick;  
	
	/**
	 * 涉及会员Id
	 */
	private Long invMemberId;  
	
	/**
	 * 涉及会员昵称
	 */
	private String invMemberNick;
	
	private List<MarginSellerOrderDO> marginSellerOrderDOs;

	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public Long getInvMemberId() {
		return invMemberId;
	}

	public void setInvMemberId(Long invMemberId) {
		this.invMemberId = invMemberId;
	}

	public String getInvMemberNick() {
		return invMemberNick;
	}

	public void setInvMemberNick(String invMemberNick) {
		this.invMemberNick = invMemberNick;
	}

	public void setMarginSellerOrderDOs(List<MarginSellerOrderDO> marginSellerOrderDOs){
		this.marginSellerOrderDOs = marginSellerOrderDOs;
	}

	public List<MarginSellerOrderDO> getMarginSellerOrderDOs(){
		return marginSellerOrderDOs;
	}  
	
}
