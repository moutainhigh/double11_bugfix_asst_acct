package com.yuwang.pinju.core.constant.trade;

/**  
 * @Project: pinju-model
 * @Description: 即时到账交易常量类
 * @author 石兴 shixing@zba.com
 * @date 2011-8-13 下午04:32:03
 * @update 2011-8-13 下午04:32:03
 * @version V1.0  
 */
public class DirectPayConstant {
	
	/**
	 * 操作类型：充值
	 */
	public static final int OPERATE_TYPE_ADD = 100;
	
	/**
	 * 操作类型：扣款
	 */
	public static final int OPERATE_TYPE_MINUS = 200;

	/**
	 * 即时到账业务类型.保证金业务(1000)
	 */
	public final static int BIZ_TYPE_MARGIN = 1000;
	
	/**
	 * 即时到账业务类型.一口价业务(200)
	 */
	public final static int BIZ_TYPE_PRICE = 200;
	
	/**
	 * 即时到账业务类型.限时折扣业务(300)
	 */
	public final static int BIZ_TYPE_LIMITDISCOUNT = 300;
	
	/**
	 * 即时到账业务类型.团购业务(400)
	 */
	public final static int BIZ_TYPE_GROUP = 400;
	
	/**
	 * 即时到账业务类型.维权业务(500)
	 */
	public final static int BIZ_TYPE_RIGHTS = 500;
	
	/**
	 * 消保类型.基础消保(100)
	 */
	public final static int MARGIN_TYPE_BASE = 100;
	
	/**
	 * 消保类型.高级消保(200)
	 */
	public final static int MARGIN_TYPE_HIGH = 200;
	
	/**
	 * 基础消保对应的商品名称
	 */
	public final static String MARGIN_TITLE_BASE = "基础消保";
	
	/**
	 * 高级消保对应的商品名称
	 */
	public final static String MARGIN_TITLE_SENIOR = "高级消保";
	
	/**
	 * 基础消保对应的商品ID
	 */
	public final static long MARGIN_ITEM_ID_BASE = 201108001L;
	
	/**
	 * 高级消保对应的商品ID
	 */
	public final static long MARGIN_ITEM_ID_SENIOR = 201108002L;
	
	/**
	 * 即时支付状态:等待支付
	 */
	public final static int DIRECTPAY_PAY_STATUS_UNPAID = 101;
	
	/**
	 * 即时支付状态:支付成功
	 */
	public final static int DIRECTPAY_PAY_STATUS_SUCCESS = 102;
	
	/**
	 * 即时支付状态:支付失败
	 */
	public final static int DIRECTPAY_PAY_STATUS_FAIL = 103;
	
	/**
	 * 即时支付状态:支付关闭
	 */
	public final static int DIRECTPAY_PAY_STATUS_CLOSE = 104;
	
	
	/**
	 * 即时支付订单状态:等待支付
	 */
	public final static int DIRECTPAY_ORDER_STATUS_UNPAID = 101;
	
	/**
	 * 即时支付订单状态:交易成功
	 */
	public final static int DIRECTPAY_ORDER_STATUS_SUCCESS = 102;
	
	/**
	 * 即时支付订单状态:交易失败
	 */
	public final static int DIRECTPAY_ORDER_STATUS_FAIL = 103;
	
	/**
	 * 即时支付订单状态:交易关闭
	 */
	public final static int DIRECTPAY_ORDER_STATUS_CLOSE = 104;
	
	/**
	 * 保证金回调后跳转actionName
	 */
	public final static String MARGIN_BACK_GO_ACTION = "margin_back_go_action";
	
	
	
}
