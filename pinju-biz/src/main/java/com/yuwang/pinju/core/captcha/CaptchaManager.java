package com.yuwang.pinju.core.captcha;

import java.awt.image.BufferedImage;

/**
 * <p>captcha manager</p>
 *
 * @author gaobaowen
 * @since 2011-7-28 15:34:59
 */
public interface CaptchaManager {

	/**
	 * <p>generate captcha image</p>
	 *
	 * @param sessionId session id
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 15:35:15
	 */
	BufferedImage generateCaptcha(String sessionId);

	/**
	 * <p>validate correct user input character. clear current member captcha cache after validating correct.</p>
	 *
	 * @param sessionId session id
	 * @param userCaptcha captcha user inputted
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 15:35:32
	 */
	boolean validate(String sessionId, String userCaptcha);
}
