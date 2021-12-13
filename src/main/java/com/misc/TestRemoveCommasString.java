package com.misc;
/**
 * <pre>
 * ==============================================================
 * Copyright UNITEDHEALTH GROUP CORPORATION 2010.
 * This software and documentation contain confidential and
 * proprietary information owned by United HealthCare Corporation.
 * Unauthorized use and distribution are prohibited.
 * ===============================================================
 * 
 * MODIFICATION HISTORY
 * Jan 11, 2010	pmorano	Initial version
 * 
 * ===========================================================
 *
 * Creation Date : Jan 11, 2010 1:24:52 PM
 * File : TestRemoveCommasString.java
 * ============================================================
 * 
 * </pre>
 * @author pmorano
 */
/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestRemoveCommasString{

	public static void main(String[] args) {
		String number1 = "1,234.56";
		String number2 = "1234.56";
		
		System.out.println("number1: " + AppUtils.removeCommasFromInput(number1));
		System.out.println("number2: " + AppUtils.removeCommasFromInput(number2));
	}
}
