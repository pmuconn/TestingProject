package com.misc;

import java.util.StringTokenizer;

public class TokenizerTest {

	public static void main(String[] args) {
		String message = " Some code - some description";

		StringTokenizer parser = new StringTokenizer(message,"-");
		
		if (parser.hasMoreTokens()) {
			String firsttoken = parser.nextToken();
			System.out.println("First token: [" + firsttoken + "]");
			System.out.println("First token trim: [" + firsttoken.trim() + "]");
		}
	}
}
