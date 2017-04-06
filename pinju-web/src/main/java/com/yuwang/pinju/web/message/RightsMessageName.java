package com.yuwang.pinju.web.message;

/**  
 * @Project: pinju-web
 * @Description: TODO
 * @author 石兴 shixing@zba.com
 * @date 2011-7-1 上午09:49:59
 * @update 2011-7-1 上午09:49:59
 * @version V1.0  
 */
public interface RightsMessageName {

	/**
	 * 删除成功
	 */
	String OPERATE_REMOVE_SUCCESS = "operate.delete.success";
	
	/**
	 * 该订单已进行过维权,不能重复维权
	 */
	String OPERATE_RIGHTS_ALREADY = "operate.rights.already";
	
	/**
	 * 会话超时，请重新登录
	 */
	String OPERATE_RIGHTS_RELOGIN="operate.rights.relogin";
	
	/**
	 * 请勿进行非法操作,订单与身份不付,请重新登录
	 */
 	String OPERATE_RIGHTS_IDENTITY="operate.rights.identity";
 	
 	/**
 	 * 您的维权入口已关闭,超过维权有效期限制
 	 */
 	String TRADE_RIGHTS_OUTOFDATE = "trade.rights.outofdate";
 	
 	/**
 	 * 获取维权记录失败
 	 */
 	String RIGHTS_RECORD_GET_EXIST = "rights.record.get.fail";

 	/**
 	 * 进入维权申请页面出错
 	 */
	String RIGHTS_SHOW_APPLY_FAIL = "rights.show.apply.fail";

	/**
	 * 已超过7天无理由退货期限-快递方式：快递-7天
	 */
	String RIGHTS_OUT_SEVEN_DATE = "rights.out.seven.date";
	
	/**
	 * 已超过7天无理由退货期限-平邮方式：平邮-30天
	 */
	String RIGHTS_OUT_THIRTY_DATE = "rights.out.thirty.date";
	
	


}
