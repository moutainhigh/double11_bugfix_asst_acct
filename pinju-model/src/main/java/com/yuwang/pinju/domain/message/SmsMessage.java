package com.yuwang.pinju.domain.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.message.domain.message.SenderModel;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;

public class SmsMessage {

	private final static Log log = LogFactory.getLog(SmsMessage.class);

	/**
	 * 短信消息类型
	 */
	private SmsMessageType type;

	/**
	 * 发送的手机号码
	 */
	private String mobile;

	/**
	 * 用于填充模板的参数集
	 */
	private Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * 消息编号
	 */
	private String messageId;

	/**
	 * 发送客户端IP
	 */
	private String clientIp;

	public SmsMessage(MemberSecurityMobileCodeDO mc) {
		this.type = SmsMessageType.getSmsMessageType(mc.getCodeType());
		if (type == null) {
			log.warn("construct SmsMessage object, type is null, " + mc);
		}
		this.mobile = mc.getMobile();
		this.messageId = mc.getMessageId();
		this.clientIp = mc.getCodeIp();
	}

	public SmsMessage(SmsMessageType type, String mobile, String messageId, String clientIp) {
		this.type = type;
		this.mobile = mobile;
		this.messageId = messageId;
		this.clientIp = clientIp;
	}

	public SmsMessageType getType() {
		return type;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}

	public SenderModel createSenderModel() {
		if (type == null) {
			return null;
		}
		return type.createSender(mobile, parameters, messageId, clientIp);
	}
}
