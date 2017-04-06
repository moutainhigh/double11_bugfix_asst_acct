/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.core.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author liyouguo
 * 
 * @since 2011-7-27
 */
public class PinjuJmsListener implements MessageListener {

	/**
	 * 
	 * @param arg0
	 * 
	 */
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if (message instanceof TextMessage) {
			try {
				System.out.println(((TextMessage) message).getText());
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException(
					"Message must be of type TextMessage");
		}
	}

}
