package com.redmintie.fractals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SierpinskiGasket {
	public static final int IMAGE_SIZE = 1000;
	public static final int TRIANGLE_SIZE = 900;
	public static void main(String[] args) {
		int iterations = Util.getIterations();
		System.out.println("Iteration 0...");
		
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		double halfImage = IMAGE_SIZE / 2.0;
		double halfSide = TRIANGLE_SIZE / 2.0;
		double halfHeight = halfSide * Math.sqrt(0.75);
		triangles.add(new Triangle(
				halfImage, halfImage - halfHeight,            // point 1
				halfImage - halfSide, halfImage + halfHeight, // point 2
				halfImage + halfSide, halfImage + halfHeight  // point 3
		));
		save(triangles, 0);
		
		for (int i = 1; i <= iterations; i++) {
			System.out.println("Iteration " + i + "...");
			for (int j = 0; j < triangles.size(); j += 3) {
				Triangle triangle = triangles.remove(j);
				double m1 = (triangle.x1 + triangle.x2) / 2;
				double n1 = (triangle.y1 + triangle.y2) / 2;
				double m2 = (triangle.x2 + triangle.x3) / 2;
				double n2 = (triangle.y2 + triangle.y3) / 2;
				double m3 = (triangle.x3 + triangle.x1) / 2;
				double n3 = (triangle.y3 + triangle.y1) / 2;
				
				triangles.add(j, new Triangle(triangle.x1, triangle.y1, m1, n1, m3, n3));
				triangles.add(j, new Triangle(triangle.x2, triangle.y2, m2, n2, m1, n1));
				triangles.add(j, new Triangle(triangle.x3, triangle.y3, m3, n3, m2, n2));
			}
			save(triangles, i);
		}
		
		System.out.println("Done!");
	}
	public static void save(ArrayList<Triangle> triangles, int stage) {
		BufferedImage img = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		
		g.clearRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
		for (Triangle triangle : triangles) {
			triangle.draw(g);
		}
		g.drawString("STAGE " + stage, 30, 30);
		
		try {
			ImageIO.write(img, "PNG", Util.createFile("fractals/gasket/gasket-" + stage + ".png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static class Triangle {
		public double x1;
		public double y1;
		public double x2;
		public double y2;
		public double x3;
		public double y3;
		public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.x3 = x3;
			this.y3 = y3;
		}
		public void draw(Graphics2D g) {
			g.fillPolygon(new int[] {(int)x1, (int)x2, (int)x3}, new int[] {(int)y1, (int)y2, (int)y3}, 3);
		}
	}
}