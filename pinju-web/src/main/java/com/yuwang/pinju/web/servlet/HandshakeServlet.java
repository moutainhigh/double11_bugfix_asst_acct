package com.yuwang.pinju.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.domain.transmission.TransHandshakeResponse;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 安全会话密钥交换
 */
public class HandshakeServlet extends HttpServlet {

	private static final long serialVersionUID = 6826590800024815389L;

	private final static String HANDSHAKE_NAME = "handshake";

	private final static Log log = LogFactory.getLog(HandshakeServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String handshake = req.getParameter(HANDSHAKE_NAME);
		if (!PinjuConstant.isDevelopment && !ServletUtil.isPinjuReferer()) {
			log.warn("handshake service, referer is NOT .pinju.com request, client ip: [" + ServletUtil.getRemoteIp() + "], handshake value: [" + handshake + "]");
			return;
		}
		SecurityTransManager trans = SpringBeanFactory.getBean(SecurityTransManager.class);
		TransHandshakeResponse handshakeResult = trans.handshake(handshake);
		if (handshakeResult == null) {
			log.warn("handshake service, handshake failed, client ip: [" + ServletUtil.getRemoteIp() + "], handshake value: [" + handshake + "]");
			response(req, resp, "", "");
			return;
		}
		response(req, resp, handshakeResult.getTransId(), handshakeResult.getHash());
	}

	private void response(HttpServletRequest req, HttpServletResponse resp, String tid, String keh) throws IOException {
		StringBuilder builder = new StringBuilder(95);
		builder.append("{");
		builder.append("\"tid\":\"").append(tid).append("\"");
		builder.append(",\"keh\":\"").append(keh).append("\"");
		builder.append("}");
		String json = builder.toString();
		resp.setContentType("application/json; charset=UTF-8");
		resp.addHeader("Cache-Control", "no-store, no-cache");
		resp.addHeader("Pragma", "no-cache");
		resp.getOutputStream().print(json);
	}
}
