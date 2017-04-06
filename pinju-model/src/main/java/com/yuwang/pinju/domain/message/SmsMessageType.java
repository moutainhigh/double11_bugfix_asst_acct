package com.yuwang.pinju.domain.message;

import java.util.Date;
import java.util.Map;

import com.yuwang.message.constants.MessageConstants;
import com.yuwang.message.domain.message.SenderModel;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;

/**
 * <p>短信消息类型</p>
 *
 * @author gaobaowen
 * @since 2011-11-23 16:33:46
 */
public enum SmsMessageType {

	/**
	 * 绑定手机号码时发送验证码
	 */
	BOUNDING_CODE("mobile-bounding-code", MemberSecurityMobileCodeDO.CODE_TYPE_MOBILE_BOUND, true),

	/**
	 * 解绑手机号码时发送验证码
	 */
	UNBOUNDING_CODE("mobile-unbounding-code", MemberSecurityMobileCodeDO.CODE_TYPE_MOBILE_UNBOUND, true),

	/**
	 * 使用手机号码找回密码时发送验证码
	 */
	RETRIEVE_PASSWORD("mobile-retrieve-password", MemberSecurityMobileCodeDO.CODE_TYPE_RETRIEVE_PASSWORD, false)
	;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 短信类型
	 */
	private int codeType;

	/**
	 * 验证码的发送是否需要登录会员所绑定的手机号码
	 */
	private boolean needLogin;

	private SmsMessageType(String templateName, int codeType, boolean needLogin) {
		this.templateName = templateName;
		this.codeType = codeType;
		this.needLogin = needLogin;
	}

	public static SmsMessageType getSmsMessageType(int codeType) {
		for (SmsMessageType type : values()) {
			if (type.getCodeType() == codeType) {
				return type;
			}
		}
		return null;
	}

	public String getTemplateName() {
		return templateName;
	}

	public int getCodeType() {
		return codeType;
	}

	public boolean isNeedLogin() {
		return needLogin;
	}

	SenderModel createSender(String mobile, String name, String value, String messageId, String clientIp) {
		SenderModel model = createSender(mobile, null, messageId, clientIp);
		model.addContentParameters(name, value);
		return model;
	}

	SenderModel createSender(String mobile, Map<String, String> parameters, String messageId) {
		return createSender(mobile, parameters, messageId, null);
	}

	SenderModel createSender(String mobile, Map<String, String> parameters, String messageId, String clientIp) {
		SenderModel model = new SenderModel();
		model.setSendType(MessageConstants.CHANNEL_SENDTYPE_SMS);
		model.setContentTemplateName(templateName);
		if (parameters != null) {
			model.setContentParameters(parameters);
		}
		model.setChannel(MessageConstants.SMS_MESSAGE_SYSTEM);
		model.addDest(mobile);
		model.setMessageId(messageId);
		if (clientIp != null) {
			model.setSenderIp(clientIp);
		}
		model.setSendTime(new Date());
		return model;
	}
}
