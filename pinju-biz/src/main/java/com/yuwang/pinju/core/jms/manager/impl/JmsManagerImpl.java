package com.yuwang.pinju.core.jms.manager.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.jms.manager.JmsManager;
import com.yuwang.pinju.core.rate.json.DsrOrderCommentDO;
import com.yuwang.pinju.domain.member.ConcernDO;
import com.yuwang.pinju.domain.member.PrivateMailDO;

public class JmsManagerImpl implements JmsManager {

	private final static Log log = LogFactory.getLog(JmsManagerImpl.class);

	private JmsTemplate dsrCommentTemplate;
	private JmsTemplate sendPMailTemplate;
	private JmsTemplate sendConcernTemplate;

	public void setDsrCommentTemplate(JmsTemplate dsrCommentTemplate) {
		this.dsrCommentTemplate = dsrCommentTemplate;
	}

	public void setSendPMailTemplate(JmsTemplate sendPMailTemplate) {
		this.sendPMailTemplate = sendPMailTemplate;
	}

	public void setSendConcernTemplate(JmsTemplate sendConcernTemplate) {
		this.sendConcernTemplate = sendConcernTemplate;
	}

	@Override
	public void sendDsrComment(DsrOrderCommentDO orderComment) throws ManagerException {
		if (orderComment == null) {
			log.error("orderComment is null can not send comment to MQ");
			return;
		}
		log.info("sendDsrComment start, order id: " + orderComment.getOrderId());
		JSONObject json = JSONObject.fromObject(orderComment);
		final String message = json.toString();
		try {
			dsrCommentTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
			log.info("sendDsrComment finished, order id: " + orderComment.getOrderId());
		} catch (Exception e) {
			throw new ManagerException("sendDsrComment error, message: " + message, e);
		}
	}

	@Override
	public void sendConcern(final ConcernDO concernDO) {
		// TODO Auto-generated method stub
		log.info("sendConcern start");
		sendConcernTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				String message = JSONObject.fromObject(concernDO).toString();
				if (log.isDebugEnabled()) {
					log.debug("sendConcern, message: " + message);
				}
				Message msg = session.createTextMessage(message);
				return msg;
			}
		});
		log.info("sendConcern finished");
	}

	@Override
	public void sendPrivateMail(final PrivateMailDO privateMail) {
		log.info("sendPrivateMail start");
		sendPMailTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				String message = JSONObject.fromObject(privateMail).toString();
				if (log.isDebugEnabled()) {
					log.debug("sendPrivateMail, message: " + message);
				}
				Message msg = session.createTextMessage(message);
				return msg;
			}
		});
		log.info("sendPrivateMail finished");
	}
}
