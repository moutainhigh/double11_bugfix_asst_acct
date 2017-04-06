package com.yuwang.pinju.web.module.member.screen;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;

public class TestAction implements Action {
	
	private long mid;

	@Override
	public String execute() throws Exception {
		if (mid == 0) {
			mid = 100000340009000L;
		}
		PinjuControl pc = new MemberSellerQualityControl(mid);
		pc.doControl();
		return SUCCESS;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}
}
