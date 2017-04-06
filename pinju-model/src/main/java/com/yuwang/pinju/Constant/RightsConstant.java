/**
 * 
 */
package com.yuwang.pinju.Constant;


/**
 * @Project: pinju-model
 * @Description: 消保维权常量类
 * @author 石兴 shixing@zba.com
 * @date 2011-6-28 下午03:40:53
 * @update 2011-6-28 下午03:40:53
 * @version V1.0
 */
public class RightsConstant {
	
	/**
	 * status
	 * 已发起维权申请,等待卖家处理 
	 */
	public final static int WAIT_SELLER_HANDLE = 0;
	
	/**
	 * status
	 * 卖家已同意,（等待财务打款,等待买家发货）
	 */
	public final static int SELLER_AGREE = 1;
	
	/**
	 * status
	 * 卖家已拒绝,等待买家处理
	 */
	public final static int SELLER_REFUSE = 2;
	
	/**
	 * status
	 * 买家已发货,等待卖家确认收货
	 */
	public final static int WAIT_SELLER_RECEIVED = 3;
	
	/**
	 * status
	 * 等待客服处理
	 */
	public final static int WAIT_CUSTOMER_HANDLE = 4;
	
	/**
	 * status
	 * 维权成功(卖家处理的成功)
	 */
	public final static int RIGHTS_SUCCESS_SELLER = 5;
	
	/**
	 * status
	 * 维权关闭
	 */
	public final static int RIGHTS_CLOSE = 6;
	
	/**
	 * status
	 * 维权成功(客服仲裁的成功)
	 */
	public final static int RIGHTS_SUCCESS_CUSTOMER = 7;
	
	/**
	 * status
	 * 维权关闭(客服仲裁的关闭)
	 */
	public final static int RIGHTS_CLOSE_CUSTOMER = 8;
	
	/**
	 * status
	 * 客服裁决成功(等待财务打款)
	 */
	public final static int RIGHTS_CUSTOMER_AGREE = 9;
	
	/**
	 * status
	 * 卖家已确认收货(等待财务打款)
	 */
	public final static int RIGHTS_CONFIRM_GOODS_ALREADY = 10;

	
	/**
	 * status
	 * 买家已发货,等待卖家确认收货(客服裁决)
	 */
	public final static int WAIT_SELLER_RECEIVED_BY_CUSTOMER = 11;
	
	/**
	 * 客服裁决卖家同意(等待买家发货)
	 */
	public final static int SELLER_AGREE_BY_CUSTOMER = 12;
	
	
	/**
	 * 买家发货状态，2-买家已发货
	 */
	public final static int RETURN_GOODS_ALREADY = 2;
	
	/**
	 * 买家发货状态，5-卖家已确认收货
	 */
	public final static int RETURN_GOODS_AGREE = 5;
	
	/**
	 * buyer_require
	 * 维权要求，要求退款0 
	 */
	public final static int REQUIRE_MONEY = 0;
	
	/**
	 * buyer_require (即退款退货)
	 * 维权要求，要求退货1 
	 */
	public final static int REQUIRE_GOODS = 1;
	
	/**
	 * 维权过期时间,默认为15 天
	 */
	public final static int OUT_OF_DATE = 15;
	/**
	 * 维权类型：七天无理由退货
	 */
	public final static int NO_REASON_REGOODS_7DAY=0;
	
	/**
	 * 维权类型：假一赔三
	 */
	public final static int ONE_FAKE_LOSE_THREE=1;
	
	/**
	 * 快递已超过七天无理由退货
	 */
	public final static int OUT_SEVEN_DATE = 7;
	
	/**
	 * 平邮已超过三十天无理由退货
	 */
	public final static int OUT_THIRTY_DATE = 30;
	
	/**
	 * 维权业务发起来源_从维权发起Add By ShiXing@2011.09.19
	 */
	public final static int RIGHTS_BIZ_FROM_RIGHTS = 0;
	
	/**
	 * 维权业务发起来源_从退款发起Add By ShiXing@2011.09.19
	 */
	public final static int RIGHTS_BIZ_FROM_REFUND = 1;
	
	/**
	 * 操作类型.维权
	 */
	public static final Integer OPER_TYPE_RIGHTS=0;
	/**
	 * 操作类型.退款
	 */
	public static final Integer OPER_TYPE_REFUND=1;
	
	/**
	 * 处理状态.已处理
	 */
	public static final Integer STATUS_HANDLE_OVER = 200;
	
	/**
	 * 处理状态.未处理
	 */
	public static final Integer STATUS_HANDLE_NOT = 100;
	
	/**
	 * 处理状态.已关闭
	 */
	public static final Integer STATUS_HANDLE_CLOSE = 300;
	
	/**
	 * 业务类型：维权
	 */
	public static final Integer BIZ_TYPE_RIGHTS = 100;
	
	/**
	 * 业务类型：退款
	 */
	public static final Integer BIZ_TYPE_RREFUND = 101;
	
}
