package com;

public class DoubleValueOf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String oldString = "0.55";
		
		String newString = String.valueOf(Double.valueOf(oldString)*100.0D);
		
		System.out.println("Converted string: " + newString);
		
		double newdouble = PaulUtil.roundDecDigits(Double.valueOf(oldString)*100, 2);
		System.out.println("Converted string2: " + newdouble);
		
	}

}
