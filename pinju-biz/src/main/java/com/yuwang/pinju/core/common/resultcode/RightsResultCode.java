package com.yuwang.pinju.core.common.resultcode;

/**  
 * @Project: pinju-web
 * @Description: 维权业务ResultCode
 * @author 石兴 shixing@zba.com
 * @date 2011-7-1 上午09:49:59
 * @update 2011-7-1 上午09:49:59
 * @version V1.0  
 */
public interface RightsResultCode {

	/**
	 * 操作失败
	 */
	String OPERATE_FAILED = "operate.failed";

	/**
	 * 操作成功
	 */
	String OPERATE_SUCCESS = "operate.success";

	/**
	 * 该订单已进行过维权,不能重复维权
	 */
	String OPERATE_RIGHTS_ALREADY = "operate.rights.already";
	
 	/**
 	 * 该维权记录不存在
 	 */
 	String RIGHTS_RECORD_NOT_EXIST = "rights.record.not.exist";
 	
}
