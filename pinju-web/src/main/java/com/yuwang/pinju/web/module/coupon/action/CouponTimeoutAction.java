package com.yuwang.pinju.web.module.coupon.action;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.coupon.ao.CouponTimingAO;

public class CouponTimeoutAction implements Action{
	private CouponTimingAO couponTimingAO;


	public String execute() throws Exception {
		couponTimingAO.couponTimeout();
		
		return null;
	}
	
	public void setCouponTimingAO(CouponTimingAO couponTimingAO) {
		this.couponTimingAO = couponTimingAO;
	}

}
