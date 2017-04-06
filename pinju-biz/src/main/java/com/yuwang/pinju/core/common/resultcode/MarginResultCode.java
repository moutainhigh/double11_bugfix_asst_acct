package com.yuwang.pinju.core.common.resultcode;

/**
 * @Project: pinju-biz
 * @Description:  保证金ResultCode
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午01:49:28
 * @update 2011-8-12 下午01:49:28
 * @version V1.0
 */
public interface MarginResultCode {

	/**
	 * 该用户已经缴纳保证金
	 */
	String MAGIN_SELLER_OVER="magin.seller.over_error";
	
	/**
	 * 卖家保证金额度未知,请完成开店流程
	 */
	String SELLER_MARGIN_NOT_EXIST="seller.margin.not.exist";
	
	/**
	 * 您还不是品聚卖家,无需缴纳保证金
	 */
	String SELLER_SHOP_NOT_EXIST="seller.shop.not.exist";
	
	/**
	 * 创建订单失败
	 */
	String SELLER_MARGIN_CREATEORDER_FAIL="seller.margin.createorder.fail";

	/**
	 * 无此订单,请勿非法操作
	 */
	String DIRECTPAY_ORDER_NOT_EXITS = "directpay.order.not.exits";
	
	/**
	 * 保证金后续业务操作失败
	 */
	String DIRECTPAY_MARGIN_OPERATION_FAIL = "directpay.margin.operation.fail";
	
	/**
	 * 缴保证金异常
	 */
	String PAYMARGIN_EXCEPTION = "paymargin.exception";
	
	/**
	 * 缴保证金失败
	 */
	String PAYMARGIN_FAIL = "paymargin.fail";
	
	/**
	 * 卖家补缴保证金时,更新卖家账户失败
	 */
	String PAYMARGIN_UPDDATE_SELLER_FAIL="paymargin.update.seller.fail";
	
	/**
	 * 卖家补缴保证金时,更新品聚账户失败
	 */
	String PAYMARGIN_UPDDATE_PINJU_FAIL="paymargin.update.pinju.fail";
	
	/**
	 * 该订单已处理
	 */
	String PAYMARGIN_ORDER_ALREADY = "paymargin.order.already";

	/**
	 * 缴消保时数据格式转换错误
	 */
	String NUMBERFORMAT_EXCEPTION_MARGIN = "numberformat.exception.margin";

	/**
	 * 缴消保时读取配置参数错误
	 */
	String MARGIN_REQ_PARAM_FAIL = "margin.req.param.fail";

	/**
	 * 处理保证金后续信息(卖家)失败
	 */
	String MARGIIN_PROCESS_SELLER_INFO_FAIL = "margiin.process.seller.info.fail";

	/**
	 * 处理保证金后续信息(品聚)失败
	 */
	String MARGIIN_PROCESS_PINJU_INFO_FAIL = "margiin.process.pinju.info.fail";

	/**
	 * 您输入的保证金格式不正确,请重新输入
	 */
	String MARGIN_FORMAT_ERROR = "margin.format.error";
	
	/**
	 * 更新保证金订单状态失败！
	 */
	String UPDATE_MARGIN_ORDER_STATE_FAIL = "update.margin.order.state.fail";
	
}
