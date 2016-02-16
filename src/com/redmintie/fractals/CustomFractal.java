package com.redmintie.fractals;

import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CustomFractal {
	public static final int IMAGE_SIZE = 1000;
	public static final int SQUARE_SIZE = 900;
	public static void main(String[] args) {
		int iterations = Util.getIterations();
		System.out.println("Iteration 0...");
		
		ArrayList<Square> squares = new ArrayList<Square>();
		double pos = IMAGE_SIZE / 2 - SQUARE_SIZE / 2;
		squares.add(new Square(pos, pos, SQUARE_SIZE));
		save(squares, 0);
		
		for (int i = 1; i <= iterations; i++) {
			System.out.println("Iteration " + i + "...");
			for (int j = 0; j < squares.size(); j += 5) {
				Square square = squares.remove(j);
				double m1 = square.x + square.size / 3;
				double n1 = square.y + square.size / 3;
				double m2 = square.x + square.size / 3 * 2;
				double n2 = square.y + square.size / 3 * 2;
				double size = square.size / 3;
				
				squares.add(j, new Square(m1, square.y, size));
				squares.add(j, new Square(square.x, n1, size));
				squares.add(j, new Square(m1, n1, size));
				squares.add(j, new Square(m2, n1, size));
				squares.add(j, new Square(m1, n2, size));
			}
			save(squares, i);
		}
		
		System.out.println("Done!");
	}
	public static void save(ArrayList<Square> squares, int stage) {
		BufferedImage img = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		
		g.clearRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
		for (Square square : squares) {
			square.draw(g);
		}
		g.drawString("STAGE " + stage, 30, 30);
		
		try {
			ImageIO.write(img, "PNG", Util.createFile("fractals/custom/custom-" + stage + ".png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static class Square {
		public double x;
		public double y;
		public double size;
		public Square(double x, double y, double size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
		public void draw(Graphics2D g) {
			g.fillRect((int)x, (int)y, (int)size + 1, (int)size + 1);
		}
	}
}