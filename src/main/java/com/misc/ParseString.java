package com.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
public class ParseString{

	public static void main(String[] args) {
//		String msg = "Error in F6417pp4UnetDataServiceImpl: RETURNCODE: 3 (The file(s) accessed by the request is closed), EXPLANATIONCODE: 129 (129)";
		String msg = "Insert request unsuccessful, Foundation Codes: 6/101, ORS Codes: 6/101";
		List tokenList = new ArrayList();

	    StringTokenizer st = new StringTokenizer(msg);
	    
	    //display tokens
	     while (st.hasMoreTokens()) {
	     	System.out.println(st.nextToken());
	     }
	     
	    //load arraylist with tokens
	     while (st.hasMoreTokens()) {
	     	tokenList.add(st.nextToken());
	     }
	     
	     //search list and remove return code and explanation code.
	     String explanationCode = "";
	     String returnCode = "";
		 try {
		 	returnCode = (String)tokenList.get(tokenList.indexOf("RETURNCODE:")+1);
		 	explanationCode = (String)tokenList.get(tokenList.indexOf("EXPLANATIONCODE:")+1);
	     } catch (IndexOutOfBoundsException e){
	     	//do nothing. 
	     }
	     System.out.println("Return Code: " +returnCode+" Explanation code: " + explanationCode);
	     

	     while (st.hasMoreTokens()) {
	     	if ("EXPLANATIONCODE:".equalsIgnoreCase(st.nextToken())) {
	     		explanationCode = st.nextToken();
	     	}
	     	if ("RETURNCODE:".equalsIgnoreCase(st.nextToken())) {
	     		returnCode = st.nextToken();
	     	}
//	     	tokenList.add(st.nextToken());
	     //    System.out.println(st.nextToken());
	     }
	     System.out.println("Return Code: " +returnCode+" Explanation code: " + explanationCode);
	     
	     //test the parsing of this address line
	     System.out.println("TRICARE Address parse: ");
	     //String address = "TRICARE Claims_100 Main St_Suite 999_Anytown, NJ 99999";
	     //String address = "TRICARE Claims_100 Main St_Anytown, NJ 9999-1234";
	     String address ="100 Main St_Anytown, NJ 9999-1234";
//	     String address ="";
	     StringTokenizer stAddress = new StringTokenizer(address,"_");
	     //display tokens
	     while (stAddress.hasMoreTokens()) {
	     	System.out.println(stAddress.nextToken());
	     }
	     
	}
}
