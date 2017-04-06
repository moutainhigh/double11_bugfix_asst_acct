package com.yuwang.pinju.core.member.manager.ticket.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.yuwang.pinju.core.member.manager.ticket.TicketValidator;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.http.HttpService;
import com.yuwang.pinju.core.util.http.HttpService.HttpResponse;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.domain.member.ticket.TicketValidatorResultDO;

public class SdoTicketValidator implements TicketValidator {

	private final static Log log = LogFactory.getLog(SdoTicketValidator.class);

	private final static String SDO_VALIDATION_URL = "https://cas.sdo.com/cas/Validate.release";

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
		if(responseStr.indexOf("<?xml") < 0) {
			responseStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + responseStr;
		}
		if(log.isDebugEnabled()) {
			log.debug("prepare process ticket " + ticket.getTicket() + " response data, process xml:\n" + responseStr);
		}
		try {
			return parseXml(responseStr, ticket);
		} catch (Exception e) {
			log.error("parse xml failed, xml:\n" + responseStr, e);
			return TicketValidatorResultDO.createError(e, ticket);
		}
	}

	private TicketValidatorResultDO parseXml(String xml, MemberTicketDO ticket) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new InputSource(new StringReader(xml)));
		Element root = doc.getRootElement();
		String state = root.getChildText("state");
		log("state", state);
		if(isSuccess(state)) {
			String sdid = root.getChildText("sdid");
			log("sdid", sdid);
			if(!EmptyUtil.isBlank(sdid)) {
				return TicketValidatorResultDO.createSuccess(sdid, ticket);
			}
		}
		String describe = root.getChildText("describe");
		log("describe", describe);
		return TicketValidatorResultDO.createError(state + "|" + describe, ticket);
	}

	private void log(String name, String value) {
		if(log.isDebugEnabled()) {
			log.debug("parseXml, " + name + " = [" + value + "]");
		}
	}

	private boolean isSuccess(String state) {
		return "yes".equalsIgnoreCase(state);
	}
}
