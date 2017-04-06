package com.yuwang.pinju.core.message.manager.sms;

import com.yuwang.pinju.domain.message.SmsMessage;

/**
 * <p>短消息发送</p>
 *
 * @author gaobaowen
 * @since 2011-11-22 10:28:01
 */
public interface SmsMessageManager {

	void sendSms(SmsMessage sms);
}
