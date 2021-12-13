package com.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * ==============================================================
 * Copyright UNITEDHEALTH GROUP CORPORATION 2009.
 * This software and documentation contain confidential and
 * proprietary information owned by United HealthCare Corporation.
 * Unauthorized use and distribution are prohibited.
 * ===============================================================
 * 
 * MODIFICATION HISTORY
 * Sep 18, 2009	pmorano	Initial version
 * 
 * ===========================================================
 *
 * Creation Date : Sep 18, 2009 2:48:25 PM
 * File : TestListNull.java
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
public class TestListNull{

	public static void main(String[] args) {
		List<String> newList = null;
		
//		if (newList.isEmpty()) {
//			System.out.println("list is empty");
//		}
		
		//test if get(0) returns anything
		newList = new ArrayList();
		String str = newList.get(0);
	}
}
