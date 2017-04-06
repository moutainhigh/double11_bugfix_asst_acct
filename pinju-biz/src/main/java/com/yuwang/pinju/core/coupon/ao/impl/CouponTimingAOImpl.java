package com.yuwang.pinju.core.coupon.ao.impl;

import java.util.Date;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.ao.CouponTimingAO;
import com.yuwang.pinju.core.coupon.manager.CouponTimeoutManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

public class CouponTimingAOImpl extends BaseAO implements CouponTimingAO{
	
	private CouponTimeoutManager couponTimeoutManager;
	

	/**
	 * 优惠券超时
	 */
	public Boolean couponTimeout(){
		log.info("CouponTimingAOImpl.couponTimeout begin exexute at " + new Date());
		try{
			couponTimeoutManager.invalidateTimeoutCoupon();
			log.info("CouponTimingAOImpl.couponTimeout couponTimeoutManager.invalidateTimeoutCoupon() success. ");
			
			couponTimeoutManager.invalidateTimeoutCouponBatch();
			log.info("CouponTimingAOImpl.couponTimeout couponTimeoutManager.invalidateTimeoutCouponBatch() success. ");
			
		}catch (Exception e) {
			log.error("CouponTimingAOImpl.couponTimeout error", e);
			return false;
		}
		
		log.info("CouponTimingAOImpl.couponTimeout end exexute at " + new Date());
		
		return true;
		
	}

	
	public void setCouponTimeoutManager(CouponTimeoutManager couponTimeoutManager) {
		this.couponTimeoutManager = couponTimeoutManager;
	}

}