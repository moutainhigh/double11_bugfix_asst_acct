package com.yuwang.pinju.core.member.manager;

import java.util.Date;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO.LinkType;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityParam.DefaultSecurityParam;
import com.yuwang.pinju.domain.member.security.SecurityLinkDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.member.security.SmsSenderVO;

/**
 * <p>会员安全信息 manager</p>
 *
 * @author gaobaowen
 * @since 2011-9-1 14:54:37
 */
public interface MemberSecurityManager {

	/**
	 * 找回密码邮件正文模板名
	 */
	String EMAIL_TEMPLATE_RETRIEVE_PASSWORD_CONTENT = "retrieve-password";

	/**
	 * 找回密码邮件主题模板名
	 */
	String EMAIL_TEMPLATE_RETRIEVE_PASSWORD_SUBJECT = "retrieve-password-subject";

	/**
	 * <p>更新会员安全最后登录时间及登录IP地址</p>
	 *
	 * @param memberId          当前登录的会员编号
	 * @param currentLoginTime  当前登录时间
	 * @param currentLoginIp    当前登录IP
	 * @return  更新所影响的结果数量
	 *
	 * @author gaobaowen
	 * @since 2011-12-2 13:22:48
	 */
	int updateSecurityLoginTime(long memberId, Date currentLoginTime, String currentLoginIp);

	/**
	 * <p>检查字符串是否为手机号码模式</p>
	 *
	 * @param mobile  需要检查的字符串
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-22 14:14:24
	 */
	boolean isMobile(String mobile);

	/**
	 * <p>给已登录的会员发送短信</p>
	 *
	 * @param memberId  会员编号
	 * @param loginName  当前登录用户名
	 * @param type  发送类型
	 * @param clientIp  客户端 IP 地址
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INVALID RESULT_MEMBER_INVALID}：会员编号、登录会员名无效，需要重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_NOT_EXISTS RESULT_MOBILE_NOT_EXISTS}：当前会员还没有绑定过手机号码</li>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_FREQUENCE_OVER RESULT_MOBILE_FREQUENCE_OVER}：发送频率过快，应大于 1 分钟</li>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_DAILY_OVER RESULT_MOBILE_DAILY_OVER}：当天发送次数超过限制</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(Class)} 方法获取调用对象：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberSecurityMobileCodeDO#class}：发送的短信的消息</li>
	 * </ul>
	 *
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-11-22 14:14:50
	 */
	Result sendSmsCodeToLogin(SmsSenderVO smsSender) throws ManagerException;

	/**
	 * <p>给未登录的会员发送短信</p>
	 *
	 * @param mobile  需要发送的手机号码
	 * @param type  发送类型
	 * @param clientIp  客户端 IP 地址
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_INVALID RESULT_MOBILE_INVALID}：手机号码无效</li>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_FREQUENCE_OVER RESULT_MOBILE_FREQUENCE_OVER}：发送频率过快，应大于 1 分钟</li>
	 *   <li>{@link MemberResultConstant#RESULT_MOBILE_DAILY_OVER RESULT_MOBILE_DAILY_OVER}：当天发送次数超过限制</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(Class)} 方法获取调用对象：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberSecurityMobileCodeDO#class}：发送的短信的消息</li>
	 * </ul>
	 *
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-11-22 14:14:50
	 */
	Result sendSmsCodeToUnlogin(SmsSenderVO smsSender) throws ManagerException;

	/**
	 * <p>验证会员输入的手机验证码是否有效</p>
	 *
	 * @param validator  手机验证码校验参数
	 * @return  验证后的手机验证码对象。若会员输入的验证码无效或者消息编号不存在，将返回 null 值。
	 * 仅在验证码输入正确时返回非 null 值
	 * @throws ManagerException  参数无效，或者执行数据库操作时将抛出该异常
	 *
	 * @author gaobaowen
	 * @since 2011-11-23 17:37:58
	 */
	MemberSecurityMobileCodeDO validateMobileCode(SmsCodeValidatorVO validator) throws ManagerException;

	/**
	 * <p>根据会员编号查找会员安全邮件信息</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-10-20 10:17:16
	 */
	MemberSecurityEmailDO findMemberSecurityEmailByMemberId(long memberId) throws ManagerException;

	/**
	 * <p>根据会员编号查找会员安全信息。若不存在时则新增一条数据</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 14:56:42
	 */
	MemberSecurityDO getMemberSecurity(long memberId) throws ManagerException;

