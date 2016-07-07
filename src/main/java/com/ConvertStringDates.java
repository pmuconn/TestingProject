/*
 * Created on Oct 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConvertStringDates
{

	//Testing how the utildate works for Mark
	
	public static void main(String[] args)
	{
		java.util.Date uDate;
		java.sql.Date sqlDate;
		Timestamp sqlTime;
		
		String sDate = "2007-11-10";
		uDate = PaulUtil.toUtilDate(sDate,"yyyy-MM-dd");
		System.out.println("util date: " + uDate);
		
		sDate = "Mon Apr 13 10:48:12 CDT 2015";
		uDate = PaulUtil.toUtilDate(sDate,"E MMM dd HH:mm:ss z yyyy");
		System.out.println("util date: " + uDate);
	
		uDate = new java.util.Date();
		System.out.println("util date: " + uDate);
		
		java.util.Date utilDate = new java.util.Date();
	    sqlDate = new java.sql.Date(utilDate.getTime());
	    System.out.println("utilDate:" + utilDate);
	    System.out.println("sqlDate:" + sqlDate);
		
		sqlTime = new Timestamp(uDate.getTime());
	    System.out.println("sql timestamp:" + sqlTime);
	    System.out.println("time: " + uDate.getTime());
	    
	    System.out.println("Tests on Date.");
	    uDate = new java.util.Date();
		System.out.println("util date: " + uDate);
		SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = dtFormat.format(uDate);
		System.out.println("just date: " + strDate);
		dtFormat = new SimpleDateFormat("HH:mm:ss");
		String strTime = dtFormat.format(uDate);
		System.out.println("just time: " + strTime);
		dtFormat = new SimpleDateFormat("kk:mm:ss");
		strTime = dtFormat.format(uDate);
		System.out.println("just time (military): " + strTime);
		
		
		
	}
}
