package com.yuwang.pinju.core.common.resultcode;

/**
 * @Project: pinju-biz
 * @Description:  业务层ResultCode
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午01:49:28
 * @update 2011-8-12 下午01:49:28
 * @version V1.0
 */
public interface TradeResultCode {

	String PAYMARGIN_EXCEPTION ="paymargin.exception";
	/**
	 * 订单商品所属店铺错误
	 */
	String ORDER_CHECK_SHOP_FAIL= "order.check.shop.fail";
	/**
	 * 买家账户错误
	 */
	String ORDER_CHECK_BUYERMEMBER_FAIL = "order.check.buyermember.fail";
	/**
	 * 卖家与买家同一账户
	 */
	String ORDER_CHECK_BUYER_SAME_SELLER_FAIL = "order.check.buyersameseller.fail";
	
	/**
	 * 卖家账户错误
	 */
	String ORDER_CHECK_SELLERMEMBER_FAIL = "order.check.sellermember.fail";
	
	/**
	 * 商品库存与购买数量不符
	 */
	String ORDER_CHECK_BUYNUM_FAIL = "order.check.buynum.fail";
	/**
	 * 商品有效性错误
	 */
	String ORDER_CHECK_ITEM_FAIL = "order.check.item.fail";
	/**
	 * 商品状态错误
	 */
	String ORDER_CHECK_ITEMSTATE_FAIL = "order.check.itemstate.fail";

	/**
	 * sku与对应的商品对象关系错误
	 */
	String ORDER_CHECK_SKUTOITEM_FAIL = "order.check.skutoitem.fail";
	/**
	 * 该商品限时购买活动错误
	 */
	String ORDER_CHECK_LASTTIME_FAIL = "order.check.lasttime.fail";
	/**
	 * 该商品限时购买活动已结束
	 */
	String ORDER_CHECK_LASTTIME_BUYTIME_FAIL = "order.check.lasttime.buytime.fail";
	
	/**
	 * 您已超过该商品的限时购买活动的可购买数量
	 */
   String ORDER_CHECK_LASTTIME_BUYNUM_FAIL = "order.check.lastTime.buynum.fail";
	/**
	 * 买家收货地址出错
	 */
   String ORDER_CHECK_BUYERDELIVERY_FAIL = "order.check.buyerdelivery.fail";
   /**
    * 结算生成失败
    */
   String ORDER_CHECK_EXCEPTION="order.check.exception";
   
   	/**
   	 * 效验特供码失败
   	 */
	String ORDER_CHECK_DISCOUNTCODE_FAIL= "order.check.discountcode.fail";
	
	/**
	 * 特供码活动结束
	 */
	String ORDER_CHECK_DISCOUNTCODE_END= "order.check.discountcode.end";
	
	/**
	 * 特供码已被使用(无效)
	 */
	String ORDER_CHECK_DISCOUNTCODE_NULLITY= "order.check.discountcode.nullity";
	
	
	/**
	 * 无此订单,请勿非法操作
	 */
	String DIRECTPAY_ORDER_NOT_EXITS = "directpay.order.not.exits";
	
	/**
	 * 该订单已处理
	 */
	String PAYMARGIN_ORDER_ALREADY = "paymargin.order.already";
	
	/**
	 * 验证码错误
	 */
	String ORDER_CHECK_VALIDATECODE_FAIL= "order.check.validateCode.fail";
	
	/**
	 * 卖家没有绑定第3方平台支付账户
	 */
	String ORDER_CHECK_SELLER_PAY_FAIL ="order_check_seller_pay_fail";
	
	/**
	 * 活动商品,下单活动停止或数据错误
	 */
	String ORDER_CHECK_ACTIVITY_FAIL= "order_check_activity_fail";
	
	/**
	 * 生成支付时,订单参数出错
	 */
	String PAY_CHECK_ORDER_PARMS_FAIL = "pay_check_parms_fail";
	
	/**
	 * 即时到账支付回调业务处理失败
	 */
	String PAY_PROCESS_DIRECT_EXCEPTION = "pay.process.direct.exception";
	/**
	 * 即时到账支付回调订单已处理
	 */
	String  PAY_PROCESS_ORDER_ALREADY = "pay.process.order.already";
	
	/**
	 * 优惠券已被使用
	 */
	String ORDER_CHECK_COUPON_USED_FAIL ="order_check_coupon_used_fail";
	
	
	/**
	 * 优惠券状态失效
	 */
	String ORDER_CHECK_COUPON_STATE_FAIL ="order_check_coupon_state_fail";
	
	/**
	 * 优惠券不是买家所属
	 */
	String ORDER_CHECK_COUPON_ATTRIBUTION_FAIL ="order_check_coupon_attribution_fail";
	
	
	/**
	 *子账号不能购买商品
	 */
	String ORDER_CHECK_ACCOUNT_FAIL ="order_check_account_fail";
}

