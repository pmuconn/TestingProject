package com;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTimeStamp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		convertToUtilDate("2012-01-05 12473774","yyyy-MM-dd HHmmssSS");
//		convertToUtilDate("2012-01-05 14200311","yyyy-MM-dd HHmmssSS");
//		convertToUtilDate("2012-01-05 08315021","yyyy-MM-dd HHmmssSS");
//		convertToUtilDate("2012-01-05 06104670","yyyy-MM-dd HHmmssSS");
//		convertToUtilDate("2011-12-12-11.49.03","yyyy-MM-dd-HH.mm.ss");
//		convertToUtilDate("2011-12-12-11.49.03.8640500","yyyy-MM-dd-HH.mm.ss.SSSZ");
		
//		Timestamp t = Timestamp.valueOf("2011-12-12 11:49:03.864500");
//		System.out.print(t.toString());
		
		//the convert should return an 8 character timestamp.
		Timestamp t = new Timestamp(new Date().getTime());
		System.out.println(t.toString());
		convertTimeToString(t);
		
		
		//like prime timestamp:
		String ptime = "10511297";
		Timestamp pt = stringToTimestamp(ptime);
		System.out.println(pt.toString());
		convertTimeToString(pt);
		
	}

	public static Timestamp stringToTimestamp(String inTimestamp) {
		Timestamp returnValue;
		
		long currentEmployeePlanTimestamp = Long.parseLong(inTimestamp);
		System.out.println("Long before convert to timestamp: " +currentEmployeePlanTimestamp );
		returnValue = new Timestamp(currentEmployeePlanTimestamp);
		System.out.println("Timestamp value: " + returnValue);
		return returnValue;
	}
	
	public static String convertTimeToString(Timestamp inTimestamp) {
		String returnValue = "";
	    if (inTimestamp != null) {
	      SimpleDateFormat dtFormat = new SimpleDateFormat("HHmmssSS");
	      returnValue = dtFormat.format(inTimestamp);
	      System.out.println("Converted timestamp: " +returnValue);
	    }
		return returnValue;
	}
	public static java.util.Date convertToUtilDate(String argS, String argDateFormat) {

		System.out.println("***");
		System.out.println("In DateTime: " + argS + " In format: " + argDateFormat);
		java.util.Date date = null;
		SimpleDateFormat newFormat = new SimpleDateFormat(argDateFormat);
		try {
			date = (java.util.Date) newFormat.parse(argS);
		} catch (Exception e) {
			System.out.println("exception thrown during parse");
		}
		System.out.println("Date: " + date);
		System.out.println("Date time: " + date.getTime());

		Timestamp time = new Timestamp(date.getTime());
		System.out.println("Date Timestamp value: " + time);

		newFormat = new SimpleDateFormat("HHmmssS");
	    String stringTime = newFormat.format(time);
		System.out.println("Date String Timestamp value: " + stringTime);

		
		
		return date;
	}	
	  }
