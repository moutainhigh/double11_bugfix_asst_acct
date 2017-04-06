package com.yuwang.pinju.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.web.cookie.convert.SessionIdGenerator;
import com.yuwang.pinju.web.system.ServletUtil;

public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(CaptchaServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isNormalReferer(request)) {
			return;
		}

		String sid = request.getParameter("sid");
		if (!SessionIdGenerator.validateHashSessionId(sid)) {
			log.warn("captcha sid is invalid, sid: " + sid);
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("sid: " + sid);
		}
		setCaptchaHeader(response);
		CaptchaManager captchaManager = SpringBeanFactory.getBean(CaptchaManager.class);
		BufferedImage img = captchaManager.generateCaptcha(sid);
		log.debug("captcha manager generated captcha image object");
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(img, "jpeg", sos);
		sos.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	private void setCaptchaHeader(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setContentType("image/jpeg");
	}

	private boolean isNormalReferer(HttpServletRequest request) {
		if (ServletUtil.isPinjuReferer()) {
			return true;
		}
		String referer = request.getHeader("Referer");
		if (referer == null) {
			log.warn("can not get http referer header then do not show captcha, for safety");
			return false;
		}
		log.warn("http referer header: [" + ServletUtil.getHttpReferer() + "] is not start with: \"http://www.pinju.com/\" or \"https://www.pinju.com/\"");
		return false;
	}
}
