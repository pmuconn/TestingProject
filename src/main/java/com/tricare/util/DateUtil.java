/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <2011>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
*/
package com.tricare.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a date utility tool.
 * @since Q1 2013 - TriCare West
 */
public final class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class.getName());

	
	/**
	 * Adds the supplied "amount" to the supplied Date as indicated by the supplied Date "field". 
	 * @param java.util.Date - Date which to increment or from which to decrement an amount.
	 * @param int - {@link Calendar} field identifier upon which to act.
	 * @param int - the date amount by which to change the supplied Date.
	 * @return java.util.Date - changed Date value.
	 */
	public static Date add(Date inDate, int inField, int inAmount) {    	    
		Calendar returnValue = Calendar.getInstance();
		
		if (inDate == null) {
			logger.error("Date supplied to the DateUtil 'add' method was null.");
	        throw new IllegalArgumentException("The date must not be null");
	    }   
	     
		returnValue.setTime(inDate);
	    
	    switch(inField) {
		    case Calendar.MONTH:
		    	returnValue.add(Calendar.MONTH, inAmount);
		        break;
		    case Calendar.DATE:
		    	returnValue.add(Calendar.DATE, inAmount);
		        break;
		    case Calendar.YEAR:
		    	returnValue.add(Calendar.YEAR, inAmount);
		        break;
		    default: 
		    	logger.error("The calendar field " + inField + " supplied to the DateUtil 'add' method is unsupported.");
		    	throw new IllegalArgumentException("The calendar field " + inField + " is unsupported");
	    }                
	         
	    return returnValue.getTime();        
	}

	/**
	 * Compares two supplied Dates to determine if they are the same or in date Order.</br>
	 * If the second Date logical comes after the first Date or the dates are the same then this
	 * evaluates to TRUE.
	 * @param Date
	 * @param Date - must be equal or after the first date to get a true response.
	 * @return boolean - true when Dates compare; false otherwise.
	 */
	public static boolean compareDateEqualOrdered(Date inDate1, Date inDate2){
		boolean returnValue = false;
		SimpleDateFormat sdfOriginal = new SimpleDateFormat(DateConstant.FORMAT_EEE);
		Date parsedDate1;
		Date parsedDate2;
		
		if (inDate1 != null && inDate2 != null) {
			try {
				parsedDate1 = sdfOriginal.parse(inDate1.toString());
				parsedDate2 = sdfOriginal.parse(inDate2.toString());
				if (parsedDate2.compareTo(parsedDate1) < 0) {
					returnValue = true;
				}
			} catch (ParseException e) {
				logger.warn("Unable to Compare Dates using FORMAT_EEEE : Error was: "+e.getMessage());
			}
		} else {
			logger.warn("Unable to Compare Dates using FORMAT_EEEE because a Date argument was null.");
		}
		
		return returnValue;
	}

	/**
	 * Converts a date to the MM/DD/YYYY format.
	 * @param java.util.Date
	 * @return String
	 */
	public static String convertDateToString(Date inDate) {
		String returnValue = Constant.EMPTY_STRING;

		DateFormat formatter = new SimpleDateFormat(DateConstant.FORMAT_MM_DD_YYYY);
		returnValue = formatter.format(inDate);

		return returnValue;
	}

	/**
	 * Gets the current date formatted as {@link DateConstant#FORMAT_MM_DD_YYYY}
	 * @return String - today's date formatted as {@link DateConstant#FORMAT_MM_DD_YYYY}
	 */
	public static String currentDateAsString(){
		return currentDateAsString(DateConstant.FORMAT_MM_DD_YYYY);
	}
	
	/**
	 * Gets the current date formatted with the supplied format
	 * @param String - the format of today's date
	 * @return String - today's date formatted with the supplied format
	 */
	public static String currentDateAsString(String inFormat) {
		String returnValue;
		DateFormat dateFormat;
		
		dateFormat = new SimpleDateFormat(inFormat);
		returnValue = dateFormat.format(new Date());
		
		return returnValue;
	}
	
	/**
	 * Retrieve the current date with the current time.
	 * @return Date - Current date with the time retained.
	 */
	public static Date currentDatewithTime() {
		return new Date();
	}
	
	/**
	 * Retrieve the current date with the time at 00:00:00
	 * @return Date - Current date with the time zeroed out
	 */
	public static Date currentDateZeroTime() {
		return resetTimeToZero(new Date());
	}
	
	/** 
	 * Get an SQL Timestamp for the current date, with no hours, mins, secs, millis
	 * @return java.sql.Timestamp
	 */
	public static Timestamp currentSqlTimestampNoTime() {
		java.sql.Timestamp returnValue;
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(currentDateZeroTime());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		returnValue = new java.sql.Timestamp(calendar.getTime().getTime());
		
		return returnValue;
	}

	/**
	 * Retrieve the current date as a Timestamp.
	 * @return Timestamp - Current time stamp.
	 */
	public static Timestamp currentTime() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * Converts a String Date to an Apache Fast date Format.
	 * @param java.util.Date
	 * @param String - supplied date's Format
	 * @return String - Apache formatted ate.
	 */
	public static String formatString(String inDate, String inFormat)
	{
	    Date date = stringToDate(inDate, inFormat);     
	            
	    FastDateFormat df = FastDateFormat.getInstance(inFormat, null, null);
	    
	    return df.format(date);
	}
	
	
	public static String formatString(String inDate, String inFormat, String outFormat)
	{
	    String returnValue;;
		Date date;
		SimpleDateFormat dateFormat = new SimpleDateFormat(inFormat);
		
	    try {
	    	date = dateFormat.parse(inDate);
	    	 FastDateFormat df = FastDateFormat.getInstance(outFormat, null, null);
	    	 returnValue = df.format(date);
	    } catch (ParseException e) {
	    	returnValue = "";
	    	logger.warn("Failed to parse inDate string value to a date: " + e + ":Returning current date instead: " + currentDateZeroTime().toString());
	    }
	    return returnValue;
	}


	/**
	 * Get the Date day prior or after based on to the supplied Date.
	 * + means add days to date 
	 * - means prior day
	 * @param Date - the date to subtract from
	 * @return Date - reduced by one day
	 */
	public static Date getDateDay(Date inDateToSubtractFrom,int day) {
		Date returnValue;
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.resetTimeToZero(inDateToSubtractFrom));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		
		returnValue = calendar.getTime();

		return returnValue;
	}
	/**
	 * Get the Date ONE day after the supplied Date.
	 * @param Date - the date to add to.
	 * @return Date - increased by one day.
	 */
	public static Date getDateOneDayAfter(Date inDateToAddTo) {
		Date returnValue;
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.resetTimeToZero(inDateToAddTo));
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		
		returnValue = calendar.getTime();

		return returnValue;
	}
	
	/**
	 * Get the Date ONE day prior to the supplied Date.
	 * @param Date - the date to subtract from
	 * @return Date - reduced by one day
	 */
	public static Date getDateOneDayPrior(Date inDateToSubtractFrom) {
		Date returnValue;
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.resetTimeToZero(inDateToSubtractFrom));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		returnValue = calendar.getTime();

		return returnValue;
	}
	
	/**
	 * Get the Date and Time ONE MINUTE prior to the supplied Date and Time.
	 * @param Date - the date to subtract from.
	 * @return Date - reduced by one minute.
	 */
	public static Date getDateOneMinutePrior(Date inDateToSubtractFrom) {
		Date returnValue;
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.setTime(inDateToSubtractFrom);
		calendar.add(Calendar.MINUTE, -1);
		
		returnValue = calendar.getTime();

		return returnValue;
	}
	
	/**
	 * Format the date "string" to get "mm/dd" string.
	 * @return String - the formatted anniversary date
	 */
	public static String getFormattedMonthDay(String inDateString) {
		String returnValue = Constant.EMPTY_STRING;
		StringBuffer buffer = new StringBuffer();
		
		// Format as mm/yy if available
		if (inDateString.length() == 4) {
			buffer.append(inDateString.substring(0, 2));
			buffer.append(Constant.FORWARD_SLASH);
			buffer.append(inDateString.substring(2));
		} else {
			buffer.append(Constant.UNKNOWN);
		}
		returnValue = buffer.toString();
		
		return returnValue;
	}
	
	/**
	 * Determine if the supplied date is in the past at the CENTURY level based on the supplied Century Integer value.</br>
	 * For example if 22 is supplied as the Century value to be tested and the Date supplied has a Century equivalent to the 21st Century,
	 * this will return TRUE.
	 * @param Date - the date to check to see if it is in the past compared to the supplied Century Integer value.
	 * @param Integer - Century portion of a Date (i.e. 20) to test if it is AFTER the Century portion of the supplied date.
	 * @return boolean - true if date test succeeded, false otherwise
	 */
	public static boolean isCenturyAfter(Date inDateToCheck, Integer inCenturyTestAfter) {
		boolean returnValue = false;
		Calendar cal = new GregorianCalendar();
		Integer year;
		Integer century;
		
		cal.setTime(inDateToCheck);
		
		year = new Integer(cal.get(Calendar.YEAR));

		// Ensure Year format is filled to all four positions (CCYY).
		century = new Integer(String.format("%04d", year).substring(0, 2));
		
		if (century < inCenturyTestAfter) {
    		returnValue = true; 
    	} else {
    		logger.warn("Supplied Date and Century Test did NOT indicate the supplied Date was in the past; Date supplied was ["+
    						inDateToCheck+"] and the Century to Test was ["+inCenturyTestAfter+"].");
    	}
		
		return returnValue;
	}

	/**
	 * Determine if the supplied date is in the future at the CENTURY level based on the supplied Century Integer value.</br>
	 * For example if 18 is supplied as the Century value to be tested and the Date supplied has a Century equivalent to the 21st Century,
	 * this will return TRUE.
	 * @param Date - the date to check to see if it is in the FUTURE compared to the supplied Century Integer value.
	 * @param Integer - Century portion of a Date (i.e. 18) to test if it is BEFORE the Century portion of the supplied date.
	 * @return boolean - true if date test succeeded, false otherwise
	 */
	public static boolean isCenturyBefore(Date inDateToCheck, Integer inCenturyTestBefore) {
		boolean returnValue = false;
		Calendar cal = new GregorianCalendar();
		Integer year;
		Integer century;
		
		cal.setTime(inDateToCheck);
		
		year = new Integer(cal.get(Calendar.YEAR));

		// Ensure Year format is filled to all four positions (CCYY).
		century = new Integer(String.format("%04d", year).substring(0, 2));
		
		if (century > inCenturyTestBefore) {
    		returnValue = true; 
    	} else {
    		logger.warn("Supplied Date and Century Test did NOT indicate the supplied Date was in the future; Date supplied was ["+
    						inDateToCheck+"] and the Century to Test was ["+inCenturyTestBefore+"].");
    	}
		
		return returnValue;
	}

	/**
	 * Determine if the given date is equal to {@link DateConstant#BEGINNING_OF_TIME}
	 * @param Date - the date to check
	 * @return boolean - true if the date is equal to the beginning of time
	 */
	public static boolean isDateBeginningOfTime(Date inDate) {
		boolean returnValue = false;
		Date beginningOfTime = DateConstant.BEGINNING_OF_TIME;
		Date resetDate;
		
		resetDate = resetTimeToZero(inDate);
		beginningOfTime = resetTimeToZero(beginningOfTime);
		returnValue = resetDate.equals(beginningOfTime);
		
		return returnValue;
	}
	
	/**
	 * Determine if the given date is equal to {@link DateConstant#END_OF_TIME}
	 * @param Date - the date to check
	 * @return boolean - true if the date is equal to the end of time
	 */
	public static boolean isDateEndOfTime(Date inDate) {
		boolean returnValue = false;
		Date endOfTime = DateConstant.END_OF_TIME;
		Date resetDate;
		
		resetDate = resetTimeToZero(inDate);
		endOfTime = resetTimeToZero(endOfTime);
		returnValue = resetDate.equals(endOfTime);
		
		return returnValue;
	}
	
	/**
	 * Determine if the given date string is equal to {@link DateConstant#END_OF_TIME}
	 * @see #isDateEndOfTime(Date)
	 * @param String - the date string to check
	 * @return boolean - true if the date is equal to the end of time
	 */
	public static boolean isDateEndOfTime(String inDate) {
		return isDateEndOfTime(stringToDate(inDate, DateConstant.FORMAT_MM_DD_YYYY));
	}
	
	/**
	 * Determines if the supplied java.util.Date is formatted in the supplied String Format.
	 * @param Date
	 * @param boolean - when true a date that is in  the future from the current date is allowed.
	 * @param String - the Format to verify.
	 * @return int - when 0 date is successfully verified; when 1 the supplied Date was NULL; when 2 the
	 * 				 supplied Date did not parse in the supplied format; when 3 the Date is in the Future but
	 * 				 the supplied boolean requests that NOT be a valid date.
	 */
	public static int isDateFormatted(Date date, boolean allowFuture, String formatStr) {
		int returnValue = 0;
		String convertedDateString = null;
		SimpleDateFormat sdfOriginal = new SimpleDateFormat(DateConstant.FORMAT_EEE);
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date originalDate = null;
		Date convertedDate = null;
		
		if (date != null) {
			try {
				originalDate = sdfOriginal.parse(date.toString());
				convertedDateString = df.format(originalDate);
				if (!convertedDateString.equals(Constant.EMPTY_STRING)) {
					try {
						convertedDate = df.parse(convertedDateString);
						if (!allowFuture) {
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.HOUR_OF_DAY, 0);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
							if (cal.getTime().before(convertedDate)){
								returnValue = 3;
							}
						}
					} catch (ParseException e) {
						logger.warn("Unable to PARSE Date value supplied to Dateutil.isDateFormatted.  Date is: "+convertedDateString + "  Error was: "+e.getMessage());
						returnValue = 2;
					}
				} else {
					logger.warn("Date value supplied to Dateutil.isDateFormatted FORMATTED to an EMPTY STRING.");
					returnValue = 1;
				}
			} catch (ParseException e) {
				logger.warn("Unable to FORMAT Date value supplied to Dateutil.isDateFormatted.  Error was "+e.getMessage());
				returnValue = 2;
			}
		} else {
			logger.warn("Date value supplied to Dateutil.isDateFormatted was NULL.");			
			returnValue = 1;
		}
		
		return returnValue;

	}
	
	/**
	 * Determine if a date is in the future
	 * @param Date - the date to check to see if in the past
	 * @return boolean - true if date is in range, false otherwise
	 */
	public static boolean isDateInFuture(Date inDateToCheck) {
		return inDateToCheck.after(currentDateZeroTime());
	}
	
	/**
	 * Determine if a date is in the past
	 * @param Date - the date to check to see if in the past
	 * @return boolean - true if date is in range, false otherwise
	 */
	public static boolean isDateInPast(Date inDateToCheck) {
		return inDateToCheck.before(currentDateZeroTime());
	}
	
	/**
	 * Determine if a date is in a date range <b>(inclusive of start and end date)</b>
	 * @param Date - the start date in the range
	 * @param Date - the end date in the range
	 * @param Date - the date to check to see if in the range
	 * @return boolean - true if date is in range, false otherwise
	 */
	public static boolean isDateInRange(Date inStartDate, Date inEndDate, Date inDateToCheck) {
		boolean returnValue = false;
		Date resetStartDate;
		Date resetEndDate;
		Date resetDateToCheck;

		if (Util.areAnyNull(inStartDate, inEndDate, inDateToCheck)) {
			returnValue = false;
			logger.warn("Null date argument(s)");
		} else {
			resetStartDate = resetTimeToZero(inStartDate);
			resetEndDate = resetTimeToZero(inEndDate);
			resetDateToCheck = resetTimeToZero(inDateToCheck);
			if (resetDateToCheck.equals(resetStartDate) || resetDateToCheck.equals(resetEndDate)) {
				returnValue = true;
			} else if (resetDateToCheck.after(resetStartDate) && resetDateToCheck.before(resetEndDate)) {
				returnValue = true;
			}
		}
		
		return returnValue;
	}
	
	 /**
	 * Indicates if the supplied Date can be parsed in the supplied Format.
	 * @param String - an expected date.
	 * @param String - the expected date format.
	 * @return boolean - true when the supplied String is parsed into the supplied format.
	 */
	public static boolean isStringaParseableDate(String inDate, String InExpectedFormat) {
		boolean returnValue;
		Date parsedDate;
		
		try {
			parsedDate = stringToDateNoDefault(inDate, InExpectedFormat);
			logger.debug("Supplied String Date does parse tothis Date: "+parsedDate);
			returnValue = true;
		} catch (ParseException parseErr) {
			returnValue = false;
		}
		
		return returnValue;
	}
	
	 /**
	 * Indicates if the supplied Date can be parsed in the supplied Format and is a valid date
	 * @param String - an expected date.
	 * @param String - the expected date format.
	 * @return boolean - true when the supplied String is parsed into the supplied format and is a valid date
	 */
	public static boolean isStringValidDate(String inDate, String InExpectedFormat) {
		boolean returnValue;
		Date parsedDate = null ;
		SimpleDateFormat dateFormat = new SimpleDateFormat(InExpectedFormat);
		
		try {
			parsedDate = dateFormat.parse(inDate);
			logger.debug("Supplied String Date does parse tothis Date: "+parsedDate);
			returnValue = true;
		} catch (ParseException parseErr) {
			returnValue = false;
		}
		if ((parsedDate != null) && (!dateFormat.format(parsedDate).equals(inDate))){
			returnValue = false;
		}
		
		return returnValue;
	}
	
	/**
	 * Determine if the given date is equal to {@link DateUtil.currentDateZeroTime()}
	 * @param Date - the date to check.
	 * @return boolean - true if the date is equal to the Today's Date.
	 */
	public static boolean isTodaysDate(Date inDate) {
		boolean returnValue = false;
		Date todaysDate = DateUtil.currentDateZeroTime();
		Date resetDate;
		
		resetDate = resetTimeToZero(inDate);
		returnValue = resetDate.equals(todaysDate);
		
		return returnValue;
	}
	
	/** 
	 * Converts a java.util.Date into a java.sql.Date
	 * @param Date - java.util.Date
	 * @return java.sql.Date - java.sql.Date
	 */
	public static java.sql.Date parseDateToSqlDate(Date inDate) {
		java.sql.Date returnValue;
		
		returnValue = new java.sql.Date(inDate.getTime());

		return returnValue;
	}

	/** 
	 * Converts a Date to a formatted string.
	 * <p>
	 * 		<b>NOTE:</b> A null {@code inDate} 
	 * 		will return an EMPTY string
	 * </p>
     * @param String - the format of the output string
     * @param Date - the Date to convert
     * @return String - the date converted to the {@code inDatePattern} format
     */
    public static String parseDateToString(String inDatePattern, Date inDate) {   
    	String returnValue = Constant.EMPTY_STRING;
    	SimpleDateFormat dateFormatter = new SimpleDateFormat();
    	
        if (inDate != null && !Util.isNullOrEmpty(inDatePattern)) {
        	dateFormatter.applyPattern(inDatePattern);
        	returnValue = dateFormatter.format(inDate);
        } else {
        	logger.warn("Failed to parse date to string, date argument is null");
        }
        
        return returnValue;
    }
	

	/**
	 * Retrieve the given date with the time at 00:00:00
	 * @param Date - the date to zero out the time on
	 * @return Date - the given date with 00:00:00 time
	 */
	public static Date resetTimeToZero(Date inDate) {
		Date returnValue = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(inDate);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		returnValue = calendar.getTime();
		
		return returnValue;
	} 

	/**
	 * Assumes String Date is in the format {@link DateConstant#FORMAT_MM_DD_YYYY}.
	 * @see #stringToDate(String, String)
	 * @param String - Date String to convert
	 * @return Date - the parsed date from the input string
	 */
	public static Date stringToDate(String inDate)	{ 
		return stringToDate(inDate, DateConstant.FORMAT_MM_DD_YYYY);
	}
	
        /**
		 * Converts the supplied String to a Date object. If the 
		 * date is <b>NOT</b> parsed then today's date is returned.
		 * @param String - Date String to convert
		 * @param String - the format
		 * @return Date - the parsed date from the input string
		 */
		public static Date stringToDate(String inDate, String inFormat)	{ 
			Date returnValue;
			SimpleDateFormat dateFormat = new SimpleDateFormat(inFormat);
			
		    try {
		    	returnValue = dateFormat.parse(inDate);
		    } catch (ParseException e) {
		    	returnValue = currentDateZeroTime();
		    	logger.warn("Failed to parse inDate string value to a date: " + e + ":Returning current date instead: " + currentDateZeroTime().toString());
		    }

		    return returnValue;	     
		}

	public static Date stringToDateNoDefault(String inDate, String inFormat) throws ParseException
	{
	    SimpleDateFormat dateFormat = new SimpleDateFormat(inFormat);
	    dateFormat.setLenient(false);
	    
	    try
	    {
	        return dateFormat.parse(inDate);
	    }
	    catch (ParseException e)
	    {
	        logger.error("Failed to parse inDate string value to a date: " + e);
	        throw e;
	    }
	}
	
	/**Strips time from Date.
	 * @param String Date String to strip time. Acceptable formats: yyyy-MM-dd or MM/dd/yyyy
	 * @return Date as String stripped of the time stamp
	 * @throws ParseException
	 */
	public static String stripTimeStamp(String dtWithTS) throws ParseException{
		String retVal = "";
		SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMMDDYYYY = new SimpleDateFormat("MM/dd/yyyy");
		
		if(dtWithTS != null && !dtWithTS.isEmpty() ){
					Date dt;
					try{
						dt = sdfUTC.parse(dtWithTS);
						retVal = sdfUTC.format(dt);
					} catch (ParseException e) {
						logger.error("Failed to parse date in format "+"yyyy-MM-dd");
						dt = sdfMMDDYYYY.parse(dtWithTS);
						retVal = sdfMMDDYYYY.format(dt);
					}
		}
		return retVal;
	}
	
	/** Default Constructor */
	private DateUtil() { }
	
  public static Calendar asCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

}