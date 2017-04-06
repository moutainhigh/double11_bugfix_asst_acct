package com.yuwang.pinju.web.cookie;

import static com.yuwang.pinju.web.cookie.PinjuCookieName.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.cookie.CookieManager;
import com.yuwang.cookie.CookieMapping;
import com.yuwang.cookie.rw.CookieReader;
import com.yuwang.cookie.rw.CookieWriter;
import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.cookie.util.Tools;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.convert.CookieMemberOriginAgreement;
import com.yuwang.pinju.web.cookie.convert.SessionIdGenerator;
import com.yuwang.pinju.web.cookie.convert.Token;
import com.yuwang.pinju.web.interceptor.CookieRefreshTokenInterceptor.RefreshToken;
import com.yuwang.pinju.web.struts2.StrutsActionContext;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>
 * CRM Cookie 管理器
 * </p>
 *
 * @author gaobaowen
 * @since 2011-7-1 09:20:37
 */
public class PinjuCookieManager {

	private final static String COOKIE_MAPPING_KEY = CookieMapping.class.getName() + ".actionContext";

	private final static Log log = LogFactory.getLog(PinjuCookieManager.class);
	
	public static String getShop1() {
		return getShop(CG_SHOP1, CN_SHOP1_PJ1);
	}
	
	public static String getShop2() {
		return getShop(CG_SHOP2, CN_SHOP2_PJ2);
	}

	private static String getShop(String group, String name) {
		CookieReader reader = CookieManager.newCookieReader(group);
		CookieMapping mapping = getCookieMapping();
		if (!reader.readCookies(mapping, ServletActionContext.getResponse())) {
			return null;
		}
		return reader.getOriginal(name, String.class);
	}
	
	public static void clearShop1(){
		clearGroup(CG_SHOP1);
	}
	
	public static void clearShop2(){
		clearGroup(CG_SHOP2);
	}
	
	public static void clearShop() {
		clearGroup(CG_SHOP1);
		clearGroup(CG_SHOP2);
	}

	/**
	 * <p>
	 * 写入新的 Session ID
	 * </p>
	 *
	 * @author gaobaowen
	 * @since 2011-7-1 09:20:48
	 */
	public static Token writeSessionId() {
		CookieWriter writer = CookieManager.newCookieWriter(CG_SESSION);
		Token token = Token.getToken();
		writer.addCookie(CN_SESSION_ID, token);
		writer.write(ServletActionContext.getResponse());
		StrutsActionContext.putValue(token);
		return token;
	}

	public static String getSessionId() {
		String sessionId = getCookieMapping().get(CN_SESSION_ID);
		if (Tools.isBlank(sessionId)) {
			log.debug("getSessionId, cannot get session id from cookie, attemp get session id from action context environment");
			Token token = StrutsActionContext.getValue(Token.class);
			if (token != null) {
				sessionId = token.toCookieString();
				log.debug("getSessionId, find session id in action context environment, session id: " + sessionId);
			}
			if (log.isDebugEnabled() && token == null) {
				log.debug("getSessionId, can not find session in in action context environment");
			}
		}
		return sessionId;
	}

	public static String getHashSessionId(Class<?> clazz) {
		return SessionIdGenerator.getHashSessionId(getSessionId(), clazz);
	}

	public static void writeRefreshToken(RefreshToken refreshToken) {
		CookieWriter writer = CookieManager.newCookieWriter(CG_REFRESH_TOKEN);
		writer.addCookie(CN_REFRESH_T, refreshToken.getCookieValue());
		writer.write(ServletActionContext.getResponse());
	}

	public static String getRefreshToken() {
		CookieReader reader = CookieManager.newCookieReader(CG_REFRESH_TOKEN);
		if (!reader.readCookies(getCookieMapping(), ServletActionContext.getResponse()) ) {
			log.warn("read token " + CG_REFRESH_TOKEN + ", failed");
			return null;
		}
		return (String)reader.getOriginalValue(CN_REFRESH_T);
	}

	/**
	 * <p>
	 * 写入用户登录信息
	 * </p>
	 *
	 * @param user 登录用户数据
	 *
	 * @author gaobaowen
	 * @since 2011-7-1 09:21:14
	 */
	public static void writeLogin(LoginRelationCookie relation) {
		if(relation == null) {
			log.warn("write login, parameter login is null cannot write login cookie information");
			return;
		}
		relation.writeLoginCookies();
	}

