package com.yuwang.pinju.core.coupon.ao;

import com.yuwang.pinju.domain.coupon.TradeCouponDO;

public interface OrderCouponAO {
	/**
	 * 获取订单优惠信息
	 * 
	 * @param  orderId
	 * @return
	 */
	public String getCouponMoneyByYuan(Long orderId);
	
	/**
	 * Created on 2011-11-29
	 * <p>Description: 获取订单使用优惠券信息</p>
	 * @param orderId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponDO getCouponDOByOrderId(Long orderId);
	
}
