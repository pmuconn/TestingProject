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
 * Jan 7, 2010	pmorano	Initial version
 * 
 * ===========================================================
 *
 * Creation Date : Jan 7, 2010 9:22:00 AM
 * File : ParseString.java
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
public class ParseStringComma{

	public static void main(String[] args) {
		
		
		String errorCodes = "S,C,I";
		
		String code1 = "S";
		String code2 = "C";
		String code3 = "I";
		String code4 = "E";
		
//		int infoCount = code2.indexOf(errorCodes);
		int infoCount = errorCodes.indexOf(code4);
		System.out.println("infocount: " + infoCount);
 		if (infoCount == -1) {
 			System.out.println("ERROR");
 		} else {
 			//Has informational Message
 			System.out.println("INFO");
 		}		
/*		
		
		//can handle states with spaces
//		String msg = " MA  ,   CT, DC";
		
		//can handle without spaces
		String msg = "DC,MA,VT,";

		//just checking for existence of a state
		int doesExist = msg.indexOf("DC");
		System.out.println("Index of string = "+doesExist);
		if (doesExist >0) {
			System.out.println("State Exists.\n");
		}
		
		System.out.println("\n");
		//Using split
		System.out.println("Using split:");
		List<String> msgList = new ArrayList<String>();
		//regex:     \s =whitespace, * = occurs zero or more times
		String[] result = msg.split("\\s*,\\s*"); 
		for ( String string : result ) {
	         System.out.println(string.trim());	//trim to clear leading space from the first state
	         msgList.add(string.trim());
		}
		System.out.println("\n");
	    //Using tokenizer
		//Note: doesnt handle spaces very well, so this is not a good solution.
		System.out.println("Using tokenizer:");
	    StringTokenizer st = new StringTokenizer(msg,",");
	    //display tokens
	     while (st.hasMoreTokens()) {
	     	System.out.println(st.nextToken());
	     }
	     
	
	*/
	     
	     
	}
}
