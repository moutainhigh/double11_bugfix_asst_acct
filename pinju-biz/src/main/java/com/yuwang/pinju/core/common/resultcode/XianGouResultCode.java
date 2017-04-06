package com.yuwang.pinju.core.common.resultcode;

/**
 * @Project: pinju-biz
 * @Description:  限购业务ResultCode
 * @author 石兴 shixing@zba.com
 * @date 2011-8-31 下午09:40:28
 * @update 2011-8-31 下午09:40:28
 * @version V1.0
 */
public interface XianGouResultCode {


	/**
	 * 限购编码已被使用,请重新领取
	 */
	String XIANGOU_CODE_IS_USED = "xiangou.code.is.used";
	
	/**
	 * 验证码输入错误,请重新输入
	 */
	String XIANGOU_CHECKCODE_FAIL = "xiangou.checkcode.fail";
	
	/**
	 * 限购编码已过期
	 */
	String XIANGOU_CODE_OVER_TIME = "xiangou.code.over.time";
	
	/**
	 * 本商品为限购商品,需要限购码才能购买
	 */
	String XIANGOU_NEED_CODE = "xiangou.need.code";
	
	/**
	 * 该商品对应的限购码已经被领完
	 */
	String XIANGOU_CODE_NOT_ENOUGH = "xiangou.code.not.enough";
	
	/**
	 * 此限购活动已经结束
	 */
	String XIANGOU_ACTIVE_CLOSED="xiangou.active.closed";
	
	
	String XIANGOU_OPERATE_FAILED="xiangou.operate.failed";
	
	/**
	 * 领取之前需要输入验证码
	 */
	String XIANGOU_BEFORE_GETCODE="xiangou.before.getcode";
	
	/**
	 * 该限购码不能购买此商品,只可以购买某商品
	 */
	String XIANGOU_CODE_IS_NOT_APPLY_THIS_ITEM= "xiangou.code.is.not.apply.this.item";

	/**
	 * 该限购码非法，请勿搞恶意破坏！
	 */
	String XIANGOU_CHECKCODE_ILLEGAL = "xiangou.checkcode.illegal";
	/**
	 * 该限购码已经过期
	 */
	String XIANGOU_OUT_OF_DATE = "xiangou.out.of.date";
}
