package com.yuwang.pinju.core.coupon.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.coupon.dao.CouponTimeoutDAO;

public class CouponTimeoutDAOImpl extends BaseDAO implements CouponTimeoutDAO{
	private static final String COUPON_TIMEOUT_NAME_SPACE = "trade_coupon_timeout.";
	
	/**
	 * 失效超时的优惠券批次
	 * 
	 * @return true表示操作成功
	 */
	public Integer invalidateTimeoutCouponBatch()throws DaoException{
		return super.executeUpdate(COUPON_TIMEOUT_NAME_SPACE + "invalidateTimeoutCouponBatch",	null);
	}
	
	/**
	 * 失效超时的优惠券
	 * 
	 * @return true表示操作成功
	 */
	public Integer invalidateTimeoutCoupon()throws DaoException{
		return super.executeUpdate(COUPON_TIMEOUT_NAME_SPACE + "invalidateTimeoutCoupon", null);
	}
}
