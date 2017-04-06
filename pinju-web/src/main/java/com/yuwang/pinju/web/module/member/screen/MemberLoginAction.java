package com.yuwang.pinju.web.module.member.screen;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.domain.member.auth.AuthParams;
import com.yuwang.pinju.domain.member.login.LoginPageImgVO;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.login.AfterLoginProcess;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>会员登录处理</p>
 *
 * @author gaobaowen
 * @since 2011-9-15 17:40:28
 */
public class MemberLoginAction implements PinjuAction, ModelDriven<MemberLoginDO> {

	private final static Log log = LogFactory.getLog(MemberLoginAction.class);

	private MemberAO memberAO;
	private PinjuMemberAO pinjuMemberAO;

	/**
	 * 登录参数
	 */
	private MemberLoginDO login;

	/**
	 * 登录页面图片信息
	 */
	private LoginPageImgVO img;

	/**
	 * 是否需要输入验证码
	 */
	private boolean showCaptcha;

	/**
	 * 登录后返回 URL
	 */
	private String returnUrl;

	/**
	 * HTTP Referer
	 */
	private String referer;
	/**
	 * 新浪client id
	 */
	private String sinaClientid;

	/**
	 * 是否禁用缓存
	 */
	private String _disable_cache_;

	public MemberLoginAction() {
		login = new MemberLoginDO();
	}

	public String execute() throws Exception {
		referer = ServletUtil.getHttpReferer();

		// 禁用 HTTP 缓存
		ServletUtil.forbidBrowserCache();
		ServletUtil.setXssFilterHeader();
		ServletUtil.forbidFrameUsingSameorigin();

		try {
			// 设置 SID 值，用于验证码
			login.setSid(PinjuCookieManager.getHashSessionId(MemberLoginAction.class));

			AuthParams auth = createAuthParams();
			// 清除登录数据
			PinjuCookieManager.clearLogin();
			if(log.isDebugEnabled()) {
				log.debug("execute MemberLoginAction, returnUrl: " + returnUrl);
			}

			if (pinjuMemberAO.isOpenApi(auth)) {
				log.info("[OPEN-API] current user as open api user login, " + auth);
				login.setParam1(auth.getParam1());
				login.setParam2(auth.getParam2());
				login.setLoginName(auth.getAppName());
			}

			// 获取 HTTP Referer
			referer = ServletUtil.getHttpReferer();

			loginPageCheckCaptcha();

			img = pinjuMemberAO.getLoginPageImg("true".equals(_disable_cache_));
			sinaClientid = PinjuConstant.SINA_WEIBO_CLIENTID;

			return SUCCESS;
		} catch (Throwable e) {
			log.error("execute error, context parameter value:\n" +
					"login: " + login +
					"\nshowCaptcha: " + showCaptcha +
					"\nreturnUrl: " + returnUrl +
					"\nreferer: " + referer +
					"\nmemberAO: " + memberAO +
					"\npinjuMemberAO: " + pinjuMemberAO +
					"\nCookie: " + ServletActionContext.getRequest().getHeader("Cookie"),
				e);
			throw new Exception(e);
		}
	}

	public String doLogin() throws Exception {

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			execute();
			return INPUT;
		}

		// 禁用 HTTP 缓存
		ServletUtil.forbidBrowserCache();

		ActionInvokeResult air = new ActionInvokeResult(login);
		if (!air.validate()) {
			log.warn("check login info failed, " + login);
			for(Map.Entry<String, String> entry : air.getValidator().entrySet()) {
				ActionInvokeResult.setInvokeMessage(entry.getValue());
				return INPUT;
			}
		}

		Result result = pinjuMemberAO.pinjuLogin(login);

		String code = result.getResultCode();

		if (log.isDebugEnabled()) {
			log.debug("do pinju login, login: " + login + ", invoke result: " + code);
		}

		Number errorCount = (Number)result.getModel(MemberKeyConstant.KEY_LOGIN_OVER_ERROR_COUNT);
		if (errorCount != null) {
			showCaptcha = true;
			log.warn("login user error count over limit, need show capatcha, error count: " + errorCount);
		}

		if (!processError(result)) {
			return INPUT;
		}

		// 登录成功
		MemberDO member = result.getModel(MemberDO.class);