	/**
	 * <p>根据邮箱查询会员安全邮箱信息</p>
	 * @param email 邮箱地址
	 * @return
	 * @throws ManagerException
	 */
	MemberSecurityEmailDO findMemberSecurityEmailDOByEmail(String email) throws ManagerException;

	/**
	 * <p>根据手机号码查询会员安全手机号信息</p>
	 * @param mobile 手机号码
	 * @return
	 * @throws ManagerException
	 */
	MemberSecurityMobileDO findMemberSecurityMobileByMobile(String mobile) throws ManagerException;

	/**
	 * <p>生成链接查询参数</p>
	 *
	 * @param memberId  会员编码
	 * @param email     邮箱地址
	 * @param linkType  链接类型
	 * @return  邮件链接信息
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 15:03:55
	 */
	MemberSecurityEmailLinkDO generateEmailLink(long memberId, String email, String ip, LinkType linkType) throws ManagerException;

	/**
	 * <p>通过链接参数确认链接并获取邮件链接对象</p>
	 *
	 * @param link  链接参数
	 * @return  确认的邮件链接对象。若返回 null 值表示链接无效，或者已经失效
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 14:59:41
	 */
	MemberSecurityEmailLinkDO confirmEmailLink(SecurityLinkDO link) throws ManagerException;

	/**
	 * <p>确认 Token 信息</p>
	 *
	 * @param param 需要有 Message ID, Message Token 和 Security Type 三个值
	 * @return 若 {@link Result#isSuccess()} 时可以使用 getModel(Long.class) 方法获取确认会员编号。若非成功，会产生以下状态值：
	 * <ul>
	 * 	<li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR}: 参数错误</li>
	 *  <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_LINK_NOT_FOUND}: 邮件类型时未找到对应的数据</li>
	 *  <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND}: 手机号码类型时未找到对应的数据</li>
	 * </ul>
	 * @throws ManagerException  数据库操作时产生错误、安全类型不正确
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 下午02:29:00
	 */
	Result confirmToken(DefaultSecurityParam param) throws ManagerException;

	/**
	 * <p>添加会员 Email 安全邮箱地址数据</p>
	 *
	 * @param memberSecurityEmail
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 上午10:42:18
	 */
	MemberSecurityEmailDO addMemberSecurityEmail(MemberSecurityEmailDO memberSecurityEmail) throws ManagerException;

	/**
	 * <p>发送找回密码邮件</p>
	 *
	 * @param email 会员安全邮件信息
	 * @param link 邮件链接信息
	 * @return 发送状态
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-9-8 11:28:04
	 */
	void sendRetrievePasswordEmail(MemberSecurityEmailDO email, MemberSecurityEmailLinkDO link) throws ManagerException;

	/**
	 * <p>根据会员编号查找会员手机信息</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws ManagerException
	 *
	 */
	MemberSecurityMobileDO findMemberSecurityMobileByMemberId(long memberId) throws ManagerException;

	/**
	 * <p>根据 mobile 获取会员安全手机号数据</p>
	 *
	 * @param mobile 会员设定的手机号码
	 * @return 安全手机号码信息，若不存在则返回 null 值
	 * @throws DaoException
	 */
	MemberSecurityMobileDO getMemberSecurityMobileDOByMobile(String mobile) throws ManagerException;

	/**
	 * <p>添加会员安全手机号码数据</p>
	 *
	 * @param memberSecurityMobile
	 * @return
	 * @throws DaoException
	 */
	MemberSecurityMobileDO addMemberSecurityMobile(MemberSecurityMobileDO memberSecurityMobile) throws ManagerException;
	/**
	 * <p>解绑会员手机绑定</p>
	 * @param memberSecurityMobile
	 * @return
	 * @throws ManagerException
	 */
	int unblandMemberSecurityMobile (MemberSecurityMobileDO memberSecurityMobile) throws ManagerException;
	

	/**
	 * <p>根据 会员编号解除会员邮箱绑定</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 * @throws ManagerException
	 */
	MemberSecurityEmailDO unboundEmail(final long memberId) throws ManagerException;

	byte[] digestMessage(String data, String key);
	String digestMessageHex(String data, String key);
	byte[] macMessage(String data);
	String macMessageBase64(String data);
	byte[] encryptMessage(String data);
	String encryptMessageBase64(String data);
	String decryptMessage(String base64);
}
