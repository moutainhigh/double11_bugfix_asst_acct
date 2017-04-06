package com.yuwang.pinju.core.captcha.impl;

import java.awt.image.BufferedImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.patchca.service.Captcha;

import com.yuwang.pinju.core.cache.PinjuCacheManager;
import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.captcha.PinjuCaptcha;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.util.EmptyUtil;

public class CaptchaManagerImpl implements CaptchaManager {

	private final static Log log = LogFactory.getLog(CaptchaManagerImpl.class);

	private PinjuCacheManager pinjuMemcachedManager;

	public void setPinjuMemcachedManager(PinjuCacheManager pinjuMemcachedManager) {
		this.pinjuMemcachedManager = pinjuMemcachedManager;
	}

	@Override
	public BufferedImage generateCaptcha(String sessionId) {

		// random generate a new captcha object
		Captcha c = PinjuCaptcha.createCaptcha();

		// put captcha value to memcache using key with current sessin id
		pinjuMemcachedManager.setCaptcha(sessionId, c.getChallenge(), PinjuConstant.CAPTCHA_MEMCACHED_TIMEOUT);
		if (log.isDebugEnabled()) {
			log.debug("generate captcha, session id: " + sessionId + ", captcha: " + c.getChallenge());
		}
		return c.getImage();
	}

	@Override
	public boolean validate(String sessionId, String userCaptcha) {
		if (log.isDebugEnabled()) {
			log.debug("validate captcha, session id: " + sessionId + ", user input captcha: " + userCaptcha);
		}
		if (EmptyUtil.isBlank(sessionId) || EmptyUtil.isBlank(userCaptcha)) {
			log.warn("validate captcha, session id or user input captcha is empty");
			return false;
		}

		// get captcha value of current session id from memcached
		String captcha = pinjuMemcachedManager.getCaptcha(sessionId);

		if (EmptyUtil.isBlank(captcha)) {
			log.warn("validate captcha, captcha in cache is null or empty, validate failed, session id: " + sessionId + ", user captcha: " + userCaptcha);
			return false;
		}

		// with user inputed value to compare case insensitive
		boolean result = userCaptcha.equalsIgnoreCase(captcha);

		if (log.isDebugEnabled()) {
			log.debug("validate captcha, session id: " + sessionId + ", result: " + result + ", captcha: " + captcha
					+ ", user captcha: " + userCaptcha);
		}

		if (PinjuConstant.isDevelopment) {
			log.debug("validate captcha, current environment is development mode, captcha need entire 4444, user captcha [" + userCaptcha + "]");
			if (userCaptcha.equalsIgnoreCase("4444")) {
				result = true;
			}
		}

		// clear captcha value in cache
		boolean clearResult = pinjuMemcachedManager.clearCaptcha(sessionId);
		if (log.isDebugEnabled()) {
			log.debug("validate captcha finished, validate result: " + result + ", clear current session id [" + sessionId
					+ "] cache, clear result: " + clearResult);
		}
		return result;
	}
}
