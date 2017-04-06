package com.yuwang.pinju.core.captcha.ext;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.library.CurvesImageOp;

public class ExtCurvesImageOp extends CurvesImageOp {

	private float strokeMin = 1;
	private float strokeMax = 2;
	private ColorFactory colorFactory;

	public ExtCurvesImageOp() {
		colorFactory = new SingleColorFactory();
	}

	public float getStrokeMin() {
		return strokeMin;
	}

	public void setStrokeMin(float strokeMin) {
		this.strokeMin = strokeMin;
	}

	public float getStrokeMax() {
		return strokeMax;
	}

	public void setStrokeMax(float strokeMax) {
		this.strokeMax = strokeMax;
	}

	public ColorFactory getColorFactory() {
		return colorFactory;
	}

	public void setColorFactory(ColorFactory colorFactory) {
		this.colorFactory = colorFactory;
	}

	private double hermiteSpline(double x1, double a1, double x2, double a2, double t) {
		double t2 = t * t;
		double t3 = t2 * t;
		double b = (-a2 - 2D * a1 - 3D * x1) + 3D * x2;
		double a = (a2 + a1 + 2D * x1) - 2D * x2;
		return a * t3 + b * t2 + a1 * t + x1;
	}

	private double catmullRomSpline(double x0, double x1, double x2, double x3, double t) {
		double a1 = (x2 - x0) / 2D;
		double a2 = (x3 - x1) / 2D;
		return hermiteSpline(x1, a1, x2, a2, t);
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if (dest == null)
			dest = createCompatibleDestImage(src, null);
		double width = dest.getWidth();
		double height = dest.getHeight();
		Graphics2D g2 = (Graphics2D) src.getGraphics();
		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		Random r = new Random();
		int cp = 4 + r.nextInt(3);
		int xPoints[] = new int[cp];
		int yPoints[] = new int[cp];
		width -= 10D;
		for (int i = 0; i < cp; i++) {
			xPoints[i] = (int) (5D + ((double) i * width) / (double) (cp - 1));
			yPoints[i] = (int) (height * (r.nextDouble() * 0.5D + 0.20000000000000001D));
		}

		int subsections = 6;
		int xPointsSpline[] = new int[(cp - 1) * subsections];
		int yPointsSpline[] = new int[(cp - 1) * subsections];
		for (int i = 0; i < cp - 1; i++) {
			double x0 = i <= 0 ? 2 * xPoints[i] - xPoints[i + 1] : xPoints[i - 1];
			double x1 = xPoints[i];
			double x2 = xPoints[i + 1];
			double x3 = i + 2 >= cp ? 2 * xPoints[i + 1] - xPoints[i] : xPoints[i + 2];
			double y0 = i <= 0 ? 2 * yPoints[i] - yPoints[i + 1] : yPoints[i - 1];
			double y1 = yPoints[i];
			double y2 = yPoints[i + 1];
			double y3 = i + 2 >= cp ? 2 * yPoints[i + 1] - yPoints[i] : yPoints[i + 2];
			for (int j = 0; j < subsections; j++) {
				xPointsSpline[i * subsections + j] = (int) catmullRomSpline(x0, x1, x2, x3,
						(1.0D / (double) subsections) * (double) j);
				yPointsSpline[i * subsections + j] = (int) catmullRomSpline(y0, y1, y2, y3,
						(1.0D / (double) subsections) * (double) j);
			}

		}

		for (int i = 0; i < xPointsSpline.length - 1; i++) {
			g2.setColor(colorFactory.getColor(i));
			g2.setStroke(new BasicStroke(strokeMin + (strokeMax - strokeMin) * r.nextFloat()));
			g2.drawLine(xPointsSpline[i], yPointsSpline[i], xPointsSpline[i + 1], yPointsSpline[i + 1]);
		}

		return src;
	}
}