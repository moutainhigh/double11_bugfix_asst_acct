package com.yuwang.pinju.core.coupon.dao;

import com.yuwang.pinju.core.common.DaoException;

public interface CouponTimeoutDAO {
	/**
	 * 失效超时的优惠券批次
	 * 
	 * @return true表示操作成功
	 */
	public Integer invalidateTimeoutCouponBatch()throws DaoException;
	
	/**
	 * 失效超时的优惠券
	 * 
	 * @return true表示操作成功
	 */
	public Integer invalidateTimeoutCoupon()throws DaoException;
}
