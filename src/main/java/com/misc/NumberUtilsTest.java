package com.misc;

import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtilsTest {

	public static void main(String[] args) {
		
		System.out.println("Is <> zero test");
		System.out.println("0.00" + " " + isZero("0.00"));
		System.out.println("0" + " " + isZero("0"));
		System.out.println("0.1" + " " + isZero("0.1"));
		System.out.println("1" + " " + isZero("1"));
		System.out.println("-0.1" + " " + isZero("-0.1"));
		
	}

	private static boolean isZero(String numberToCheck) {
        boolean result = false;
        if (NumberUtils.toDouble(numberToCheck) != 0) {
            result = true;
        }
        return result;
	}
}
