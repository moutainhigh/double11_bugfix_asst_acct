package com.yuwang.pinju.core.member.ao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.api.core.manager.OpenApiApplicationManager;
import com.yuwang.api.core.manager.OpenApiSessionManager;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.filter.FilterResult;
import com.yuwang.pinju.core.filter.manager.FilterManager;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.core.member.manager.LoginPageManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.member.manager.register.RegisterCallback;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.member.ChangePasswordDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.auth.AuthParams;
import com.yuwang.pinju.domain.member.login.LoginPageImgVO;

public class PinjuMemberAOImpl implements PinjuMemberAO, MemberKeyConstant, MemberResultConstant {

	private final static Log log = LogFactory.getLog(PinjuMemberAOImpl.class);

	private CaptchaManager captchaManager;
	private PinjuMemberManager pinjuMemberManager;
	private OpenApiApplicationManager openApiApplicationManager;
	private OpenApiSessionManager openApiSessionManager;
	private MemberManager memberManager;
	private FilterManager loginNameFilterManager;
	private MemberSecurityManager memberSecurityManager;
	private LoginPageManager loginPageManager;

	public void setCaptchaManager(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
	}

	public void setPinjuMemberManager(PinjuMemberManager pinjuMemberManager) {
		this.pinjuMemberManager = pinjuMemberManager;
	}

	public void setOpenApiApplicationManager(OpenApiApplicationManager openApiApplicationManager) {
		this.openApiApplicationManager = openApiApplicationManager;
	}

