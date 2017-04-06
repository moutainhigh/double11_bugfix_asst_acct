package com.yuwang.pinju.core.coupon.manager;

import com.yuwang.pinju.core.common.ManagerException;

public interface CouponTimeoutManager {
	/**
	 * 失效超时的优惠券批次
	 * 
	 * @return true表示操作成功
	 */
	public Boolean invalidateTimeoutCouponBatch()throws ManagerException;
	
	/**
	 * 失效超时的优惠券
	 * 
	 * @return true表示操作成功
	 */
	public Boolean invalidateTimeoutCoupon()throws ManagerException;
	
}
