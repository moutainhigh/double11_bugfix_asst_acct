package com.yuwang.pinju.web.module.ajax.member.mobile;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstantUtil;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;
import com.yuwang.pinju.domain.member.security.SmsSenderResultVO;
import com.yuwang.pinju.domain.member.security.SmsSenderVO;
import com.yuwang.pinju.domain.message.SmsMessageType;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class SmsCodeSenderAjax implements PinjuAction {

	private final static Log log = LogFactory.getLog(SmsCodeSenderAjax.class);

	private final static Pattern TYPE_PATTERN = Pattern.compile("[0-9]{1,3}");

	private String mobile;
	private String type;
	private SmsSenderResultVO result;
	private MemberSecurityAO memberSecurityAO;

	@Override
	public String execute() throws Exception {

		// validate HTTP Referer header
		if (!PinjuConstant.isDevelopment && !ServletUtil.isPinjuReferer()) {
			log.warn("request is not pinju referer, request ip: " + ServletUtil.getRemoteIp() + ", development mode: " + PinjuConstant.isDevelopment + ", referer: " + ServletUtil.getHttpReferer());
			return INVALID_REQUEST;
		}

		// validate sms code type
		SmsMessageType type = generateSmsMessageType();
		if (type == null) {
			log.warn("execute, type: [" + type + "] is incorrect, client ip: [" + ServletUtil.getRemoteIp() + "], mobile: [" + mobile + "]");
			return wrapErrorKey("mobile.code.type.error");
		}

		// construct sms code sender object according mobile parameter
		SmsSenderVO sender = generateSmsSender(type);
		if (sender == null) {
			return SUCCESS;
		}
		Result result = memberSecurityAO.sendSmsCode(sender);
		if (result.isSuccess()) {
			MemberSecurityMobileCodeDO mobileCode = result.getModel(MemberSecurityMobileCodeDO.class);
			return wrapSuccess(mobileCode.getMessageId());
		}

		log.warn("execute, result code: " + result.getResultCode() + ", sender object: " + sender);

		String message = Message.getMessage(result.getResultCode(), PinjuConstant.MOBILE_CODE_DAILY_MAX_COUNT);
		if (!StringUtils.isBlank(message)) {
			return wrapError(message);
		}
		return wrapErrorKey("mobile.code.error");
	}

	private SmsSenderVO generateSmsSender(SmsMessageType type) {		
		if (type.isNeedLogin()) {
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (!login.isLogin()) {
				log.warn("current mobile parameter is null, user not login, response \"mobile.code.type.error\" result code");
				wrapErrorKey("mobile.code.unlogin.error");
				return null;
			}
			return new SmsSenderVO(login.getMemberId(), login.getNickname(), mobile, type, ServletUtil.getRemoteIp());
		}
		if (!PinjuConstantUtil.isMobile(mobile)) {
			log.warn("execute, mobile [" + mobile + "] is invalid, client ip: [" + ServletUtil.getRemoteIp() + "]");
			wrapErrorKey("result.mobile.invalid");
			return null;
		}
		return new SmsSenderVO(mobile, type, ServletUtil.getRemoteIp());
	}

	private String wrapSuccess(String messageId) {
		result = SmsSenderResultVO.createSuccess(messageId);
		return SUCCESS;
	}

	private String wrapError(String message) {
		result = SmsSenderResultVO.createError(message);
		return SUCCESS;
	}

	private String wrapErrorKey(String messageKey) {
		return wrapError(Message.getMessage(messageKey));
	}

	private SmsMessageType generateSmsMessageType() {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		if (!TYPE_PATTERN.matcher(type).matches()) {
			return null;
		}
		return SmsMessageType.getSmsMessageType(Integer.parseInt(type));
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	public SmsSenderResultVO getResult() {
		return result;
	}

	public void setMobile(String mobile) {
		this.mobile = StringUtils.trim(mobile);
	}

	public void setType(String type) {
		this.type = StringUtils.trim(type);
	}
}
