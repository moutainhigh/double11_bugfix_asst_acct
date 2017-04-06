package com.yuwang.pinju.core.common.resultcode;

/**
 * <p>Description: 退款 resultCode </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public class RefundResultCode {

	/**
	 * 签名失败
	 */
	public static final String PLATFORM_REFUND_SING_ERROR = "platform.refund.sign.error";
	/**
	 * 平台退款异常
	 */
	public static final String PLATFORM_REFUND_EXCEPTION="platform.refund.exception";
	
	/**
	 * 退款单已经处理
	 */
	public static final String PLATFORM_REFUND_ALREADY= "platform.refund.already" ;
	
	/**
	 * 退款单不存在
	 */
	
	public static final String PLATFORM_REFUND_NOT_EXIST = "platform.refund.not.exits" ;
	
	
	/**
	 * 订单不存在
	 */
	
	public static final String  PLATFORM_REFUND_ORDERITEM_NOTEXITS = "platform.refund.order.not.exits";
	
	/**
	 * 支付单不存在 或该订单没有支付
	 */
	public static final String PLATFORM_REFUND_PAY_ORDER_NOTEXITS = "platform.refund.pay.order.not.exits";
	
	/**
	 * 退款发送请求失败
	 */
	public static final String PLATFORM_REFUND_SEND_EXCEPTION = "platform.refund.send.exception";
	
	/**
	 * 退款失败 显示信息
	 */
	public static final String PLATFORM_REFUND_FAIL ="platform.refund.manaul.fail";
	
	/**
	 * 卖家拒绝退款，买家5天超时
	 */
	public static final String  BUYER_NOTREPLY_TIMEOUT = "buyer.noReply.timeout";
	
	/**
	 * 卖家同意退款，而买家在5天内不退还商品
	 */
	public static final String BUYER_NOT_RETURN_GOODS_TIMEOUT = "buyer.notReturn.goods.timeout";
}


