package com.yuwang.pinju.Constant;


/**
 * 订单相关常量
 * 
 * @author 杜成
 * @date 2011-6-7
 * @version 1.0
 */
public class OrderConstant {
	/**
	 * 标记描述分隔
	 */
	public final static String SPLITKEY = ";";
	/**
	 * 标记描述key:value分隔
	 */
	public final static String SPLITATTRIBUTES = ":";
	
	/**
	 * 抛出异常
	 */
	public static final String EXCEPTION = "3";

	/**
	 * 参数出错
	 */
	public static final String PARAMETERRROR = "1";

	/**
	 * 操作成功
	 */
	public static final String OPERATESUCCED = "2";

	/**
	 * 效验出错
	 */
	public static final String CHECKERROR = "4";

	/** 限时购买效验错误参数 ***/
	/**
	 * 限时购买时间错误
	 */
	public static final String lastTimeBuyTimeError = "11";
	
	/**
	 * 限时购买数量错误
	 */
	public static final String lastTimeBuyNumError = "12";
	
	/**
	 * 限时购买该商品已离开活动
	 */
	public static final String lastTimeBuyItemError = "13";
	
	
	/** 订单效验错误参数 ***/
	/**
	 * 没有昵称
	 */
	public static final String NICKNAMEERROR = "101";
	
	/**
	 * 订单状态错误
	 */
	public static final String ORDERSTATEEERROR = "108";
	
	/**
	 * 付款已跳至收银台
	 */
	public static final String ORDERMODIFYPRICE = "111";
	
	/**
	 * 订单不存在
	 */
	public static final String ORDERISNO = "222";
	
	/**
	 * 买家账户错误
	 */
	public static final String BUYERMEMBERERROR = "103";
	
	/**
	 * 卖家账户错误
	 */
	public static final String SELLERMEMBERERROR = "104";
	
	/**
	 * 店铺有效性错误
	 */
	public static final String SHOPERROR = "102";
	
	/**
	 * 商品有效性
	 */
	public static final String ITEMSTATEERROR = "105";

	/**
	 * 商品库存数量不够
	 */
	public static final String ITEMNUMERROR = "106";
	
	/**
	 * SKU 无效
	 */
	public static final String SKUERROR = "107";
	
	/**
	 * 日志操作类型:日志生成
	 */
	public static final String OPERATE_LOG_CREATION = "101";

	/**
	 * 日志操作类型:修改价格
	 */
	public static final String OPERATE_LOG_UPDATE_AMOUNT = "102";
	
	/**
	 * 日志操作类型:修改运费
	 */
	public static final String OPERATE_LOG_UPDATE_POST_PRICE = "201";
	
	/**
	 * 日志操作类型:发货
	 */
	public static final String OPERATE_LOG_Receving = "103";
	
	/**
	 * 日志操作类型:更新退款状态
	 */
	public static final String OPERATE_LOG_UPDATE_REFUND_STATE = "104";
	
	/**
	 * 日志操作类型:关闭订单
	 */
	public static final String OPERATE_LOG_CLOSE = "105";
	
	/**
	 * 日志操作类型:支付
	 */
	public static final String OPERATE_LOG_PAY = "106";
	
	/**
	 * 日志操作类型:确认收货
	 */
	public static final String OPERATE_LOG_RECEIVE = "107";
	
	/**
	 * 日志操作初始状态
	 */
	public static final Long OPERATE_LOG_STATE = 0L;
	

	/**
	 * 日志操作类型:订单成功
	 */
	public static final String OPERATE_LOG_SUCCESS = "107";
	
	/**
	 * 限时折扣活动折扣精度
	 */
	public static final Long ITEM_LIMIT_TIME_DISCOUNT_RATE = 1000L;
	/**
	 * 分销折扣精度
	 */
	public static final Long ITEM_CHANNEL_RATE = 100L;
	
	/**
	 * 订单无进行中退款
	 */
	public static final Integer IS_REFUND_NO = 10001;
	
	/**
	 * 订单有进行中退款
	 */
	public static final Integer IS_REFUND_YES = 10002;
	
	/**
	 * 买家取消订单原因：我不想买了   (对应数据库Code : 1001)
	 */
	public static final String ORDER_BUYER_CLOSE_REASON_NOBUY = "我不想买了";
	
	/**
	 * 买家取消订单原因：信息填写错误，重新拍   (对应数据库Code : 1002)
	 */
	public static final String ORDER_BUYER_CLOSE_REASON_ERRORINFO = "信息填写错误，重新下单";
	
	/**
	 * 买家取消订单原因：卖家缺货   (对应数据库Code : 1003)
	 */
	public static final String ORDER_BUYER_CLOSE_REASON_NOGOODS = "卖家缺货";
	
	/**
	 * 买家取消订单原因：同城见面交易   (对应数据库Code : 1004)
	 */
	public static final String ORDER_BUYER_CLOSE_REASON_FACETOFACE = "同城见面交易";
	
	/**
	 * 买家取消订单原因：其他原因   (对应数据库Code : 1005)
	 */
	public static final String ORDER_BUYER_CLOSE_REASON_OTHER = "其他原因";
	
	/**
	 * 卖家取消订单原因：买家不想买了   (对应数据库Code : 2001)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_NOBUY = "买家不想买了";
	
	/**
	 * 卖家取消订单原因：联系不到买家   (对应数据库Code : 2002)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_ERRORINFO = "联系不到买家";
	
	/**
	 * 卖家取消订单原因：暂时缺货   (对应数据库Code : 2003)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_NOGOODS = "暂时缺货";
	
	/**
	 * 卖家取消订单原因：已同城见面交易   (对应数据库Code : 2004)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_FACETOFACE = "已同城见面交易";
	
	/**
	 * 卖家取消订单原因：其他原因   (对应数据库Code : 2005)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_OTHER = "其他原因";
	
	/**
	 * 卖家取消订单原因：买家已经通过银行汇款   (对应数据库Code : 2006)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_PAY_BANK = "买家已经通过银行汇款";
	
	/**
	 * 卖家取消订单原因：买家已经通过网上银行直接付款   (对应数据库Code : 2007)
	 */
	public static final String ORDER_SELLER_CLOSE_REASON_PAY_INLINE = "买家已经通过网上银行直接付款";
	
	/**
	 * 订单关闭类型  ：买家取消订单
	 */
	public static final String ORDER_CLOSE_TYPE_BUYER = "买家取消订单";
	
	/**
	 * 订单关闭类型  ：卖家取消订单
	 */
	public static final String ORDER_CLOSE_TYPE_SELLER = "卖家取消订单";
}
