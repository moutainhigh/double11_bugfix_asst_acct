package com.yuwang.pinju.core.member.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.manager.register.RegisterCallback;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.security.PasswordValidatorVO;

public interface PinjuMemberManager {

	Result registerPinjuMember(MemberRegisterDO register, RegisterCallback callback) throws ManagerException;

	MemberPinjuLoginDO getMemberPinjuLoginByLoginName(String loginName) throws ManagerException;

	/**
	 * <p>根据登录账号验证密码是否匹配</p>
	 *
	 * @param vp 
	 * @param password 
	 *
	 * @return 密码是否正确（true: 正确；false: 错误）
	 *
	 * @throws ManagerException  loginName 或者 password 或者 tid 为 null 或者空值；执行时产生异常等
	 *
	 * @author gaobaowen
	 * @since 2011-10-9 18:17:14
	 */
	boolean validatePassowrd(PasswordValidatorVO vp) throws ManagerException;

	MemberPinjuLoginDO getMemberPinjuLoginByMemberId(long memberId) throws ManagerException;

	int updatePinjuLoginPassowrd(MemberPinjuLoginDO pinjuLogin, String password) throws ManagerException;

	String hash(String loginName, String originPassword);

	int incrementLoginErrorCount(MemberLoginDO login) throws ManagerException;

	boolean isCheckCaptcha(MemberLoginDO login) throws ManagerException;

	boolean clearLoginErrorCount(MemberLoginDO login) throws ManagerException;
}
