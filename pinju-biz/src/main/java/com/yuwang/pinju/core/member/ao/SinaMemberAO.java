package com.yuwang.pinju.core.member.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.domain.member.MemberSinaRegisterDO;

public interface SinaMemberAO {

	/**
	 * <p>sina 第三方登录品聚网</p>
	 * 
	 * @param code 授权通过code值
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 * 
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_SINA_NO_OAUTH RESULT_SINA_NO_OAUTH}：请求的code不正确，sina没有授权</li>
	 *   <li>{@link MemberResultConstant#RESULT_SINA_USER_NO_EXIST RESULT_SINA_USER_NO_EXIST}：sina会员不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_SINA_LOGIN_ERROR RESULT_SINA_LOGIN_ERROR}：sina账号登陆出现错误(超时或链接异常)</li>
	 * </ul>
	 */
	Result sinaLogin(String code);
	
	/**
	 * <p>新浪会员名注册</p>
	 *
	 * @param register 新浪会员名注册信息
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_HAS_EXIST RESULT_MEMBER_NICKNAME_HAS_EXIST}：会员名称已经被使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_WORDS_INVALID RESULT_MEMBER_NICKNAME_WORDS_INVALID}：会员名称含有争议词</li>
	 *   <li>{@link MemberResultConstant#RESULT_INSENSIVE_WORDS RESULT_INSENSIVE_WORDS}：会员名称含有敏感词</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 */
	Result registerSinaMember(MemberSinaRegisterDO register);
}
