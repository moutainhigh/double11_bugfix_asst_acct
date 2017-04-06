package com.yuwang.api;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.NumberUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * Open API 跳转
 */
public class OpenApiTokenAction implements PinjuAction {

	public final static String TOKEN_URL_TEMPLATE = PinjuConstant.PINJU_SERVER + "/member/token.htm?param1={0}&param2={1}&param3={2}";

	private final static Log log = LogFactory.getLog(OpenApiTokenAction.class);

	private final static long MAX_DIFF_TIME = 60L * 1000;

	/**
	 * Access Token
	 */
	private String param1;

	/**
	 * MAC
	 */
	private String param2;

	/**
	 * time
	 */
	private String param3;

	private String success;
	private String token;

	private MemberSecurityManager memberSecurityManager;

	@Override
	public String execute() throws Exception {
		if (StringUtils.isBlank(param1) || StringUtils.isBlank(param2) || StringUtils.isBlank(param3)) {
			log.warn("parameter has error, param1: [" + param1 + "], param2: [" + param2 + "], param3: [" + param3 + "], client ip: [" + ServletUtil.getRemoteIp() + "]");
			return SUCCESS;
		}

		String tempToken = memberSecurityManager.decryptMessage(param1);
		String mac = memberSecurityManager.macMessageBase64(tempToken + param3);
		if (!param2.equals(mac)) {
			log.warn("mac error, param1: [" + param1 + "], param2: [" + param2 + "], param3: [" + param3 + "], calculate mac: [" + mac + "], client ip: [" + ServletUtil.getRemoteIp() + "]");
			return SUCCESS;
		}

		long time = NumberUtil.parseLong(param3, 0);
		if (time <= 0 || System.currentTimeMillis() - time > MAX_DIFF_TIME) {
			log.warn("time exceed, param1: [" + param1 + "], param2: [" + param2 + "], param3: [" + param3 + "], current time: [" + System.currentTimeMillis() + "], client ip: [" + ServletUtil.getRemoteIp() + "]");
			return SUCCESS;
		}

		success = "SUCCESS";
		token = tempToken;
		return SUCCESS;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public String getSuccess() {
		return success;
	}
	public String getToken() {
		return token;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}
}
