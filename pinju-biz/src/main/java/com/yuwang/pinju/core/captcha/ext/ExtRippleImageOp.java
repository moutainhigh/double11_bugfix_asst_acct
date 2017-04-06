package com.yuwang.pinju.core.captcha.ext;

import org.patchca.filter.library.RippleImageOp;

import com.yuwang.pinju.core.captcha.PinjuCaptcha;

public class ExtRippleImageOp extends RippleImageOp {

	public ExtRippleImageOp() {
		super();
	}

	protected void transform(int x, int y, double t[]) {
		transform(x, y, t, 5, 5);
	}

	protected void transform(int x, int y, double t[], double xrandom, double yrandom) {
		double tx = Math.sin((double) y / yWavelength + yrandom);
		double ty = Math.cos((double) x / xWavelength + xrandom);
		t[0] = (double) x + xAmplitude * tx;
		t[1] = (double) y + yAmplitude * ty;
	}

	protected void filter(int inPixels[], int outPixels[], int width, int height) {
		double t[] = new double[2];
		double xrandom = 5D * PinjuCaptcha.nextDouble();
		double yrandom = 5D * PinjuCaptcha.nextDouble();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				transform(x, y, t, xrandom, yrandom);
				int pixel = getPixelBilinear(inPixels, t[0], t[1], width, height, getEdgeMode());
				outPixels[x + y * width] = pixel;
			}
		}
	}
}
