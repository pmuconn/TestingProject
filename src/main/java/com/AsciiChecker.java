package com;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

public class AsciiChecker {

	public static void main(String[] args) {
		char nonAscii = 0x00FF;
		String[] stringsToCheck = new String[] {
				"Hello",
				"Buy: " + nonAscii,
				"Some dash – from word."
        };
		List<String> toCheck = Arrays.asList(stringsToCheck);
		
		testRegex(toCheck);
		testLoop(toCheck);
	}

	public static void testRegex(List<String> stringsToCheck) {
		for (String s : stringsToCheck) {
	        System.out.println(s + " has ascii: " + checkWithRegex(s));
	        System.out.println("--Fixed string: " + correctString(s));
		}
	}

	public static void testLoop(List<String> stringsToCheck) {
		for (String s : stringsToCheck) {
	        System.out.println(s + " has ascii: " + checkByCharLoop(s));
	        System.out.println("--Fixed string: " + correctString(s));
		}
	}

	public static boolean checkWithRegex(String toTest) {
		// \\A - Beginning of input ... \\p{ASCII}* - Any ASCII character any times ...\\z - End of input
		String regex = "\\A\\p{ASCII}*\\z";
		return toTest.matches(regex);
	}
	
	public static boolean checkByCharLoop(String toTest) {
		/**
		 * Iterate through the string and make sure all the characters have a value less than 128.
		 * Java Strings are conceptually encoded as UTF-16. In UTF-16, the ASCII character set is encoded as the values 0 - 127 
		 * and the encoding for any non ASCII character (which may consist of more than one Java char) is guaranteed not to 
		 * include the numbers 0 - 127
		 */
		boolean result = true;
		for (char c : toTest.toCharArray()) {
			if ((c) > 127) { // can also use c > 0x7F
				result = false;
				break;
			}
		}
		return result;
	}
	
	public static String correctString(String badString) {
		String result = Normalizer.normalize(badString, Normalizer.Form.NFD);
		String resultString = result.replaceAll("[^\\x00-\\x7F]", "");
		return resultString;
	}

}
