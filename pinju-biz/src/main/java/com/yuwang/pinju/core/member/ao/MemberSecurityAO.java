package com.yuwang.pinju.core.member.ao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.domain.member.security.ForgotPasswordDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.RetrievePasswordDO;
import com.yuwang.pinju.domain.member.security.SecurityEmailMessageDO;
import com.yuwang.pinju.domain.member.security.SecurityLinkDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.member.security.SmsSenderVO;

public interface MemberSecurityAO {

	/**
	 * <p>生成 TOKEN 和 MESSAGE ID 的消息摘要</p>
	 *
	 * @param retrieve
	 * @return 数据摘要值
	 *
	 * @author gaobaowen
	 * @since 2011-9-29 14:57:29
	 */
	String macRetrievePassword(RetrievePasswordDO retrieve);

	/**
	 * <p>根据会员ID查询会员安全邮箱</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 * @throws DaoException
	 */
	Result findMemberSecurityEmailByMemberId(Long memberId);

	/**
	 *<p>根据会员ID查询会员安全手机号码</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 */
	Result findMemberSecurityMobileByMemberId(Long memberId);

	/**
	 * <p>检查会员 email 是否可用，若该 email 未找到，或者属于当前会员时可以使用</p>
	 *
	 * @param email
	 * @param memberId 当前会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：参数错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_EMAIL_HAS_EXIST RESULT_MEMBER_EMAIL_HAS_EXIST}：email 已经被其他会员使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，表示该 email 尚未被使用</p>
	 */
	Result checkEmail(String email, long memberId);

	/**
	 * <p>检查会员手机号 是否可用，若该手机号 未找到，或者属于当前会员时可使用</p>
	 * @param mobile 手机号码
	 * @param memberId 当前会员编号
	 *
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：参数错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MOBILE_HAS_EXIST RESULT_MEMBER_MOBILE_HAS_EXIST }：手机号码 已经被其他会员使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，表示该 手机号码 尚未被使用</p>
	 */
	Result checkMobileAndCode(long memberId,SmsCodeValidatorVO scv);


	/**
	 * <p>系统发送邮件</p>
	 *
	 * @param senderModel
	 * @param email 邮件地址
	 * @param memberId 会员编号
	 * @param linkType 链接类型
	 * @param ip 邮件链接客户端产生的IP地址
	 * @param emailLinkSufix 链接后缀URL
	 * @return
	 */
	Result sendEmail(SecurityEmailMessageDO message);
	
	/**
	 * <p>系统发生解绑邮件</p>
	 * 
	 * @param memberId 会员编号
	 * @return
	 */
	Result sendUnboundEmail(SecurityEmailMessageDO message);

	/**
	 * <p>点击邮箱链接,记录验证信息</p>
	 *
	 * @param param 链接参数
	 * @return
	 */
	Result addEmailLink(MemberSecurityEmailLinkDO link);

	/**
	 * <p>确认邮件链接</p>
	 *
	 * @param link 邮件链接参数
	 *
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_LINK_NOT_FOUND RESULT_MEMBER_SECURITY_LINK_NOT_FOUND}：邮件链接无效或者已经过期</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO.class.getName()}：邮件链接确认信息</li>
	 * </ul>
	 * @author gaobaowen
	 * @since 2011-9-5 上午09:54:09
	 */
	Result confirmLink(SecurityLinkDO link);

	/**
	 * <p>发送找回密码邮件</p>
	 *
	 * @param forgot 忘记密码参数
	 *
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_CAPTCHA_ERROR RESULT_CAPTCHA_ERROR}：验证码无效</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_EMAIL_NOT_EXIST RESULT_MEMBER_EMAIL_NOT_EXIST}：未验证过的邮件</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO.class.getName()}：发送的邮件链接确认信息</li>
	 * </ul>
	 * @author gaobaowen
	 * @since 2011-9-5 上午09:54:09
	 */
	Result sendRetrievePasswordEmail(ForgotPasswordDO forgot);

	/**
	 * <p>找回密码</p>
	 *
	 * @param retrieve 找回密码的数据
	 *
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_LINK_NOT_FOUND RESULT_MEMBER_SECURITY_LINK_NOT_FOUND}：邮件链接无效或者已经过期</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND}：手机号码类型时未找到对应的数据</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：会员不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INVALID RESULT_MEMBER_INVALID}：会员无效</li>
	 *   <li>{@link MemberResultConstant#RESULT_UPDATE_COUNT_ERROR RESULT_UPDATE_COUNT_ERROR}：更新密码错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO.class.getName()}：邮件链接确认信息</li>
	 * </ul>
	 * @author gaobaowen
	 * @since 2011-9-5 上午09:54:09
	 */
	Result retrievePassword(RetrievePasswordDO retrieve);
	
	
	Result sendSmsCode(SmsSenderVO smsSender);

	
	/**
	 * <p>添加会员手机绑定</p>
	 * @param memberSecurityMobileDO
	 * @return
	 */
	Result addMemberSecurityMobile(MemberSecurityMobileDO memberSecurityMobileDO);
	
	/**
	 * <p>解绑会员手机</p>
	 * @param memberSecurityMobileDO
	 * @return
	 */
	Result unBindMemberSecurityMobile(MemberSecurityMobileDO memberSecurityMobileDO);
	

	/**
	 * <p>会员的安全邮箱解绑</p>
	 * 
	 * @param memberId 会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_SECURITY_LINK_NOT_FOUND RESULT_MEMBER_SECURITY_LINK_NOT_FOUND}：邮件链接不正确</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 * 
	 */
	Result unboundEmail(MemberSecurityEmailLinkDO link);
	
	/**
	 * <p>查询会员安全中心相关数据</p>
	 * 
	 * @param memberId 会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 true 时， 返回结果：Result[MemberSecurityCenterDO] </p>
	 * 
	 */
	Result getSecurityCenter(long memberId);
	
	/**
	 * <p> 验证用户名与手机 或是 邮箱是否匹配存在</p>
	 * 
	 * @param ForgotPasswordDO
	 * @return
	 * @throws ManagerException
	 */
	Result checkViewLoginMobileAndEmail(ForgotPasswordDO forgot);
	
	Result checkLoginNameMobileEmail(ForgotPasswordDO forgot);
	
	/**
	 * 手机找回密码验证验证码
	 * @param scv
	 * @param nick
	 * @return
	 */
	Result checkCode(SmsCodeValidatorVO scv, String nick);

	/**
	 * <p>摘要会员名称</p>
	 * 
	 * @param loginName 会员名称
	 * @return loginName数据摘要值
	 */
	String macLoginName(String loginName);


}
