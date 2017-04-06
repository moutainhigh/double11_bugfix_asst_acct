package com.yuwang.pinju.core.coupon.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;

public interface BuyerCouponAO {
	
	/**
	 * 买家领取优惠券
	 * 
	 * @param batchId 批次id
	 * @param buyerId 买家id
	 * @param buyerNick 买家昵称
	 * @return
	 */
	public Result buyerObtainCoupon(Long batchId, Long buyerId, String buyerNick);
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 获取买家优惠券记录</p>
	 * @param queryDO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result getCoupons(TradeCouponQueryDO queryDO);
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 获取买家优惠券记录数</p>
	 * @param queryDO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int getCouponsNum(TradeCouponQueryDO queryDO);
	
	/**
	 * Created on 2011-11-24
	 * <p>Discription: 买家“删除”已使用或已过期的优惠券</p>
	 * @param couponID
	 * @param userId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result buyerDeleteCoupon(Long couponID,Long userId);
}