	/**
	 * <p>
	 * 清除用户登录数据
	 * </p>
	 *
	 * @author gaobaowen
	 * @since 2011-7-1 09:22:07
	 */
	public static void clearLogin() {
		clearGroup(CG_LOGIN);
	}

	/**
	 * <p>清除用户的 Session 数据</p>
	 *
	 * @author gaobaowen
	 * @since 2011-7-14 09:35:26
	 */
	public static void clearSession() {
		clearGroup(CG_SESSION);
	}

	/**
	 * <p>
	 * 获取所有的客户端 Cookie 信息
	 * </p>
	 *
	 * @return 客户端 Cookie 数据
	 *
	 * @author gaobaowen
	 * @since 2011-7-1 09:24:23
	 */
	public static CookieMapping getCookieMapping() {
		CookieMapping mapping = StrutsActionContext.getValue(CookieMapping.class, COOKIE_MAPPING_KEY);
		if (mapping == null) {
			log.debug("cannot find CookieMapping in ActionContext, build it");
			mapping = CookieManager.getCookieMapping(ServletActionContext.getRequest());
			StrutsActionContext.putValue(mapping, COOKIE_MAPPING_KEY);
		}
		return mapping;
	}

	/**
	 * <p>用户环境中是否已有 Session ID 数据</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-14 上午09:35:54
	 */
	public static boolean hasSessionIdCookie() {
		String sessionId = getCookieMapping().get(CN_SESSION_ID);
		if (Tools.isBlank(sessionId)) {
			return false;
		}
		return Token.checkToken(sessionId);
	}

	/**
	 * <p>
	 * 获取用户登录数据
	 * </p>
	 *
	 * @return 用户登录数据
	 *
	 * @author gaobaowen
	 * @since 2011-7-1 09:28:22
	 */
	static CookieLoginInfo getCookieLoginInfo() {

		// 从 ActionContext 中获取
		CookieLoginInfo login = StrutsActionContext.getValue(CookieLoginInfo.class, CookieLoginInfo.ACTION_CONTEXT_KEY);
		if (login != null) {
			return login;
		}

		// ActionContext 中无法获取从 Cookie 中读取

		log.debug("cannot find CookieLoginInfo in ActionContext, build it");
		HttpServletResponse response = ServletActionContext.getResponse();

		// 创建 session cookie 组数据读取器
		CookieReader sessionReader = CookieManager.newCookieReader(CG_SESSION);

		// 创建 login cookie 组数据读取器
		CookieReader loginReader = CookieManager.newCookieReader(CG_LOGIN);

		// 获取客户端 cookie 数据
		CookieMapping mapping = getCookieMapping();

		// session cookie 组读取失败时
		// 不再清空 session group 和 login group 数据
		if (!sessionReader.readCookies(mapping, response)) {
			log.debug("read session group cookie data failed, need to re-login");
			return new CookieLoginInfo();
		}

		// 创建用户登录数据对象
		login = new CookieLoginInfo();

		// 设置登录数据的 Session ID
		login.setSessionId((String)sessionReader.getCookieValue(CN_SESSION_ID).getCookie().getValue());
		
		// 读取 Session Token 对象
		Token token = sessionReader.getOriginal(CN_SESSION_ID, Token.class);

		// 获取 Session ID 的产生时间
		login.setSessionCreateTime(token.getTokenTime());

		if (log.isDebugEnabled()) {
			log.debug("getCookieLoginInfo, token: " + token);
		}

		// login cookie 组数据读取失败时
		if (!loginReader.readCookies(mapping, response)) {
			log.warn("read login group cookie data failed, need to re-login, login: " + mapping.get(CN_LOGIN_PJ0));
			return returnLoginInfo(login);
		}

		// 读取 login cookie 登录数据项中的 token hash 数据
		String tokenHash = loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_TOKEN_HASH, String.class);
		if (tokenHash == null) {
			log.warn("read login token hash failed, need to re-login, login: " + mapping.get(CN_LOGIN_PJ0));
			return returnLoginInfo(login);
		}

		// 计算 Session ID hash 值，并且比较 hash 
		String cookieTokenHash = tokenHash();
		if (!tokenHash.equals(cookieTokenHash)) {
			log.warn("login token hash [" + tokenHash + "]is not same with cookie token hash [" + cookieTokenHash + "], " +
					"cookie token value: " + login.getSessionId() + ", login cookie data: " + mapping.get(CN_LOGIN_PJ0));
			return returnLoginInfo(login);
		}