		EventProcess event = new AfterLoginProcess(memberAO, member, referer, returnUrl);
		boolean login = event.process(log);
		if (!login) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register pinju member login process result is failed");
			return INPUT;
		}
		returnUrl = ServletUtil.processReturnUrl(returnUrl);

		if (log.isDebugEnabled()) {
			log.debug("login return url: " + returnUrl);
		}

		AuthParams auth = result.getModel(AuthParams.class);
		if (auth != null) {

			ActionContext.getContext().put("token", auth.getToken());
			ActionContext.getContext().put("success", auth.getSuccess());
			log.info("[OPEN-API] current member is open api login, " +
					"login member id: [" + member.getMemberId() + "], " +
					"app key: [" + auth.getAppKey() + "], " +
					"token: [" + auth.getToken() + "], " +
				    "is success? [" + auth.getSuccess() + "]");
			return "open-api";
		}
		return SUCCESS;
	}

	private AuthParams createAuthParams() {
		AuthParams auth = new AuthParams();
		HttpServletRequest request = ServletActionContext.getRequest();
		String appKey = request.getHeader("x-pinju-app-key");
		if (StringUtils.isBlank(appKey)) {
			if (log.isDebugEnabled()) {
				log.debug("[OPEN-API] createAuthParams, can not find [x-pinju-app-key] in HTTP header, as common login process");
			}
			return auth;
		}
		Long key = null;
		try {
			key = Long.parseLong(appKey);
		} catch (Exception e) {
			log.error("[OPEN-API] createAuthParams, x-pinju-app-key is invalid, [" + request.getHeader("x-pinju-app-key") + "], as common login processing");
			return auth;
		}
		String sign = request.getHeader("x-pinju-app-sign");
		String time = request.getHeader("x-pinju-app-time");
		String name = request.getHeader("x-pinju-app-name");

		auth.setAppKey(key);
		auth.setAppName(name);
		auth.setAppSign(sign);
		auth.setAppTime(time);
		return auth;
	}

	private boolean processError(Result result) {
		if (result.isSuccess()) {
			return true;
		}
		if (MemberResultConstant.RESULT_CAPTCHA_ERROR.equals(result.getResultCode())) {
			log.warn("login user captcha input error");
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_CAPTCHA_ERROR);
			return false;
		}
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("login user account is not exist, " + login);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGINNAME_NOT_FOUND);
			return false;
		}
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ACCOUNT_BLOCK.equals(result.getResultCode())) {
			log.warn("login user account was blocked, " + login);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGIN_ACCOUNT_BLOCK);
			return false;
		}
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ERROR.equals(result.getResultCode())) {
			log.warn("login user account password error, " + login);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_PASSWORD_ERROR);
			return false;
		}
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			log.warn("login user parameter error, " + login);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGIN_PARAMETER_ERROR);
			return false;
		}
		ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGIN_FAILED);
		return false;
	}

	/**
	 * <p>自动带有登录用户名参数时检查是否需要显示验证码</p>
	 *
	 * @author gaobaowen
	 * @since 2011-10-27 10:41:41
	 */
	private void loginPageCheckCaptcha() {
		String loginName = login.getLoginName();
		if (StringUtils.isBlank(loginName) || loginName.length() < 2) {
			return;
		}

		Result rs = pinjuMemberAO.showCaptcha(loginName);

		if (log.isDebugEnabled()) {
			log.debug("login page parameter loginName, check loginName: " + loginName + ", check result: " + rs.getResultCode());
		}

		if (rs.isSuccess()) {
			Boolean result = (Boolean)rs.getModel(MemberKeyConstant.KEY_BOOLEAN);
			showCaptcha = (result == null ? true : result);
			log.debug("login page parameter loginName, check loginName, validate capatch result is success, result: " + result);
		}

		if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(rs.getResultCode())) {
			log.debug("login page parameter loginName, check loginName, check login name [" + loginName + "] inner error, need show captcha");
			showCaptcha = true;
			return;
		}

		// other need not show captcha
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public void setPinjuMemberAO(PinjuMemberAO pinjuMemberAO) {
		this.pinjuMemberAO = pinjuMemberAO;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public boolean isShowCaptcha() {
		return showCaptcha;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public void set_disable_cache_(String _disable_cache_) {
		this._disable_cache_ = _disable_cache_;
	}

	public String getSinaClientid() {
		return sinaClientid;
	}

	public void setSinaClientid(String sinaClientid) {
		this.sinaClientid = sinaClientid;
	}

	public LoginPageImgVO getImg() {
		return img;
	}

	@Override
	public MemberLoginDO getModel() {
		return login;
	}
}
