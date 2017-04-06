package com.yuwang.pinju.core.coupon.manager.impl;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.CouponTimeoutDAO;
import com.yuwang.pinju.core.coupon.manager.CouponTimeoutManager;

public class CouponTimeoutManagerImpl implements CouponTimeoutManager{
	private CouponTimeoutDAO couponTimeoutDAO;
	
	/**
	 * 失效超时的优惠券批次
	 * 
	 * @return true表示操作成功
	 */
	public Boolean invalidateTimeoutCouponBatch()throws ManagerException{
		try {
			couponTimeoutDAO.invalidateTimeoutCouponBatch();
			return true;
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponTimeoutManagerImpl#invalidateTimeoutCouponBatch]失效超时的优惠券批次:", e);
		}
	}
	
	
	/**
	 * 失效超时的优惠券
	 * 
	 * @return true表示操作成功
	 */
	public Boolean invalidateTimeoutCoupon()throws ManagerException{
		try {
			couponTimeoutDAO.invalidateTimeoutCoupon();
			return true;
		} catch (DaoException e) {
			throw new ManagerException("Event=[CouponTimeoutManagerImpl#invalidateTimeoutCoupon]失效超时的优惠券:", e);
		}
	}

	
	public void setCouponTimeoutDAO(CouponTimeoutDAO couponTimeoutDAO) {
		this.couponTimeoutDAO = couponTimeoutDAO;
	}

}
