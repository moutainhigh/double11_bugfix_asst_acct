package com.yuwang.pinju.core.common.resultcode;

/**
 * @Discription: 财付通支付和分账ResultCode
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.common.resultcode
 * @Title: TenPayResultCode.java
 * @author: [贺泳]
 * @date 2011-9-22 下午01:28:08
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public interface TenPayResultCode {
	/**
	 * 系统异常，请重新确认收货！
	 */
	String SUBACCOUNT_EXCEPTION = "subaccount.exception";
	
	/**
	 * 有可能因为网络原因，请求已经处理，但未收到应答，请重新确认收货！
	 */
	String SUBACCOUNT_POST_EXCEPTION = "subaccount.post.exception";
	
	/**
	 * 此订单未付款，请勿非法操作！
	 */
	String SUBACCOUNT_ORDER_PAY_FAIL= "subaccount.order.pay.fail";
	
	/**
	 * 此订单已被处理
	 */
	String SUBACCOUNT_ORDER_SUBACCOUNT= "subaccount.order.subaccount";
	
	/**
	 * 无此条订单记录，请勿非法操作！
	 */
	String SUBACCOUNT_ORDER_NOTEXITS = "subaccount.order.notexits";
	
	/**
	 * 分账插入发送日志异常
	 */
	String SUBACCOUNT_INSERT_SENFLOG_EXCEPTION = "subaccount.insert.sendlog.exception";
	
	/**
	 * 确认收货系统应答失败，请重新确认收货！
	 */
	String SUBACCOUNT_TEN_BACK_FAIL= "subaccount.ten.back.fail";
	
	/**
	 * 确认收货系统应答验证签名失败，请重新确认收货！
	 */
	String SUBACCOUNT_TEN_BACK_CHECKSIGN_FAIL = "subaccount.ten.back.checksign.fail";
	
	/**
	 * 登录会话超时，请重新登录后再确认收货！
	 */
	String MEMBER_NOTLOGIN = "member.notlogin";
	
	/**
	 * 确认收货密码错误，请重新输入！
	 */
	String MEMBER_PASSWORD_ERROR = "member.password.error";
	
	/**
	 * 订单号为:{0}，查询该订单信息异常，请去订单列表查看该订单状态！
	 */
	String SUBACCOUNT_QUERY_ORDER_EXCEPTION = "subaccount.query.order.exception";
	
	/**
	 * 请求地址不存在
	 */
	String SUNACCOUNT_POST_ADDRESS_FAIL = "sunaccount.post.address.fail";
	
}
