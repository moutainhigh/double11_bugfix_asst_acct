package com.yuwang.pinju.core.member.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.member.manager.LoginPageManager;
import com.yuwang.pinju.domain.member.ChangePasswordDO;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.auth.AuthParams;
import com.yuwang.pinju.domain.member.login.LoginPageImgVO;

/**
 * <p>品聚会员</p>
 *
 * @author gaobaowen
 * @since 2011-7-29 10:48:15
 */
public interface PinjuMemberAO {
	
	/**
	 * 是否属于 Open API 登录
	 */
	boolean isOpenApi(AuthParams auth);

	/**
	 * <p>品聚会员注册</p>
	 *
	 * @param register 会员注册信息
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_CAPTCHA_ERROR RESULT_CAPTCHA_ERROR}：验证码输入错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_HAS_EXIST RESULT_MEMBER_NICKNAME_HAS_EXIST}：会员名称已经被使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_EMAIL_HAS_EXIST RESULT_MEMBER_EMAIL_HAS_EXIST}：邮箱已经被使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_WORDS_INVALID RESULT_MEMBER_NICKNAME_WORDS_INVALID}：会员名称含有争议词</li>
	 *   <li>{@link MemberResultConstant#RESULT_INSENSIVE_WORDS RESULT_INSENSIVE_WORDS}：会员名称含有敏感词</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberPinjuLoginDO.class.getName()}：品聚注册信息</li>
	 *   <li>{@link MemberDO.class.getName()}：会员信息</li>
	 *   <li>{@link MemberInfoDO.class.getName()}：会员基本信息</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 10:48:28
	 */
	Result registerPinjuMember(MemberRegisterDO register);

	/**
	 *
	 * <p>品聚会员登录</p>
	 *
	 * @param login 会员登录信息
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_CAPTCHA_ERROR RESULT_CAPTCHA_ERROR}：验证码输入错误，在结果中可能会含有：
	 *     <ul>
	 *     	 <li>{@link MemberKeyConstant#KEY_LOGIN_OVER_ERROR_COUNT KEY_LOGIN_OVER_ERROR_COUNT}：有该值时表明登录次数超过限制，需要显示验证码</li>
	 *     </ul>
	 *   </li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：会员不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_PINJU_LOGIN_ACCOUNT_BLOCK RESULT_PINJU_LOGIN_ACCOUNT_BLOCK}：会员账号冻结</li>
	 *   <li>{@link MemberResultConstant#RESULT_PINJU_LOGIN_ERROR RESULT_PINJU_LOGIN_ERROR}：密码不正确，在结果中可能会含有：
	 *     <ul>
	 *     	 <li>{@link MemberKeyConstant#KEY_LOGIN_OVER_ERROR_COUNT KEY_LOGIN_OVER_ERROR_COUNT}：有该值时表明登录次数超过限制，需要显示验证码</li>
	 *     </ul>
	 *   </li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberDO.class.getName()}：登录会员信息</li>
	 *   <li>{@link AuthParams.class.getName()}：Open API 登录信息，仅在 Open API 登录才会有该项数据</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-30 16:13:27
	 */
	Result pinjuLogin(MemberLoginDO login);

	/**
	 *
	 * <p>会员错误登录次数导致是否需要输入验证码</p>
	 *
	 * @param loginName 登录账号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_BOOLEAN KEY_BOOLEAN}：是否需要呈现验证码（boolean）</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-08-01 09:32:00
	 */
	Result showCaptcha(String loginName);

	/**
	 *
	 * <p>修改品聚会员密码</p>
	 *
	 * @param cp 修改密码参数
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：会员不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_PINJU_NICKNAME_LOGINNAME_ERROR RESULT_PINJU_NICKNAME_LOGINNAME_ERROR}：当前登录会员昵称与登录数据会员账号不同</li>
	 *   <li>{@link MemberResultConstant#RESULT_PINJU_LOGIN_ERROR RESULT_PINJU_LOGIN_ERROR}：输入的旧密码不正确</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_UPDATE_COUNT KEY_MEMBER_UPDATE_COUNT}：更新成功的数据行数（Integer）</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-08-01 18:45:00
	 */
	Result updatePinjuLoginPassowrd(ChangePasswordDO cp);
	
	/**
	 * <p>登录页面图片数据，详见 {@link LoginPageManager#getLoginPageImg(boolean)} 说明</p>
	 *
	 * @param disabledCache
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-2 16:29:26
	 */
	LoginPageImgVO getLoginPageImg(boolean disabledCache);
}