		login.setInfoVersion(loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_INFO_VERSION, Long.class));
		login.setLoginTime(loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_LOGINTIME, Long.class));
		login.setMemberId(loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_MEMBER_ID, Long.class));
		CookieMemberOriginAgreement mo = (CookieMemberOriginAgreement)loginReader.getOriginalValue(CN_LOGIN_PJ0, CS_LOGIN_PJ0_ORIGIN_AGREEMENT);
		login.setMemberOrign(mo.getOrigin());
		login.setAgreeAgreement(mo.getAgreeAgreement());
		login.setAccountType(mo.getAccountType());
		login.setUserAgent(loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_USERAGENT, Integer.class));
		login.setTokenHash(cookieTokenHash);
		login.setIp(loginReader.getOriginal(CN_LOGIN_PJ0, CS_LOGIN_PJ0_IP, Long.class));
		login.setNickname(loginReader.getOriginal(CN_LOGIN_NICKNAME, String.class));

		return returnLoginInfo(login);
	}

	private static CookieLoginInfo returnLoginInfo(CookieLoginInfo login) {
		StrutsActionContext.putValue(login, CookieLoginInfo.ACTION_CONTEXT_KEY);
		return login;
	}

	private static void clearGroup(String group) {
		CookieReader loginReader = CookieManager.newCookieReader(group);
		int n = loginReader.clearGroup(ServletActionContext.getResponse());
		if (log.isDebugEnabled()) {
			log.debug("logout clear group cookie: " + group + ", clear count: " + n);
		}
	}

	/**
	 * <p>计算会话编号的 HASH 值</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 11:21:33
	 */
	private static String tokenHash() {
		return CodeUtil.hash(Token.class.getName(), getSessionId());
	}

	public static class LoginRelationCookie {

		private String nickname;
		private long memberId;
		private CookieMemberOriginAgreement mo;
		private long loginTime;
		private long infoVersion;
		private int userAgent;
		private String tokenHash;
		private long ip;

		public LoginRelationCookie(MemberDO member) {
			this.nickname = member.getNickname();
			this.memberId = member.getMemberId();
			this.mo = new CookieMemberOriginAgreement(member);
			this.loginTime = System.currentTimeMillis();
			if(member.getInfoVersion() != null) {
				this.infoVersion = member.getInfoVersion();
			}
			this.userAgent = ServletUtil.getUserAgentHash();
			this.tokenHash = tokenHash();
			this.ip = ServletUtil.getRemoteNumberIp();
		}

		public LoginRelationCookie(CookieLoginInfo login) {
			this.nickname = login.getNickname();
			this.memberId = login.getMemberId();
			this.mo = new CookieMemberOriginAgreement(login.getMemberOrign(), login.getAgreeAgreement(), login.getAccountType());
			this.loginTime = login.getLoginTime();
			this.infoVersion = login.getInfoVersion();
			this.userAgent = login.getUserAgent() == null ? ServletUtil.getUserAgentHash() : login.getUserAgent();
			this.tokenHash = login.getTokenHash() == null ? tokenHash() : login.getTokenHash();
			this.ip = login.getIp();
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public long getMemberId() {
			return memberId;
		}

		public void setMemberId(long memberId) {
			this.memberId = memberId;
		}

		public int getMemberOrignal() {
			if(mo == null) {
				return CookieLoginInfo.MEMBER_ORIGIN_UNKNOW;
			}
			return mo.getOrigin();
		}

		public void setAcceptAgreement(int agreement) {
			if(mo != null) {
				mo.setAgreeAgreement(agreement);
			}
		}

		public long getLoginTime() {
			return loginTime;
		}

		public void setLoginTime(long loginTime) {
			this.loginTime = loginTime;
		}

		public long getInfoVersion() {
			return infoVersion;
		}

		public void setInfoVersion(long infoVersion) {
			this.infoVersion = infoVersion;
		}

		private void writeLoginCookies() {
			CookieWriter writer = CookieManager.newCookieWriter(PinjuCookieName.CG_LOGIN);
			writer.addCookie(CN_LOGIN_NICKNAME, Tools.isBlank(nickname) ? "[UNKNOWN]" : nickname);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_INFO_VERSION, infoVersion);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_LOGINTIME, loginTime);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_MEMBER_ID, memberId);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_ORIGIN_AGREEMENT, mo);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_USERAGENT, userAgent);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_TOKEN_HASH, tokenHash);
			writer.addSubCookie(CN_LOGIN_PJ0, CS_LOGIN_PJ0_IP, ip);
			writer.write(ServletActionContext.getResponse());
		}
	}
}
