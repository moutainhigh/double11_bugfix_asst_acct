package com.yuwang.pinju.core.captcha;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import org.patchca.service.Captcha;
import org.patchca.service.CaptchaService;

import com.yuwang.pinju.core.captcha.ext.ExtConfigurableCaptchaService;

/**
 * <p>captcha basic parameters configuration</p>
 *
 * @author gaobaowen
 * @since 2011-8-22 09:19:44
 */
public class PinjuCaptcha {

	public final static float MULTI = 1F;

	/**
	 * captcha image width pixel
	 */
	public final static int CAPTCHA_WIDTH = (int)(80 * MULTI);

	/**
	 * captcha image height pixel
	 */
	public final static int CAPTCHA_HEIGHT = (int)(30 * MULTI);

	/**
	 * captcha character margin with left side image
	 */
	public final static int CAPTCHA_MARGIN_LEFT = (int)(12 * MULTI);

	/**
	 * captcha character margin with right side image
	 */
	public final static int CAPTCHA_MARGIN_RIGHT = (int)(12 * MULTI);

	/**
	 * amount of character
	 */
	public final static int CAPTCHA_CHAR_COUNT = 4;

	/**
	 * captcha character font
	 */
	public final static Font CAPTCHA_FONTS = new Font("Serif", Font.PLAIN, (int)(26 * MULTI));

	/**
	 * captcha colors
	 */
	private final static Color[] COLORS = getColors();

	/**
	 * characters of captcha
	 */
	private final static char[] CAPTCHA_CHARS = {
			'3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'
			// ,
			// 'a', 'b', 'c', 'd', 'e', 'f', 'i', 'k', 'm',
			// 'n', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'
		};

	private final static CaptchaService captchaService = createCaptchaService();
	private final static Random random = new Random();

	private PinjuCaptcha() {
	}

	public static int nextInt(int num) {
		return random.nextInt(num);
	}

	public static double nextDouble() {
		return random.nextDouble();
	}

	public static Captcha createCaptcha() {
		return captchaService.getCaptcha();
	}

	private static CaptchaService createCaptchaService() {
		return new ExtConfigurableCaptchaService();
	}

	public static Color nextColor() {
		return next(COLORS, Color.class);
	}

	public static char nextChar() {
		return CAPTCHA_CHARS[nextInt(CAPTCHA_CHARS.length)];
	}

	public static Font nextFont() {
		return CAPTCHA_FONTS;
	}

	@SuppressWarnings("unchecked")
	private static <T> T next(Object[] objs, Class<T> clazz) {
		if (objs == null || objs.length == 0) {
			return null;
		}
		return (T)(objs[nextInt(objs.length)]);
	}

	private static Color[] getColors() {
		int[] color = {
				0x58002B, 0x35002C, 0x12022C, 0x002443, 0x003F5B,
				0x003D39, 0x003B12, 0x2D4B08, 0x605F00, 0x5C3900,
				0x560007, 0x57001A, 0x121212, 0xAD005B, 0x6D005C,
				0x2C0A5C, 0x004E87, 0x0080B4, 0x007B76, 0x00782B,
				0x5E9619, 0xBDBA00, 0xB57600, 0xA90017, 0xAB003C,
				0x555555
			};
		Color[] colors = new Color[color.length];
		for (int i = 0; i < color.length; i++) {
			colors[i] = new Color((color[i] >>> 16) & 0xff, (color[i] >>> 8) & 0xff, color[i] & 0xff);
		}
		return colors;
	}
}
