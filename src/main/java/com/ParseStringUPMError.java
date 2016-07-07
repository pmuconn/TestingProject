package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ParseStringUPMError{

	public static void main(String[] args) {
//		String msg = "Error in F6417pp4UnetDataServiceImpl: RETURNCODE: 3 (The file(s) accessed by the request is closed), EXPLANATIONCODE: 129 (129)";
		String msg = "STATUS-SYSTEM = CICSSAA1, STATUS-GENERATED-BY = CDB, STATUS-OWNED-BY = CDB, STATUS-SERVICE-NAME = V6435CDI, STATUS-SERVICE-VERSION = 000001 {VR1: CODE-TYPE-DESC = RESPONSE1, CODE = 0099, CODE-DESC = CDB, ADDITIONAL-INFO = INVALID REQUEST - INVALID COVERAGE TYPE}  {VR1: CODE-TYPE-DESC = RESPONSE2, CODE = 003, CODE-DESC = PROGRAM V6435CDI, ADDITIONAL-INFO = LOCATION - VCDI200016 APPLICATION ERROR CDB-CDI-INQ-REQ}";
		List <String>tokenList = new ArrayList<String>();
		List <String>VRlist = new ArrayList<String>();

		//token by {
	    StringTokenizer st = new StringTokenizer(msg,"{");
	    //load arraylist with tokens
	     while (st.hasMoreTokens()) {
	     	tokenList.add(st.nextToken());
	     }
	     //create tokens based on the VR1 lines
	     for (String token : tokenList) {
	     	System.out.println(token);
	     	int indexCount = token.indexOf("VR1");
	     	if (indexCount != -1) {
	     		//found VR1
	     		VRlist.add(token);
	     	}
	     }
	     
	     System.out.println("+++++++++++++++++++++");
	     //once we have the VR list, then we token that and find the code etc.
		List <String>codelist = new ArrayList<String>();
	     for (String vr : VRlist) {
		     System.out.println(vr);
			 st = new StringTokenizer(vr,"{");
//		     StringTokenizer stnew = new StringTokenizer(vr,",");
		     while (st.hasMoreTokens()) {
		    	 codelist.add(st.nextToken());
		     }
	     }
	     
	     System.out.println("+++++++++++++++++++++");
	     for (String vr : codelist) {
		     //token the codelist by commas for code = value tokens
		     st = new StringTokenizer(vr,",");
	    	 //put tokens in a list
		     List <String> codeValueList = new ArrayList<String>();
		     //put into code/value lists
		     while (st.hasMoreTokens()) {
		    	 codeValueList.add(st.nextToken());
		     }
		     //now, each string in the list is a 'code = value'. tokenize by =
		     for (String CodeValue : codeValueList) {
			     Map<String,String> codeValueMap = new HashMap<String,String>();
			     st = new StringTokenizer(CodeValue,"=");
			     List <String> codeValueList2 = new ArrayList<String>();
			     while (st.hasMoreTokens()) {
			    	 codeValueList2.add(new String(st.nextToken()));
			     }
			     boolean test = codeValueList2.contains("CODE");
			     	int indexCount = codeValueList2.indexOf("CODE");
			     	if (indexCount != -1) {
					     String returnCode = (String)codeValueList2.get(codeValueList2.indexOf("CODE")+1);
					     System.out.println("Return Code: " +returnCode);
			     	}
			     	indexCount = codeValueList2.indexOf("ADDITIONAL-INFO");
			     	if (indexCount != -1) {
						 String explanationCode = (String)codeValueList2.get(codeValueList2.indexOf("ADDITIONAL-INFO")+1);
					     System.out.println(" Explanation code: " + explanationCode);
			     	}
		     }
	     }
	}
}