	public void setOpenApiSessionManager(OpenApiSessionManager openApiSessionManager) {
		this.openApiSessionManager = openApiSessionManager;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setLoginNameFilterManager(FilterManager loginNameFilterManager) {
		this.loginNameFilterManager = loginNameFilterManager;
	}

	public void setLoginPageManager(LoginPageManager loginPageManager) {
		this.loginPageManager = loginPageManager;
	}

	public boolean isOpenApi(AuthParams auth) {
		if (auth == null) {
			return false;
		}
		if (auth.getAppKey() == null || EmptyUtil.isBlank(auth.getAppSign()) || EmptyUtil.isBlank(auth.getAppTime())) {
			if (log.isInfoEnabled()) {
				log.debug("[OPEN-API] auth param is empty, " + auth);
			}
			return false;
		}
		try {
			OpenApiApplicationDO app = openApiApplicationManager.getOpenApiApplicationById(auth.getAppKey());
			if (app == null) {
				log.warn("[OPEN-API] isOpenApi, can not find OpenApiApplication object by id: " + auth.getAppKey());
				return false;
			}
			if (EmptyUtil.isBlank(app.getSecret())) {
				log.warn("[OPEN-API] isOpenApi, can not find OpenApiApplication secret key, OpenApiApplication id: " + app.getId());
				return false;
			}
			String sign = auth.toSignData(app.getSecret());
			String hex = memberSecurityManager.digestMessageHex(sign, null);
			if (EmptyUtil.isBlank(hex)) {
				log.warn("[OPEN-API] isOpenApi, sign data is empty, sign data: " + sign);
				return false;
			}
			if (!hex.equals(auth.getAppSign())) {
				log.warn("[OPEN-API] isOpenApi, calc sign is: " + hex + ", app sign: " + auth.getAppSign());
				return false;
			}

			String param1 = memberSecurityManager.encryptMessageBase64( auth.toEncrypt() );
			if (param1 == null) {
				log.warn("[OPEN-API] isOpenApi, can not calculate encrypt auth data: " + auth.toEncrypt());
				return false;
			}
			String param2 = memberSecurityManager.macMessageBase64( param1 );
			if (param2 == null) {
				log.warn("[OPEN-API] isOpenApi, can not calculate mac auth data: " + param1);
				return false;
			}
			auth.setAppName( CharsetUtil.decodeURL(auth.getAppName()) );
			auth.setParam1( param1 );
			auth.setParam2( param2 );
			return true;
		} catch (Exception e) {
			log.error("[OPEN-API] isOpenApi, getOpenApiApplicationById cause exception, params: " + auth, e);
			return false;
		}
	}

	@Override
	public Result registerPinjuMember(MemberRegisterDO register) {
		if (log.isDebugEnabled()) {
			log.debug("register member, " + register);
		}
		if (register == null) {
			log.warn("register member, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}

		if (register.isPinjuRegister()) {
			if (EmptyUtil.isBlank(register.getSid())) {
				log.warn("register member, session id is null or empty");
				return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
			}
			if (!captchaManager.validate(register.getSid(), register.getCaptcha())) {
				log.warn("register member, captcha validate failed");
				return ResultSupportExt.createError(RESULT_CAPTCHA_ERROR);
			}
		}
		try {

			FilterResult filterResult = loginNameFilterManager.doFilter(register.getLoginName());
			if (!filterResult.isSuccess()) {
				log.warn("register login name [" + register.getLoginName() + "] contains invalid words");
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_WORDS_INVALID);
			}

			if (WordFilterFacade.scan(register.getLoginName(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_MEMBER)) {
				log.warn("register login name [" + register.getLoginName() + "] contains insensive words");
				return ResultSupportExt.createError(RESULT_INSENSIVE_WORDS);
			}

			// 检查会员名是否存在
			MemberDO member = memberManager.findMemberByNickname(register.getLoginName());
			if (member != null) {
				log.warn("register member, login name [" + register.getLoginName() + "] has bean used by member id: "
						+ member.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_HAS_EXIST);
			}

			return pinjuMemberManager.registerPinjuMember(register, RegisterCallback.EMPTY_CALL_BACK);
		} catch (Exception e) {
			log.error("registerMember error, parameter: " + register, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result pinjuLogin(MemberLoginDO login) {
		if (log.isDebugEnabled()) {
			log.debug("pinjuLogin, " + login);
		}
		if (login == null) {
			log.warn("pinjuLogin, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (EmptyUtil.isBlank(login.getLoginName()) || EmptyUtil.isBlank(login.getPassword())) {
			log.warn("pinjuLogin, login name or password is null or empty, login name: [" + login.getLoginName()
					+ "], password: [" + StringUtil.asterisk(login.getPassword()) + "]");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberPinjuLoginDO pinjuLogin = pinjuMemberManager.getMemberPinjuLoginByLoginName(login.getLoginName());
			if (pinjuLogin == null) {
				log.warn("pinjuLogin, login name: [" + login.getLoginName() + "] has not find in MemberPinjuLogin");
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			// captcha validating
			if (!isCaptchaOk(login)) {
				log.warn("register member, captcha validate failed");
				return loginError(login, RESULT_CAPTCHA_ERROR);
			}

			// calculating password digest data
			String hash = pinjuMemberManager.hash(pinjuLogin.getLoginName(), login.getPassword());

			// password digest is not match with password data of the account
			if (!hash.equals(pinjuLogin.getPassword())) {
				return loginError(login, RESULT_PINJU_LOGIN_ERROR);
			}

			// get member data using member pinju login info
			MemberDO member = memberManager.findMember(pinjuLogin.getMemberId());

			// can not find
			if (member == null) {
				log.warn("pinju login, cannot find member data, need add a new member data, member id: "
						+ pinjuLogin.getMemberId());
				member = memberManager.saveMember(pinjuLogin.createMember(MemberDO.MEMBER_ORIGIN_PINJU));
				log.warn("pinju Login, member data add success");
			}

			if (member != null && member.isBlock()) {
				log.warn("pinju login, member account was blocked, can not login, member id: " + member.getMemberId() + ", login name: " + login.getLoginName());
				String result = RESULT_PINJU_LOGIN_ACCOUNT_BLOCK;
				if (MemberDO.STATUS_MASTER_BLOCK.equals(member.getStatus())) {
					result = RESULT_PINJU_LOGIN_ACCOUNT_MASTER_BLOCK;
				}
				return ResultSupportExt.createError(result);
			}

			boolean clearResult = pinjuMemberManager.clearLoginErrorCount(login);
			if (log.isDebugEnabled()) {
				log.debug("clear login error count cache, clear result: " + clearResult + ", member id: "
						+ pinjuLogin.getMemberId() + ", login name: " + login.getLoginName());
			}

			Result result = ResultSupportExt.createSuccess();
			result.setModel(member);

			AuthParams auth = processOpenApi(login, member);
			if (auth != null) {
				result.setModel(auth);
			}

			return result;
		} catch (Exception e) {
			log.error("pinjuLogin error, parameter: " + login, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result showCaptcha(String loginName) {
		if (EmptyUtil.isBlank(loginName)) {
			log.warn("show captcha, nickname is null or empty, need not show captcha on web");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			boolean captcha = pinjuMemberManager.isCheckCaptcha(new MemberLoginDO(loginName));
			if (log.isInfoEnabled()) {
				log.info("show captcha, nickname [" + loginName + "] show captcha check result: " + captcha);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_BOOLEAN, captcha);
			return result;
		} catch (Exception e) {
			log.error("show captcha cause exception, need show captcha on web nickname: " + loginName, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result updatePinjuLoginPassowrd(ChangePasswordDO cp) {
		if (cp == null) {
			log.warn("updatePinjuLoginPassowrd, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (!memberManager.isCorrectMemberId(cp.getMemberId())) {
			log.warn("updatePinjuLoginPassowrd, paramter member id [" + cp.getMemberId() + "] is correct");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (EmptyUtil.isBlank(cp.getNickname())) {
			log.warn("updatePinjuLoginPassowrd, paramter nickname is null or empty");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberPinjuLoginDO pinjuLogin = pinjuMemberManager.getMemberPinjuLoginByMemberId(cp.getMemberId());

			// can not find pinjuLogin object, member is not exist
			if (pinjuLogin == null) {
				log.warn("updatePinjuLoginPassowrd, can not find member login data, member id: " + cp.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			// nickname of current login member in cookie is not same with login name of the member id
			if (!cp.getNickname().equals(pinjuLogin.getLoginName())) {
				log.warn("updatePinjuLoginPassowrd, current member nickname [" + cp.getNickname() + "] is not same with " +
						"login name [" + pinjuLogin.getLoginName() + "], member id: [" + cp.getMemberId() + "]");
				return ResultSupportExt.createError(RESULT_PINJU_NICKNAME_LOGINNAME_ERROR);
			}

			// old password of inputed is not same with current login member password
			if (!pinjuLogin.getPassword().equals( pinjuMemberManager.hash(cp.getNickname(), cp.getOldPassword()) )) {
				log.warn("updatePinjuLoginPassowrd, old password [" + cp.getOldPassword() + "] is not same with login " +
						"data password [" + pinjuLogin.getPassword() + "], member id: [" + cp.getMemberId() + "]");
				return ResultSupportExt.createError(RESULT_PINJU_LOGIN_ERROR);
			}
			int count = pinjuMemberManager.updatePinjuLoginPassowrd(pinjuLogin, cp.getNewPassword());
			if (log.isInfoEnabled()) {
				log.info("updatePinjuLoginPassowrd, modify new password finished, update count: " + count + ", member id: " + cp.getMemberId());
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(KEY_MEMBER_UPDATE_COUNT, count);
			return result;
		} catch (Exception e) {
			log.error("updatePinjuLoginPassowrd cause exception, parameter: " + cp, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	/**
	 * <p>
	 * 验证码校验是否 OK。若不需要验证码时直接返回 true
	 * </p>
	 *
	 * @param loginName 登录账号
	 * @param login
	 *            会员输入的登录信息
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-30 10:22:04
	 */
	private boolean isCaptchaOk(MemberLoginDO login) throws ManagerException {
		if (!pinjuMemberManager.isCheckCaptcha(login)) {
			log.debug("check captcha, need not check captcha, login name: " + login.getLoginName());
			return true;
		}
		if (!captchaManager.validate(login.getSid(), login.getCaptcha())) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 登录错误，当登录错误超过限制时将错误次数作为结果返回
	 * </p>
	 *
	 * @param memberId
	 *            当前会员编号
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-7-29 18:46:28
	 */
	private Result loginError(MemberLoginDO login, String errorResult) throws ManagerException {
		Result result = ResultSupportExt.createError(errorResult);
		int errorCount = pinjuMemberManager.incrementLoginErrorCount(login);
		if (errorCount >= PinjuConstant.PINJU_LOGIN_ERROR_COUNT_CAPTCHA) {
			result.setModel(KEY_LOGIN_OVER_ERROR_COUNT, errorCount);
		}
		return result;
	}

	private AuthParams processOpenApi(MemberLoginDO login, MemberDO member) {
		if (log.isDebugEnabled()) {
			log.debug("[OPEN-API] do login, check open login, param1: [" + login.getParam1() + "], param2: [" + login.getParam2() + "]");
		}
		if (EmptyUtil.isBlank(login.getParam1())) {
			log.debug("[OPEN-API] do login, current login object param1 object is empty, that is not open api login");
			return null;
		}
		if (EmptyUtil.isBlank(login.getParam2())) {
			log.debug("[OPEN-API] do login, current login object param2 object is empty, that is not open api login");
			return null;
		}
		String mac = memberSecurityManager.macMessageBase64( login.getParam1() );
		if (!login.getParam2().equals(mac)) {
			log.error("[OPEN-API] do login, open api login, but param2 [" + login.getParam2() + "] is not equals with mac [" + mac + "]");
			return null;
		}
		String data = memberSecurityManager.decryptMessage( login.getParam1() );
		if (EmptyUtil.isBlank(data)) {
			log.error("[OPEN-API] do login, open api login, decrypt message is error, param1: [" + login.getParam1() + "]");
			return null;
		}
		AuthParams auth = AuthParams.parseAuthParams(data);
		if (auth == null) {
			log.error("[OPEN-API] do login, open api login, decrypt message is error, param1: [" + login.getParam1() + "], decrypt data: [" + data + "]");
			return null;
		}
		try {
			boolean result = openApiSessionManager.saveOpenApiSession(member.getMemberId(), member.getNickname(), auth.getAppKey(), auth.generateToken());
			log.error("[OPEN-API] do login, open api login, save token result is [" + result + "], member id: [" + member.getMemberId() + "]," +
						"appkey: [" + auth.getAppKey() + "], app token: [" + auth.getToken() + "]");
			auth.changeSuccess();
		} catch (Throwable e) {
			log.error("[OPEN-API] do login, open api login, save token cause exception, member id: [" + member.getMemberId() + "]," +
					"appkey: [" + auth.getAppKey() + "], app token: [" + auth.getToken() + "]", e);
		}
		return auth;
	}

	@Override
	public LoginPageImgVO getLoginPageImg(boolean disabledCache) {
		try {
			return loginPageManager.getLoginPageImg(disabledCache);
		} catch (Exception e) {
			log.error("[getLoginPageImg] manager invoke cause runtime exception, login page will be use DEFAULT IMAGE, disabled cache: [" + disabledCache + "]", e);
			return LoginPageImgVO.DEFAULT_LOGIN_PAGE_IMG;
		}
	}
}
