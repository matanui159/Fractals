package com.redmintie.fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Used for my Maths C assignment to demonstrate how you can split up a triangle into 9 smaller triangles.
 */
public class NineTriangles {
	public static final int IMAGE_SIZE = 1000;
	public static final int TRIANGLE_SIZE = 900;
	public static final double TRIANGLE_HEIGHT = Math.sqrt(0.75);
	public static final int LINE_THICKNESS = 2;
	public static void main(String[] args) {
		System.out.println("Creating Image...");
		
		double halfImage = IMAGE_SIZE / 2.0;
		double halfSide = TRIANGLE_SIZE / 2.0;
		double halfHeight = halfSide * TRIANGLE_HEIGHT;
		double x1 = halfImage;
		double y1 = halfImage - halfHeight;
		double x2 = halfImage - halfSide;
		double y2 = halfImage + halfHeight;
		double x3 = halfImage + halfSide;
		double y3 = halfImage + halfHeight;
		double m1 = x1 + (x2 - x1) / 3;
		double n1 = y1 + (y2 - y1) / 3;
		double z1 = x1 + (x2 - x1) / 3 * 2;
		double w1 = y1 + (y2 - y1) / 3 * 2;
		double m2 = x2 + (x3 - x2) / 3;
		double n2 = y2 + (y3 - y2) / 3;
		double z2 = x2 + (x3 - x2) / 3 * 2;
		double w2 = y2 + (y3 - y2) / 3 * 2;
		double m3 = x3 + (x1 - x3) / 3;
		double n3 = y3 + (y1 - y3) / 3;
		double z3 = x3 + (x1 - x3) / 3 * 2;
		double w3 = y3 + (y1 - y3) / 3 * 2;
		double cx = (x1 + x2 + x3) / 3;
		double cy = (y1 + y2 + y3) / 3;
		
		BufferedImage img = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(LINE_THICKNESS));
		
		g.clearRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
		g.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
		g.drawLine((int)x3, (int)y3, (int)x1, (int)y1);
		g.drawLine((int)cx, (int)cy, (int)m1, (int)n1);
		g.drawLine((int)cx, (int)cy, (int)z1, (int)w1);
		g.drawLine((int)cx, (int)cy, (int)m2, (int)n2);
		g.drawLine((int)cx, (int)cy, (int)z2, (int)w2);
		g.drawLine((int)cx, (int)cy, (int)m3, (int)n3);
		g.drawLine((int)cx, (int)cy, (int)z3, (int)w3);
		g.drawLine((int)z1, (int)w1, (int)m2, (int)n2);
		g.drawLine((int)z2, (int)w2, (int)m3, (int)n3);
		g.drawLine((int)z3, (int)w3, (int)m1, (int)n1);
		
		try {
			ImageIO.write(img, "PNG", Util.createFile("fractals/triangles.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Done!");
	}
}