package com.yuwang.pinju.core.captcha.ext;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.RippleFilterFactory;
import org.patchca.font.FontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.text.renderer.TextString;
import org.patchca.word.WordFactory;

import com.yuwang.pinju.core.captcha.PinjuCaptcha;

/**
 * <p>pcatcha captcha extension</p>
 *
 * @author gaobaowen
 * @since 2011-7-26 19:38:49
 */
public class ExtConfigurableCaptchaService extends ConfigurableCaptchaService {

	/**
	 * generate random captcha character
	 */
	private final static WordFactory _wordFactory = new WordFactory() {
		public String getNextWord() {
			char[] chs = new char[PinjuCaptcha.CAPTCHA_CHAR_COUNT];
			for (int i = 0; i < chs.length; i++) {
				chs[i] = PinjuCaptcha.nextChar();
			}
			return new String(chs);
		}
	};

	/**
	 * generate captcha character foreground color
	 */
	private final static ColorFactory _colorFactory = new ColorFactory() {
		public Color getColor(int i) {
			return PinjuCaptcha.nextColor();
		}
	};

	/**
	 * generate captcha image background color
	 */
	private final static BackgroundFactory _backgroundFactory = new BackgroundFactory() {
		public void fillBackground(BufferedImage image) {
			Graphics g = image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
		}
	};

	/**
	 * setting captcha character font
	 */
	private final static FontFactory _fontFactory = new FontFactory() {
		public Font getFont(int i) {
			return PinjuCaptcha.nextFont();
		}
	};

	/**
	 * setting captcha text renderer
	 */
	private final static TextRenderer _textRenderer = new BestFitTextRenderer() {{
		setLeftMargin(PinjuCaptcha.CAPTCHA_MARGIN_LEFT);
		setRightMargin(PinjuCaptcha.CAPTCHA_MARGIN_RIGHT);
	}
		protected void arrangeCharacters(int width, int height, TextString ts) {
			super.arrangeCharacters(width, height, ts);
		}
	};

	public ExtConfigurableCaptchaService() {
		backgroundFactory = _backgroundFactory;
		wordFactory = _wordFactory;
		fontFactory = _fontFactory;
		textRenderer = _textRenderer;
		colorFactory = _colorFactory;
		filterFactory = new PinjuFilterFactory();
		width = PinjuCaptcha.CAPTCHA_WIDTH;
		height = PinjuCaptcha.CAPTCHA_HEIGHT;
	}

	public Captcha getCaptcha() {
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        backgroundFactory.fillBackground(bufImage);
        String word = wordFactory.getNextWord();
        textRenderer.draw(word, bufImage, fontFactory, colorFactory);
        bufImage = filterFactory.applyFilters(bufImage);
        return new Captcha(word, bufImage);
    }

	/**
	 * <p>captcha addition process, add interference curve</p>
	 *
	 * @author gaobaowen
	 * @since 2011-7-26 19:47:36
	 */
	private static class PinjuFilterFactory extends RippleFilterFactory {
		public PinjuFilterFactory() {
			ripple = new ExtRippleImageOp();
		}

		protected List<BufferedImageOp> getPreRippleFilters() {
			List<BufferedImageOp> list = new ArrayList<BufferedImageOp>();
//			list.add(new ExtCurvesImageOp());
			return list;
		}
	}
}
