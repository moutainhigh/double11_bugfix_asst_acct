package com.yuwang.pinju.web.message;

/**
 * <p>资源消息的 key 名字</p>
 *
 * @author gaobaowen
 * 2011-6-10 上午09:10:28
 */
public interface MessageName {

	/**
	 * 错误页面默认的消息
	 */
	String ERROR_DEFAULT_MESSAGE = "error.default.message";

	/**
	 * 无效的操作
	 */
	String OPERATE_INVALID = "operate.invalid";

	/**
	 * 操作失败
	 */
	String OPERATE_FAILED = "operate.failed";

	/**
	 * 操作成功
	 */
	String OPERATE_SUCCESS = "operate.success";

	/**
	 * 更新成功
	 */
	String OPERATE_UPDATE_SUCCESS = "operate.update.success";

	/**
	 * 新增成功
	 */
	String OPERATE_INSERT_SUCCESS = "operate.insert.success";

	/**
	 * 删除成功
	 */
	String OPERATE_REMOVE_SUCCESS = "operate.remove.success";

	/**
	 * 提交的参数有错误
	 */
	String OPERATE_SUBMIT_PARAMETER_ERROR = "operate.submit.parameter.error";

	/**
	 * 重复提交
	 */
	String OPERATE_SUBMIT_DUPLICATE = "operate.submit.duplicate";

	/**
	 * 验证码输入错误
	 */
	String OPERATE_CAPTCHA_ERROR = "operate.captcha.error";

	/**
	 * 当前登录账号已改变，请退出再次登录后继续操作
	 */
	String OPERATE_MEMBER_NOT_MATCH = "operate.member.not.match";

	/**
	 * 邮箱地址未经验证
	 */
	String RETRIEVE_PASSWORD_EMAIL_NOT_EXISTS = "retrieve.password.email.not.exists";

	/**
	 * 新浪微博登录未授权
	 */
	String MEMBER_SINA_NO_OAUTH = "member.sina.no.oauth";

	/**
	 * 新浪微博用户账号并不存在
	 */
	String MEMBER_SINA_USER_NO_EXIST = "member.sina.user.no.exist";

	/**
	 * 您的用户信息已被修改，请重新登录
	 */
	String MEMBER_SINA_USER_DIFFERENT = "member.sina.user.different";

	/**
	 * 新浪微博登录出现异常
	 */
	String MEMBER_SINA_LOGIN_ERROR = "member.sina.login.error";

	/**
	 * 该昵称已经被其他会员使用了
	 */
	String MEMBER_NICKNAME_HAS_BEEN_USED = "member.nickname.has.been.used";

	/**
	 * 注册失败，请稍候重试
	 */
	String MEMBER_REGISTER_FAILED = "member.register.failed";

	/**
	 * 该会员名中包含可能会引起纠纷的词汇，无法使用
	 */
	String MEMBER_LOGINNAME_INVALID = "member.loginname.invalid";

	/**
	 * 该会员名中包含敏感词，无法使用
	 */
	String MEMBER_LOGINNAME_INSENSIVE = "member.loginname.insensive";

	/**
	 * 该会员名已经被其他会员使用了
	 */
	String MEMBER_LOGINNAME_HAS_BEEN_USED = "member.loginname.has.been.used";

	/**
	 * 您的新浪微博会员已被使用
	 */
	String RESULT_MEMBER_SIN_UID_HAS_EXIST = "result.member.sin.uid.has.exist";

	/**
	 * 请输入登录账号
	 */
	String MEMBER_LOGIN_LOGINNAME_EMPTY = "member.login.loginname.empty";

	/**
	 * 该邮箱已经被其他会员使用了
	 */
	String MEMBER_EMAIL_HAS_BEEN_USED = "member.email.has.been.used";

	/**
	 * 登录账号不存在
	 */
	String MEMBER_LOGINNAME_NOT_FOUND = "member.loginname.not.found";

	/**
	 * 登录账号的密码错误
	 */
	String MEMBER_PASSWORD_ERROR = "member.password.error";

	/**
	 * 登录数据没有填写完全
	 */
	String MEMBER_LOGIN_PARAMETER_ERROR = "member.login.parameter.error";

	/**
	 * 登录失败，请稍候再尝试
	 */
	String MEMBER_LOGIN_FAILED = "member.login.failed";

	/**
	 * 登录账号已冻结暂时无法登录
	 */
	String MEMBER_LOGIN_ACCOUNT_BLOCK = "member.login.account.block";
	
	/**
	 * 登录账号已被卖家主账号冻结暂时无法登录
	 */
	String MEMBER_LOGIN_ACCOUNT_MASTER_BLOCK = "member.login.account.master.block";

	/**
	 * 您所输入的原密码不正确，请重新输入
	 */
	String MEMBER_CHANGE_PASSWORD_AUTH_ERROR = "member.change.password.auth.error";

	/**
	 * 您的密码暂时无法修改，请稍候重试
	 */
	String MEMBER_CHANGE_PASSWORD_FAILED = "member.change.password.failed";

	/**
	 * 密码修改成功，下次登录将使用您所设置的新密码
	 */
	String MEMBER_CHANGE_PASSWORD_SUCCESS = "member.change.password.success";

