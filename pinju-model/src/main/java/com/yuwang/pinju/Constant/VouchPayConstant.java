package com.yuwang.pinju.Constant;

/**
 * Created on 2011-9-16
 * 
 * @see <p>
 *      Discription: 担保交易支付常量定义
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class VouchPayConstant {
	/**
	 * 担保交易支付状态:未支付
	 */
	public final static long VOUCH_PAY_STATE_UNPAID = 100;

	/**
	 * 担保交易支付状态:已支付
	 */
	public final static long VOUCH_PAY_STATE_PAID= 101;
	
	/**
	 * 担保交易支付状态:已分账
	 */
	public final static long VOUCH_PAY_STATE_SPLIT= 102;
	
	/**
	 * 支付 支付平台类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_TEN_PAY_TYPE = "1";

	/**
	 * 支付 日志发送类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_TEN_Send_TYPE = "支付";

	/**
	 * 平台退款 支付平台类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_PLATFORM_REFUND_PAY_TYPE = "93";

	/**
	 * 平台退款 日志发送类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_PLATFORM_REFUND_SEND_TYPE = "平台退款";

	/**
	 * 分账回退 支付平台类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_SPLIT_ROLLBACK_PAY_TYPE = "95";

	/**
	 * 分账回退 日志发送类型@Add By HeYong 2011-09-16
	 */
	public final static String VOUCH_SPLIT_ROLLBACK_SEND_TYPE = "分账回退";

	/**
	 * 标记描述分隔
	 */
	public final static String SPLITKEY = ";";

	/**
	 * 标记描述key:value分隔
	 */
	public final static String SPLITATTRIBUTES = ":";

	/**
	 * 分割同一个key的多个值
	 */
	public final static String SPLITVALUES = ",";

}
