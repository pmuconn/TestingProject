/*
 * Created on Sep 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.misc;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestStrangeDouble
{

	public static void main(String[] args)
	{
		double someDouble = 0.279;
		String someString = "";
		
		//first test
		float f = 0.279F;
		f = f * 100.0F;
		someString = Double.toString(someDouble* 100.0D );
		System.out.println("someString = " + someString + ", f = " +f);
		BigDecimal bd = new BigDecimal(0.279);
		BigDecimal bdmult = new BigDecimal(100);
		bd.setScale(2,BigDecimal.ROUND_HALF_UP);
		bd = bd.multiply(bdmult);
		bd.setScale(2,BigDecimal.ROUND_HALF_UP);
		System.out.println("big decimal result = " + bd + 
				" dbl val = " +bd.doubleValue());

		//second test
		Double percent = new Double(0.279);
		NumberFormat percentFormatter;
		String percentOut;

		percentFormatter = NumberFormat.getPercentInstance();
		percentFormatter.setMaximumFractionDigits(2);
		percentOut = percentFormatter.format(percent);
		System.out.println("Percent = " + percentOut);

		//third test
		double p = ((double) 5.279) * 100;
        java.text.DecimalFormat fmt = new java.text.DecimalFormat("0.00");
        System.out.println("another percent " + fmt.format(p) );

	}
	
	  /**
	   * Performs round of a double value.
	   * @param a is the double value to be rounded
	   * @param scale is the round precision (ie. 2 for 2 places)
	   * @return the rounded double.
	   */
	  public static double roundDecDigits(double a, int scale) {
	    double num = scale;
	    double _deka_ = 10.0;
	    double c = Math.pow(_deka_, num);
	    double d = a * c;
	    double result1 = java.lang.Math.round(d);
	    double the_result;
	    the_result = result1 / c;
	    return the_result;
	  }

	  /**
	   * Performs round of a double value.
	   * @param a is the double value to be rounded
	   * @param scale is the round precision (ie. 2 for 2 places)
	   * @return the rounded double.
	   */
	  public static double convertDecimal(double a, int scale) {
	    double num = scale;
	    double _deka_ = 10.0;
	    double c = Math.pow(_deka_, num);
	    double d = a * c;
	    double result1 = java.lang.Math.round(d);
	    double the_result;
	    the_result = result1 / c;
	    return the_result;
	  }
}
