package com.yuwang.pinju.core.common.resultcode;

/**
 * @Discription: 优惠券ResultCode
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.common.resultcode
 * @Title: CouponResultCode.java
 * @author: [李鑫]
 * @date 2011-11-25
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public interface CouponResultCode {
	/**
	 * 无此条优惠券，请勿非法操作！
	 */
	String COUPON_NOTEXITS = "coupon.notexits";
	
	/**
	 * 您无权限操作此优惠券，请勿非法操作！
	 */
	String COUPON_BUYERMEMBERERROR = "coupon.buyerMembererror";
	
	/**
	 * 优惠券状态已更改，您不能再对该优惠券做删除操作！
	 */
	String COUPON_DELETE_STATEERROR = "coupon.delete.stateerror";
	
	/**
	 * 网络繁忙，请稍后重试！
	 */
	String COUPON_POST_EXCEPTION = "coupon.post.exception";
	
}
