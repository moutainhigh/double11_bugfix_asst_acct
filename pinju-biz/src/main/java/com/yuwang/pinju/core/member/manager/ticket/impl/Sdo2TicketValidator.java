package com.yuwang.pinju.core.member.manager.ticket.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.member.manager.ticket.TicketValidator;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.http.HttpService;
import com.yuwang.pinju.core.util.http.HttpService.HttpResponse;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.domain.member.ticket.TicketValidatorResultDO;

public class Sdo2TicketValidator implements TicketValidator {

	private final static Log log = LogFactory.getLog(Sdo2TicketValidator.class);

	private final static String SDO_VALIDATION_URL = "https://cas.sdo.com/cas/Validate.ex";

	@Override
	public TicketValidatorResultDO validate(MemberTicketDO ticket) {
		HttpService http = new HttpService(SDO_VALIDATION_URL);
		http.addParameter("service", "http://www.pinju.com/member/auth");
		http.addParameter("ticket", ticket.getTicket());
		HttpResponse response = null;
		try {
			response = http.doGet("gb2312");
		} catch (IOException e) {
			log.error("http request error", e);
			return TicketValidatorResultDO.createError(e, ticket);
		}
		String str = response.getStringData();
		if(EmptyUtil.isBlank(str)) {
			log.error("request ticket: " + ticket.getTicket() + " response is empty");
			return TicketValidatorResultDO.createError(TicketValidatorResultDO.RESPONSE_EMPTY, ticket);
		}
		TicketValidatorResultDO result = parseResponse(ticket, str);
		log.info("validate resutl: " + result);
		return result;
	}

	private TicketValidatorResultDO parseResponse(MemberTicketDO ticket, String responseStr) {
		String[] strs = responseStr.split("\\s");
		if(strs.length < 2 || !isSuccess(strs[0])) {
			return TicketValidatorResultDO.createError(responseStr, ticket);
		}
		return TicketValidatorResultDO.createSuccess(strs[1], ticket);
	}

	private boolean isSuccess(String state) {
		return "yes".equalsIgnoreCase(state);
	}
}

