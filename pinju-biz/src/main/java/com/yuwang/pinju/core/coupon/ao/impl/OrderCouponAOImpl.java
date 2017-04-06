package com.yuwang.pinju.core.coupon.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;

public class OrderCouponAOImpl extends BaseAO implements OrderCouponAO{
	
	private CouponManager couponManager;

	/**
	 * 获取订单优惠信息
	 * 
	 * @param  orderId
	 * @return
	 */
	public String getCouponMoneyByYuan(Long orderId){
		TradeCouponDO tradeCouponDO = null;
		
		try{
			tradeCouponDO = couponManager.getTradeCouponDOByOrderId(orderId);
			
			//没有优惠
			if(tradeCouponDO == null){
				return "0";
			}
			
			return tradeCouponDO.getCouponMoneyByYuan();
		}catch (Exception e) {
			log.error("OrderCouponAOImpl#getCouponMoneyByYuan execute error:" + e);
			return "0";
		}
		
	}
	
	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	@Override
	public TradeCouponDO getCouponDOByOrderId(Long orderId) {
		try {
			return couponManager.getTradeCouponDOByOrderId(orderId);
		} catch (ManagerException e) {
			log.error("OrderCouponAOImpl#getCouponDOByOrderId execute error:" + e);
		}
		return null;
	}

}
