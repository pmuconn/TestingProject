/*
 *  ********************************************************************
 *  Copyright UNITEDHEALTH GROUP CORPORATION 2004.
 *  This software and documentation contain confidential and 
 *  proprietary information owned by UnitedHealth Group Corporation.
 *  Unauthorized use and distribution are prohibited.
 *  ********************************************************************
 */ 
package com.misc;

// java imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * UtilTool, Utility class for date convertion
 */
public class UtilTool {

	/** reference to the date format */
    public static SimpleDateFormat format;
    /** reference to the new Date format */
    public static SimpleDateFormat newFormat;
    /** reference to the logger */

    static {
        newFormat = new SimpleDateFormat("MM/dd/yyyy");
        format = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
    }
	/**
		 * 
		 * @param argDate date
		 * 
		 * @return Date date
		 */	
    public static java.util.Date formatDate(java.util.Date argDate) {

            String dateStr = argDate.toString();
            java.util.Date newDate = null;
            try {
                newDate = format.parse(dateStr);
            } catch(ParseException e) {
                System.out.println(e.getMessage());
            }
            
            return newDate;
    }
	/**
	*
	* 
	* 
	* @return SimpleDateFormat date
	*/	
			
	public static SimpleDateFormat getFormat() {
		return format;        
    }
	/**
	 * 
	 * @param argS string
	 * 
	 * @return Date date
	 */	
    public static java.util.Date toUtilDate(String argS) {
            java.util.Date date = null;
            if (argS == null || "".equals(argS))
            {
            	return date;
            }
            try {
                date = (java.util.Date) format.parse(argS);
            } catch (Exception e) {
            	System.out.println("Exception thrown on formatting the date");
            }
            return date;

        }
	/**
	* 
	* @param argDate date
	* @param argDay day
	* @return java.util.Date date
	*/	

    public static java.util.Date getDateAfterDays(java.util.Date argDate, int argDay) {
                java.util.Date date = null;
                try {
                    date = new java.util.Date(argDate.getTime()+(argDay*86400000));
                    return date;
                } catch (Exception e) {
                	System.out.println("Exception thrown on formatting the date");
                }
                return date;
            }

	/**
	 * Returns a Date set the the  first day of the month of the specified date.
	 * @param argDate date to obtain the first day
	 * @return first day of the specifed date
	 */
    public static Date getFirstDayOfDate(Date argDate) {
                Date date = null;
                try {
                    StringTokenizer stToken = new StringTokenizer(dateToString(argDate),"/,-");
                    String month = stToken.nextToken();
                    stToken.nextToken();
                    String year = stToken.nextToken();
                    String dtStr = month+"/"+"01/"+year;
                    date = toUtilNewFormatDate(dtStr);
                } catch (Exception e) {
                	System.out.println("Exception thrown while getting first day of month of a given date.");
                }
                return date;
            }

	/**
	 * Trims the leading and trailing spaces of the specifed string.
	 * @param argStr input string to trim
	 * @return trimmed string or null if the input string is null
	 */            
    public static String trim(String argStr)
    {
        if (argStr == null)
            return argStr;
        else
            return argStr.trim(); 
    }
    
    /**
     * Converts the specifed date to a string
     * @param argDateValue date to be converted
     * @return string representation of the date
     */
    public static String dateToString(Date argDateValue){
        String dateStr = newFormat.format(argDateValue);
        return dateStr;
    }
        
    /**
     * Gets the Elapsed Time
     * 
     * @param argBeginTime begin time
     * @param argEndTime end time
     * @return the Elapsed Time
     */
    public static long getElapsedTime(long argBeginTime,long argEndTime)
    {
        /** local variable to hold the elapsed time */
        long elapsedTime = 0;
        long timeDiff = 0;
        
        timeDiff = argEndTime - argBeginTime;
        
        elapsedTime = timeDiff/100000;
            
        return elapsedTime;
    }
    
    //CC19 - View Customer Contract Details
     /**
      * Checks if the String passed is null 
      * @param argStr begin time
      * @return the Elapsed Time
      * @throws IllegalArgumentException exception
      */
    public static Object isNull(String argStr) throws IllegalArgumentException
    {
        if(argStr == null)
        {
            throw new IllegalArgumentException(argStr+"Object is null");
        }
        else
        {
            return argStr;
        }
    }//End of isNull
        
    /**
     * Converts it to MM/DD/YYYY format date 
     * @param argString begin time
     * @return Util Date
     */
   public static java.util.Date toUtilNewFormatDate(String argString)                    
   {
           java.util.Date date = null;
           if (argString == null || "".equals(argString))
           {
           	return date;
           }
           try {
               date = (java.util.Date) newFormat.parse(argString);
           } 
           catch (Exception e) 
           {
           	System.out.println("Exception thrown on formatting the date");
           }
           return date;

   } 

	/**
	 * Adds the specified number of days to the specified date.
	 * 
	 * @param argDate date to be modified
	 * @param argDays number of days (+ or -) to add
	 * @return resulting date from adding <code>p_days</code> to <code>p_date</code> 
	 *         or <code>null</code> if p_date is <code>null</code>
	 */
	public static java.util.Date calendarDateAdd(java.util.Date argDate, int argDays)
	{
		if (argDate == null)
			return null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(argDate);
		
		calendar.add(Calendar.DATE, argDays);
		return calendar.getTime();
	}

	/**
	 * Pads the specified string with the specified padding character.
	 * 
	 * @param argStr string to be padded. Can be null.
	 * @param argTotalLen total length of the string after padding.  Must be greater than 0.
	 * @param argPadChar character to pad to the string.  Must not be null.
	 * @param argPadOnEnd pad onto the end if true; pad on the beginning if false
	 * @return string of length totalLen
	 */
	public static String padString(String argStr, int argTotalLen, String argPadChar, boolean argPadOnEnd) {

		if (argTotalLen < 1) {
			throw new IllegalArgumentException("Total length is less than 1");
		}
		if (argPadChar == null) {
			throw new IllegalArgumentException("Pad character is null");
		}
		
		StringBuffer sb = new StringBuffer();
		int initialLen = 0;
		if (argStr != null) {
			sb.append(argStr);
			initialLen = argStr.length();
		}

		if (argPadOnEnd) {
			for (int i = initialLen; i < argTotalLen; i++) {
				sb.append(argPadChar);
			}
		} else {
			for (int i = initialLen; i < argTotalLen; i++) {
				sb.insert(0, argPadChar);
			}
		}

		return sb.toString();
	}
	
}  // end of class