	/**
	 * 会员地址超过最大数
	 */
	String MEMBER_DELIVERY_OVER_MAX = "member.delivery.over.max";

	/**
	 * 头像上传失败，请稍候重试
	 */
	String MEMBER_SNS_AVATOR_UPLOAD_ERROR = "member.sns.avator.upload.error";

	/**
	 * 个人简介不能大于300个字符
	 */
	String MEMBSER_SNS_INFO_BIOGRAPHY_LENGTH = "memberSnsInfo.biography.length";

	/**
	 * 请选择图片进行上传
	 */
	String MEMBER_SNS_AVATOR_UPLOAD_NULL = "member.sns.avator.upload.null";

	/**
	 * 已经存在绑定的支付账户，不能再次绑定
	 */
	String PAY_ACCOUNT_REBOUND = "pay.account.rebound";

	/**
	 * 之前支付账户已经解绑，请重新设置新的支付账户
	 */
	String PAY_ACCOUNT_OUBOUND_SUCCESS = "pay.account.oubound.success";

	/**
	 * 验证支付账号信息时出现错误，请您重新填写并再次绑定支付账号
	 */
	String PAY_ACCOUNT_BOUND_ERROR = "pay.account.bound.error";

	/**
	 * 该支付账号已被其他会员绑定
	 */
	String PAY_ACCOUNT_ALREADY_BOUND_ERROR = "pay.account.already.bound.error";

	/**
	 * 会员没有选择绑定账号类型
	 */
	String PAY_ACCOUNT_ACCOUNTTYPE_NO = "pay.account.accounttype.no";

	/**
	 * 文件大小超限
	 */
	String FILE_SIZE_TO_LARGE = "file.size.to.large";

	/**
	 * 图片不能大于1M
	 */
	String FILE_ONE_M_LARGE = "file.one.m.large";

	/**
	 * 文件类型无效
	 */
	String FILE_TYPE_INVALID = "file.type.invalid";

	/**
	 * 发送验证邮件出错
	 */
	String MEMBER_SEND_EMAIL_ERROR = "member.send.email.error";
	
	/**
	 * 发送验证解绑邮件失败
	 */
	String MEMBER_SEND_UNBOUND_EMAIL_ERROR = "member.send.ubbound.mail.error";

	/**
	 * 您还没有验证过邮箱
	 */
	String MEMBER_EMAIL_NO_BOUND = "member.email.no.bound";
	
	/**
	 * 邮箱解绑出现异常
	 */
	String MEMBER_EMAIL_UNBOUND_ERROR = "member.email.unbound.error";
	/**
	 * 填写的评论内容有误
	 */
	String RATE_ORDER_COMMENT_ERROR = "rate.order.comment.error";

	/**
	 * 请填写评论内容
	 */
	String RATE_ORDER_COMMENT_EMPTY = "rate.order.comment.empty";

	/**
	 * 评论字数应在 300 个字符以内
	 */
	String RATE_ORDER_COMMENT_LONG = "rate.order.comment.long";

	/**
	 * 请您认真审核您的评价，是否在您的评论中有涉及到违禁词
	 */
	String RATE_ORDER_COMMENT_ILLEGAL = "rate.order.comment.illegal";

	/**
	 * 验证签名失败
	 */
	String TEN_PAY_SIGN_ERROR = "ten.pay.sign.error";

	/**
	 * 网络异常,请稍候重试
	 */
	String TEN_PAY_NET_ERROR = "ten.pay.net.error";

	/**
	 * 财付通账号无效,
	 */
	String TEN_PAY_ACCOUNT_IS_INVALID = "ten.pay.account.is.invalid";
	
	/**
	 * 手机验证码已经失效或填写错误,请重新获取
	 */
	String MEMBER_MOBILE_UNCONFIM_CODE_FAILURE = "member.mobile.unconfim.code.failure";
	
	/**
	 * 该手机号码{0}已经被使用,请重新操作
	 */
	String MEMBER_MOBILE_HAS_USED = "member.mobile.has.used";
	
	/**
	 * 手机号码"{0}"无效,请填写正确的手机号码
	 */
	String MEMBER_MOBILE_FAILURE = "member.mobile.failure";
	
	/**
	 * 您输入的会员名不存在
	 */
	String MEMBER_NOT_EXIST = "member.not.exist";
	
	/**
	 * 您输入的邮箱不存在
	 */
	String MEMBER_EMAIL_NOT_EXIST = "member.email.not.exist";
	
	/**
	 * 您输入的手机号码不存在
	 */
	String MEMBER_MOBILE_NOT_EXIST = "member.mobile.not.exist";
	
	/**
	 * 该会员与所绑定的邮箱不一致
	 */
	String MEMEBR_EMAIL_NOT_EQUAL = "member.email.not.equal";
	
	/**
	 * 该会员与所绑定的手机号码不一致
	 */
	String MEMEBR_MOBILE_NOT_EQUAL = "member.mobile.not.equal";
	
	/**
	 * 主账号角色已存在
	 */
	String MEMBER_MASTER_ASST_ROLE_EXIST = "member.master.asst.role.exist";
	/**
	 * 您选择的角色不存在
	 */
	String MEMBER_ASST_ACCOUNT_ROLE_NO_EXIST = "member.asst.account.role.no.exist";
}
