package com.yuwang.pinju.core.refund.ao.impl;

import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.refund.ao.BuyerRefundLogAO;

public class BuyerRefundLogAOImpl implements BuyerRefundLogAO{

	private MemberAsstLog memberAsstLog;
	
	@Override
	public void buyerApplyCustProcess(Long refundId) {
		log("申请 " + refundId + " 退款客服介入");
	}
	
	private void log(String message){
		memberAsstLog.log("crm", "refund", message);
	}
	
	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
