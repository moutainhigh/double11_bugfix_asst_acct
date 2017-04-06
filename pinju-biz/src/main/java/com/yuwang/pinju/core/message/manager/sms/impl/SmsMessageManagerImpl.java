package com.yuwang.pinju.core.message.manager.sms.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.message.domain.message.SenderModel;
import com.yuwang.message.jms.manager.MessageJmsManager;
import com.yuwang.pinju.core.message.manager.sms.SmsMessageManager;
import com.yuwang.pinju.domain.message.SmsMessage;

public class SmsMessageManagerImpl implements SmsMessageManager {
	
	private final static Log log = LogFactory.getLog(SmsMessageManagerImpl.class);

	private MessageJmsManager messageJmsManager;

	public void setMessageJmsManager(MessageJmsManager messageJmsManager) {
		this.messageJmsManager = messageJmsManager;
	}

	public void sendSms(SmsMessage sms) {
		if (sms == null) {
			log.warn("sendSms, parameter sms is null");
			return;
		}
		SenderModel model = sms.createSenderModel();
		if (model == null) {
			log.warn("sendSms, model is null");
			return;
		}
		messageJmsManager.sendSMSSend(model);
	}
}
