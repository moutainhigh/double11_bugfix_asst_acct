package com.yuwang.pinju.core.constant.member;

public interface MemberResultConstant {

	/**
	 * 更新没有成功（一般指更新影响的数据行数小于 1）
	 */
	String RESULT_UPDATE_COUNT_ERROR = "result.update.count.error";

	/**
	 * 当前会员没有已绑定的支付账户
	 */
	String RESULT_PAYMENT_NOT_EXISTS = "result.payment.not.exists";

	/**
	 * 请求数据参数错误
	 */
	String RESULT_PARAMETERS_ERROR = "result.parameters.error";

	/**
	 * 验证码输入错误
	 */
	String RESULT_CAPTCHA_ERROR = "result.captcha.error";

	/**
	 * 品聚登录错误，密码不正确
	 */
	String RESULT_PINJU_LOGIN_ERROR = "result.pinju.login.error";

	/**
	 * 账号冻结
	 */
	String RESULT_PINJU_LOGIN_ACCOUNT_BLOCK = "result.pinju.login.account.block";

	/**
	 * 账号被卖家主账号冻结
	 */
	String RESULT_PINJU_LOGIN_ACCOUNT_MASTER_BLOCK = "result.pinju.login.account.master.block";

	/**
	 * 当前会员昵称与登录数据会员账号不一致
	 */
	String RESULT_PINJU_NICKNAME_LOGINNAME_ERROR = "result.pinju.nickname.loginname.error";

	/**
	 * 会员名已经存在
	 */
	String RESULT_MEMBER_NICKNAME_HAS_EXIST = "result.member.nickname.has.exist";

	/**
	 * 您的新浪微博会员已被使用
	 */
	String RESULT_MEMBER_SIN_UID_HAS_EXIST = "result.member.sin.uid.has.exist";

	/**
	 * 会员名中包括争议词
	 */
	String RESULT_MEMBER_NICKNAME_WORDS_INVALID = "result.member.nickname.words.invalid";

	/**
	 * SINA用户信息已被修改
	 */
	String RESULT_MEMBER_SINA_USER_DIFFERENT = "result.member.sina.user.different";

	/**
	 * 含有敏感词
	 */
	String RESULT_INSENSIVE_WORDS = "result.insensive.words";

	/**
	 * 邮箱已经存在
	 */
	String RESULT_MEMBER_EMAIL_HAS_EXIST = "result.member.email.has.exist";

	/**
	 * 手机号码已存在
	 */
	String RESULT_MEMBER_MOBILE_HAS_EXIST = "result.member.mobile.has.exist";

	/**
	 * 安全邮箱不存在
	 */
	String RESULT_MEMBER_EMAIL_NOT_EXIST = "result.member.email.not.exist";

	/**
	 * 该邮箱不属于该会员
	 */
	String RESULT_MEMBER_EMAIL_NOT_EQUAL = "result.member.email.not.equal";

	/**
	 * 该手机不属于该会员
	 */
	String RESULT_MEMBER_MOBILE_NOT_EQUAL = "result.member.mobile.not.equal";


	/**
	 * 已经存在已绑定的支付账户，不能重复绑定
	 */
	String RESULT_PAYMENT_REBOUND = "result.payment.rebound";

	/**
	 * 会员编号无效
	 */
	String RESULT_MEMBER_ID_INVALID = "result.member.id.invalid";

	/**
	 * 手机号码无效
	 */
	String RESULT_MOBILE_INVALID = "result.mobile.invalid";

	/**
	 * 手机号码不存在
	 */
	String RESULT_MOBILE_NOT_EXISTS = "result.mobile.not.exists";

	/**
	 * 手机号码已经被使用
	 */
	String RESULT_MOBILE_USED = "result.mobile.used";

	/**
	 * 内部错误
	 */
	String RESULT_MEMBER_INNER_ERROR = "result.member.inner.error";

	/**
	 * 会员不存在
	 */
	String RESULT_MEMBER_MEMBER_NOT_EXIST = "result.member.member.not.exist";

	/**
	 * 会员无效
	 */
	String RESULT_MEMBER_INVALID = "result.member.invalid";

	/**
	 * 会员已接受过用户协议
	 */
	String RESULT_MEMBER_ACCEPT_AGREEMENT = "result.member.accept.agreement";

