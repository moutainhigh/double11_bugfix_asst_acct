package com.yuwang.pinju.core.refund.ao;

import java.util.Date;

import com.yuwang.pinju.domain.refund.RefundDO;

/** <p>Description: 	退款的公共AO  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-14
 */
public interface RefundBaseAO {

	/**
	 * 等待卖家同意退款的超时
	 */
	public static final Integer  WAIT_SELL_ARGEE=5;
	/**
	 * 卖家同意后 买家需要发货的超时时间 
	 */
	public static final Integer  BUYER_RETURN_GOODS=5;
	/**
	 * 卖家拒绝退款的超时时间
	 */
	public static  final Integer SELLER_REF_REFUND=5;
	/**
	 * 卖家确认收货的是快递超时时间
	 */
	public static final Integer  SELLER_ACCETP_EMS=10;
	/**
	 * 卖家确认收货的是平邮的超时时间
	 */
	public static final Integer  SELLER_ACCETP_SNAIL=30;
	/**
	 * <p>Description: 根据退款状态获取超时时间</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-14
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public  Date getOvertimes(RefundDO refundDO);
	
	/**
	 * <p>Description: 获取订单的实付价格</p>
	 * @param orderId
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getRealPayMentamount(Long orderId);
}


