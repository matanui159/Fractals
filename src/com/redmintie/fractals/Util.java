package com.redmintie.fractals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
	private static BufferedReader reader;
	public static BufferedReader getReader() {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		return reader;
	}
	public static int getIterations() {
		BufferedReader reader = getReader();
		while (true) {
			System.out.print("How many iterations? ");
			try {
				return Integer.parseInt(reader.readLine());
			} catch (IOException ex) {
				ex.printStackTrace();
				System.exit(0);
			} catch (NumberFormatException ex) {
				System.out.println("Please enter an integer.");
			}
		}
	}
	public static File createFile(String path) {
		File file = new File(path);
		if (!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}
}