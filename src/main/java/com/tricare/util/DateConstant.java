package com.tricare.util;

import java.sql.Date;

/**
 * A Collection of commonly used Date Strings and values.
 * @since Q1 2013 - TriCare West
 */
public class DateConstant {
	
	/** Beginning of time Date equal to Date.valueOf("0000-01-01") */
	public static final Date BEGINNING_OF_TIME = Date.valueOf("0001-01-01");
	
	/** Date Format = "yyyyDDDHHmmss" */
	public static final String CCYYDDDHHMMSS = "yyyyDDDHHmmss";
	
	/** Date Format = "yyyyMMddHHmmss" */
	public static final String CCYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	/** Date Format = "yyyyMMddHHmmss'Z'" */
	public static final String CCYYMMDDHHMMSSZ = "yyyyMMddHHmmss'Z'";
	
	/** Date */
	public static final String DATE = "Date";
	
	/** End of time Date equal to Date.valueOf("9999-12-31") */
	public static final Date END_OF_TIME = Date.valueOf("9999-12-31");

	/** Date Format = "EEE MMM d hh:mm:ss z yyyy" */
	public static final String FORMAT_EEE = "EEE MMM d hh:mm:ss z yyyy";
	
	/** Date Format = "MM/dd/yyyy" */
	public static final String FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
	
	/** Date Format = "MM/yy" */
	public static final String FORMAT_MM_YY = "MM/yy";
	
	/** Month Format = "MMMM" */
	public static final String FORMAT_MONTH_MMMM = "MMMM";
	
	/** Date Format = "yyyy-MM-dd" */
	public static final String FORMAT_YYYYDASHMMDASHDD = "yyyy-MM-dd";

	/** Date Format = "yyyy-MM-dd hh:mm:ss" */
	public static final String FORMAT_YYYYDASHMMDASHDDSPHHCOLONMINCOLONMINSS = "yyyy-MM-dd hh:mm:ss";
	
	/** Date Format = "yyyyMMdd" */
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	
	/** Time Format = "HH:mm:ss" */
	public static final String HH_MM_SS = "HH:mm:ss";
	
	/** Time format */
	public static final String HHMMSS = "HHmmss";
	
	
	/** Default Constructor */
	private DateConstant() {}
	
}