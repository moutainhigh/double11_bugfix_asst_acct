/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import com.yuwang.api.core.manager.OpenApiApplicationManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.UUIDGeneration14Util;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * @author liyouguo
 * 
 * @since 2011-9-14
 */
public class GetCurrentSignAction extends BaseAction {

	private Long appKey;
	private String format;
	private String method;
	private String appSecret;
	private String sessionKey;

	private String sign;

	/**
	 * 签名生成接口
	 */
	private MemberSecurityManager memberSecurityManager;
	
	/**
	 * 开放API应用
	 */
	private OpenApiApplicationManager openApiApplicationManager;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public MemberSecurityManager getMemberSecurityManager() {
		return memberSecurityManager;
	}

	public void setMemberSecurityManager(
			MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public Long getAppKey() {
		return appKey;
	}

	public void setAppKey(Long appKey) {
		this.appKey = appKey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OpenApiApplicationManager getOpenApiApplicationManager() {
		return openApiApplicationManager;
	}

	public void setOpenApiApplicationManager(
			OpenApiApplicationManager openApiApplicationManager) {
		this.openApiApplicationManager = openApiApplicationManager;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3871289840397415432L;

	public String execute() throws Exception {
		sign = memberSecurityManager.digestMessageHex(getSignData(), null);
		title = "current sign:";
		return SUCCESS;
	}
	
	private String title;

	public String resetSecret() throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		sign = new String(Hex.encodeHex(digest.digest(UUIDGeneration14Util
				.getUUIDHex().getBytes("UTF-8"))));
		title = "new app secret:";
		openApiApplicationManager.resetApplicationSecretById(appKey, sign);
		return SUCCESS;
	}

	private String getSignData() {
		StringBuilder sbf = new StringBuilder();
		sbf.append(appKey).append("|").append(method);
		if (!EmptyUtil.isBlank(sessionKey))
			sbf.append("|").append(sessionKey);
		if (!EmptyUtil.isBlank(format))
			sbf.append("|").append(format);
		sbf.append("|").append(appSecret);
		return sbf.toString();
	}
}
