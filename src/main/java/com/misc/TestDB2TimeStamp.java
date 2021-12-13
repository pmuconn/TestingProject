package com.misc;

import java.sql.Timestamp;

public class TestDB2TimeStamp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String DB2timestamp = "2011-12-12-11.49.03.8640500";
		
		Timestamp t = Timestamp.valueOf("2011-12-12 11:49:03.864500");
		System.out.print(t.toString());
		
	}

	
	static String convertSeparators(String input) {
	    char[] chars = input.toCharArray();
	    chars[10] = ' ';
	    chars[13] = ':';
	    chars[16] = ':';
	    return new String(chars);
	}
}
