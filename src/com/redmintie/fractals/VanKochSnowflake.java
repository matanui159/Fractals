package com.redmintie.fractals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class VanKochSnowflake {
	public static final int IMAGE_SIZE = 1000;
	public static final int TRIANGLE_SIZE = 500;
	public static final double TRIANGLE_HEIGHT = Math.sqrt(0.75);
	public static final int LINE_THICKNESS = 1;
	public static void main(String[] args) {
		int iterations = Util.getIterations();
		System.out.println("Iteration 0...");
		
		ArrayList<Line> lines = new ArrayList<Line>();
		double halfImage = IMAGE_SIZE / 2.0;
		double halfSide = TRIANGLE_SIZE / 2.0;
		double halfHeight = halfSide * TRIANGLE_HEIGHT;
		double x1 = halfImage;
		double y1 = halfImage - halfHeight;
		double x2 = halfImage - halfSide;
		double y2 = halfImage + halfHeight;
		double x3 = halfImage + halfSide;
		double y3 = halfImage + halfHeight;
		lines.add(new Line(x1, y1, x2, y2, Math.PI / 3 * 5));
		lines.add(new Line(x2, y2, x3, y3, Math.PI));
		lines.add(new Line(x3, y3, x1, y1, Math.PI / 3));
		save(lines, 0);
		
		for (int i = 1; i <= iterations; i++) {
			System.out.println("Iteration " + i + "...");
			for (int j = 0; j < lines.size(); j += 4) {
				Line line = lines.remove(j);
				double x, y;
				double height = Math.sqrt((x = line.x1 - line.x2) * x + (y = line.y1 - line.y2) * y) / 3
						* TRIANGLE_HEIGHT;
				double m1 = line.x1 + (line.x2 - line.x1) / 3;
				double n1 = line.y1 + (line.y2 - line.y1) / 3;
				double m2 = line.x1 + (line.x2 - line.x1) / 3 * 2;
				double n2 = line.y1 + (line.y2 - line.y1) / 3 * 2;
				double z1 = (line.x1 + line.x2) / 2 + Math.sin(line.normal) * height;
				double w1 = (line.y1 + line.y2) / 2 - Math.cos(line.normal) * height;
				
				lines.add(j, new Line(line.x1, line.y1, m1, n1, line.normal));
				lines.add(j, new Line(m1, n1, z1, w1, line.normal + Math.PI / 3));
				lines.add(j, new Line(z1, w1, m2, n2, line.normal - Math.PI / 3));
				lines.add(j, new Line(m2, n2, line.x2, line.y2, line.normal));
			}
			save(lines, i);
		}
		
		System.out.println("Done!");
	}
	public static void save(ArrayList<Line> lines, int stage) {
		BufferedImage img = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(LINE_THICKNESS));
		
		g.clearRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
		for (Line line : lines) {
			line.draw(g);
		}
		g.drawString("STAGE " + stage, 30, 30);
		
		try {
			ImageIO.write(img, "PNG", Util.createFile("fractals/snowflake/snowflake-" + stage + ".png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static class Line {
		public double x1;
		public double y1;
		public double x2;
		public double y2;
		public double normal;
		public Line(double x1, double y1, double x2, double y2, double normal) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.normal = normal;
		}
		public void draw(Graphics2D g) {
			g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
		}
	}
}