	/**
	 * 商家信息没有找到
	 */
	String RESULT_MEMBER_SELLER_QUALITY_NOT_FOUND = "result.member.seller.quality.not.found";

	/**
	 * 签订代扣协议失败 Add By ShiXing@2011.09.13
	 */
	String RESULT_SIGN_PAY_AGREEMENT_FAIL = "result.sign.pay.agreement.fail";

	/**
	 * 该商户已签约成功！Add By ShiXing@2011.09.23
	 */
	String RESULT_SIGN_ALREADY = "result.sign.already";

	/**
	 * 该商户不存在委托关系！Add By ShiXing@2011.09.23
	 */
	String RESULT_SIGN_NOT_EXIST = "result.sign.not.exist";

	/**
	 * 您还未绑定财付通账号,请先做绑定操作Add By ShiXing@2011.09.14
	 */
	String RESULT_SIGN_PAY_BIND_NOT = "result.sign.pay.bind.not";

	/**
	 * 您还未签订财付通代扣协议,请先签订协议Add By ShiXing@2011.09.13
	 */
	String RESULT_SIGN_PAY_AGREEMENT_NOT = "result.sign.pay.agreement.not";

	/**
	 * 该支付账号已被其他会员绑定
	 */
	String PAY_ACCOUNT_ALREADY_BOUND_ERROR = "pay.account.already.bound.error";

	/**
	 * 获取代扣协议信息失败Add By ShiXing@2011.09.13
	 */
	String RESULT_GET_PAY_AGREEMENT_FAIL = "result.get.pay.agreement.fail";

	/**
	 * 您还没有绑定财付通账号,请点击立即绑定账号完成绑定操作！Add By ShiXing@2011.09.13
	 */
	String RESULT_MEMBERID_FAIL = "result.memberid.fail";

	/**
	 * 会员邮箱已验证
	 */
	String RESULT_MEMBER_EMAIL_CHECKED = "result.member.email.checked";

	/**
	 * 会员手机已验证
	 */
	String RESULT_MEMBER_MOBILE_CHECKED = "result.member.mobile.checked";

	/**
	 * 邮件发送失败
	 */
	String RESULT_MEMBER_EMAIL_SEND_ERROR = "result.member.email.send.error";

	/**
	 * 邮件链接未找到或者已经确认过
	 */
	String RESULT_MEMBER_SECURITY_LINK_NOT_FOUND = "result.member.security.link.not.found";

	/**
	 * 手机验证码未找到或者已经确认过
	 */
	String RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND = "result.member.security.mobile.not.found";

	/**
	 * 短信发送频率过于频繁（间隔未达到指定时间）
	 */
	String RESULT_MOBILE_FREQUENCE_OVER = "result.mobile.frequence.over";

	/**
	 * 当日一个业务短信发送次数超过指定次数
	 */
	String RESULT_MOBILE_DAILY_OVER = "result.mobile.daily.over";

	/**
	 * 您的账号没有授权登录
	 */
	String RESULT_SINA_NO_OAUTH = "result.sina.no.oauth";

	/**
	 * sina账号不存在
	 */
	String RESULT_SINA_USER_NO_EXIST = "result.sina.user.no.exist";

	/**
	 * sina账号登陆出现错误(超时或链接异常)
	 */
	String RESULT_SINA_LOGIN_ERROR = "result.sina.login.error";

	/**
	 * sina账号还没有填写pinju网的昵称
	 */
	String RESULT_SINA_NOT_NICKNAME_ERROR = "result.sina.not.nickname.error";

	/**
	 * 手机绑定验证前验证码失效
	 */
	String RESULT_UNCONFIM_CODE_FAILURE ="result.unconfim.code.failure";

	/**
	 * 手机绑定不存在
	 */
	String RESULT_MEMBER_SECURITY_NO_EXIST ="result.member.security.no.exist";

	/**
	 * 解绑手机错误
	 */
	String RESULT_UNBLAND_MOBILE_ERROR = "result.unbland.mobile.error";
	/**
	 * 子账号角色不存在
	 */
	String RESULT_ASST_ACCOUNT_ROLE_NO_EXIST = "result.asst.account.role.no.exist";

	/**
	 * 主账号角色已存在
	 */
	String RESULT_ASST_ROLE_EXIST = "result.asst.role.exist";
	
}
