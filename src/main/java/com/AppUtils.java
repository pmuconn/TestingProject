/**
 * <pre>
 * ==============================================================
 * Copyright UNITEDHEALTH GROUP CORPORATION 2008.
 * This software and documentation contain confidential and
 * proprietary information owned by United HealthCare Corporation.
 * Unauthorized use and distribution are prohibited.
 * ===============================================================
 * 
 * MODIFICATION HISTORY
 * 11/2006        	Cognizant Offshore      SPRF# 400011181 (December Release)
 * 											- Added formatAmount() method
 * 01/02/2008     	Cognizant Offshore      SPRF 4x19846. Method added to format
 * 01/2008      	UHG India				Fortify Issues
 * 02/2008      	UHG India				Resolves the issues of Null Dereference(Control Flow).
 * 03/05/2008		UHG India				Non-Serialied Object(Interface referenced) Stored in session
 * 											Fortify warning issue. Add New Support Method  the string amount
 * 09/2008		    UHG India			  	SPRF# 4-23210 (Clinical Self Service). 
 * 2/2009			Amy Huang				B2C Transaction Logging Update for QTR1 2009
 * 											--Replacing old isNumeric() with new isNumeric()
 * 
 * 
 * </pre>
 */

package com;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public final class AppUtils {

	/* Added for SPRF 11181 END */

	// prevent initialization
	private AppUtils() {
	}

	/**
	 * Utility method to check if the input String is null or empty.
	 * 
	 * @param field
	 * @return boolean flag indicating if the input String is null or empty.
	 */
	public static boolean isNull(String argField) {

		return ((null == argField) || "".equals(argField.trim()));
	}

	/**
	 * Utility method to check if the input object is null. If the input object
	 * is of java.lang.String type it checks for the empty case too.
	 * 
	 * @param object
	 *            The input object to be checked for null.
	 * @return boolean flag indicating if the input object is null.
	 */
	public static boolean isNull(Object argObject) {

		if (argObject instanceof java.lang.String) {
			return isNull(argObject.toString());
		} else {
			return (null == argObject);
		}
	}

	/**
	 * Utility method to convert Calendar object to String. The date is 
	 * returned in mmddyyyy format. The seperator to be used can be specified
	 * by the caller.
	 * 
	 * If the input Calendar object is null, an empty String is returned.
	 * 
	 * 
	 * @param date
	 *            The Calendar object to be converted to String date.
	 * @param seperator-
	 *            The seperator to be used.
	 * @return String representation of the date sent in the Calendar object.
	 */

	public static String getDateAsString(Calendar argDate, char argSeperator) {

		if (AppUtils.isNull(argDate)) {
			return "";
		}
		StringBuffer response = new StringBuffer(100);

		int year = argDate.get(Calendar.YEAR);
		int month = argDate.get(Calendar.MONTH) + 1;
		int day = argDate.get(Calendar.DATE);

		if (month < 10) {
			response.append("0");
		}
		response.append(month);
		response.append(argSeperator);
		if (day < 10) {
			response.append("0");
		}
		response.append(day);
		response.append(argSeperator);
		response.append(year);

		return response.toString();
	}

	/*
	 * Expects the in
	 */
	public static String formatDateString(String argDate, char argSeperator) {
		StringBuffer response = new StringBuffer(15);
		if (argDate.length() == 8) {
			response.append(argDate.substring(4, 6)).append(argSeperator).append(
					argDate.substring(6)).append(argSeperator).append(
							argDate.substring(0, 4));
			return response.toString();
		} else if (argDate.length() == 10) {
			response.append(argDate.substring(5, 7)).append(argSeperator).append(
					argDate.substring(8)).append(argSeperator).append(
							argDate.substring(0, 4));
			return response.toString();

		} else {
			return argDate;
		}

	}

	/*
	 * Expects the in
	 */
	public static String insertDateSeparator(String argDate, char argSeperator) {
		if (argDate.length() > 10 && argDate.indexOf("-") > 0) {
			argDate = argDate.substring(0, argDate.indexOf("-"));
		}
		StringBuffer response = new StringBuffer(15);
		if (argDate.length() == 8) {
			/* OUT:yyyy-MM-dd IN:yyyyMMdd */
			response.append(argDate.substring(0, 4)).append(argSeperator).append(
					argDate.substring(4, 6)).append(argSeperator).append(
							argDate.substring(6));
			return response.toString();
		} else if (argDate.length() == 10) {
			/* OUT:yyyy-MM-dd IN:MM/dd/yyyy */
			response.append(argDate.substring(6)).append(argSeperator).append(
					argDate.substring(0, 2)).append(argSeperator).append(
							argDate.substring(3, 5));
			return response.toString();
		} else {
			return argDate;
		}
	}


	/**
	 * This method removes the leading character from the String.
	 * 
	 * @param inputString
	 * @param charToRemove
	 * @return String
	 */
	public static String removeLeadingChar(String argInputString,
			String argCharToRemove) {

		String firstChar = null;

		if (argInputString != null && argInputString.trim().length() > 0) {

			for (int i = 0; i < argInputString.length(); i++) {
				firstChar = argInputString.substring(0, 1);

				if (firstChar != null && firstChar.equals(argCharToRemove)) {
					argInputString = argInputString
							.substring(1, argInputString.length());
					i = 0;
				} else {
					break;
				}

			}

		} else {
			return "";
		}

		return argInputString;

	}

	public static String removeLeadingZeros(String argString) {
		if (argString == null) {
			return argString;
		}

		int i;
		int iCurrentLen = argString.length();

		for (i = 0; i < iCurrentLen; i++) {
			if (argString.charAt(i) != '0') {
				return (argString.substring(i).trim());
			}
		}

		return (argString.substring(i).trim());
	}

	/**
	 * Utility method to format dollar from float to String with the decimal
	 * size passed from the parameter
	 * 
	 * 
	 * @param _amount
	 *            in float.
	 * @param decimalSize-
	 *            speficy the decimal size you want
	 * @return dollar in the String
	 */
	public static String fromatDollar(float argAmount, int argDecimalSize) {

		String defaultDollar = "0";
		int defaultDollarDecimalSize = 1;
		StringBuffer sb = new StringBuffer();
		sb.append("0.0");
		while (defaultDollarDecimalSize < argDecimalSize) {
			sb.append("0");
			defaultDollarDecimalSize++;
		}
		defaultDollar = sb.toString();

		String dollar = "";

		try {

			dollar = String.valueOf(argAmount);

			if (dollar.indexOf('.') > 0) {
				int dotPos = dollar.indexOf('.');
				int dollardecimalSize = dollar.substring(dotPos + 1,
						dollar.length()).length();
				sb = new StringBuffer();
				sb.append(dollar);
				while (dollardecimalSize < argDecimalSize) {
					sb.append("0");
					dollardecimalSize++;
				}
				dollar = sb.toString();
			}

		} catch (Exception e) {
			dollar = defaultDollar;
		}

		return dollar;

	}

	/**
	 * Utility method to format dollar from float to String with the decimal
	 * size passed from the parameter
	 * 
	 * 
	 * @param dollar
	 *            in String, it can contain either $ or not, if it contains $,
	 *            the return value will have $ as well
	 * @param decimalSize --
	 *            to speficy the decimal size you want
	 * @return dollar in the String
	 */
	public static String formatDollar(String argDollar, int argDecimalSize) {
		String defaultDollar = "0";
		int defaultDollarDecimalSize = 1;
		StringBuffer sb = new StringBuffer();
		sb.append("0.0");
		while (defaultDollarDecimalSize < argDecimalSize) {
			sb.append("0");
			defaultDollarDecimalSize++;
		}
		defaultDollar = sb.toString();

		boolean hasDollarSign = false;

		if (argDollar == null || "".equals(argDollar)) {
			argDollar = defaultDollar;
		}

		if (argDollar.indexOf('$') != -1) {
			hasDollarSign = true;
			argDollar = removeLeadingChar(argDollar, "$");
		}
		try {
			float fAmount = Float.parseFloat(argDollar);

			if (fAmount == 0) {
				argDollar = defaultDollar;
			}

			int dollardecimalSize = 0;
			sb = new StringBuffer();
			sb.append(argDollar);
			// if there is decimal point
			if (argDollar.indexOf('.') > 0) {
				int dotPos = argDollar.indexOf('.');
				dollardecimalSize = argDollar.substring(dotPos + 1,
						argDollar.length()).length();

				while (dollardecimalSize < argDecimalSize) {
					sb.append("0");
					dollardecimalSize++;
				}

			} else {
				dollardecimalSize = 0;
				while (dollardecimalSize < argDecimalSize) {
					sb.append("0");
					dollardecimalSize++;
				}

			}
			argDollar = sb.toString();

		} catch (Exception e) {
			argDollar = defaultDollar;
		}

		if (hasDollarSign) {
			argDollar = "$" + argDollar;
		}
		return argDollar;

	}

	public static String formatAmountDecimal(String argAmount) {

		String newValue = null;
		if( argAmount != null ) {
			newValue =  argAmount ;
			if( newValue.indexOf( "." ) == newValue.length() - 2 )
				newValue += "0";
		}
		else 
			newValue = argAmount;
	
		return newValue;		

	}

	// only get last 6 digit

	public static String formatGroupNumberNPolicyNumber(String argValue) {
		return getLast6Digits(argValue);
	}

	/**
	 * Returns last 6 digits/characters of the input value
	 * 
	 * @param value
	 * @return
	 */
	public static String getLast6Digits(String argValue) {

		if (argValue != null) {
			int len = argValue.length();
			if (argValue.length() > 6) {
				return argValue.substring(len - 6);
			} else {
				return argValue;
			}
		}
		return "";
	}

	/**
	 * Converts dates (In String) to yyyyMMdd format, iff format is in
	 * mm/dd/yyyy.
	 * 
	 * @param dateVal
	 * @param format
	 * @return
	 */
	public static String convertDateToYYYYMMDD(String argDateVal, String argFormat) {
		StringBuffer convertedVal = new StringBuffer("");
		// if dateVal in MMDDYYYY
		if ("mm/dd/yyyy".equals(argFormat)) {
			try {
				convertedVal.append(argDateVal
						.substring(argDateVal.lastIndexOf("/") + 1));
				if (argDateVal.substring(0, argDateVal.indexOf("/")).length() == 1) {
					convertedVal.append("0").append(
							argDateVal.substring(0, argDateVal.indexOf("/")));
				} else {
					convertedVal.append(argDateVal.substring(0, argDateVal
							.indexOf("/")));
				}

				if (argDateVal.substring(argDateVal.indexOf("/") + 1,
						argDateVal.lastIndexOf("/")).length() == 1) {
					convertedVal.append("0").append(
							argDateVal.substring(argDateVal.indexOf("/") + 1, argDateVal
									.lastIndexOf("/")));
				} else {
					convertedVal
							.append(argDateVal.substring(argDateVal.indexOf("/") + 1,
									argDateVal.lastIndexOf("/")));
				}
			} catch (StringIndexOutOfBoundsException sboe) {
			}
		}
		return convertedVal.toString();

	}

	public static String formatDate(String argDateVal, String argFromFormat,
			String argToFormat) {
		StringBuffer convertedVal = new StringBuffer("");
		// if dateVal in MMDDYYYY
		if ("mm/dd/yyyy".equalsIgnoreCase(argFromFormat)
				&& "yyyyMMdd".equalsIgnoreCase(argToFormat)) {

			convertedVal
					.append(argDateVal.substring(argDateVal.lastIndexOf("/") + 1));
			if (argDateVal.substring(0, argDateVal.indexOf("/")).length() == 1) {
				convertedVal.append("0").append(
						argDateVal.substring(0, argDateVal.indexOf("/")));
			} else {
				convertedVal.append(argDateVal.substring(0, argDateVal.indexOf("/")));
			}

			if (argDateVal.substring(argDateVal.indexOf("/") + 1,
					argDateVal.lastIndexOf("/")).length() == 1) {
				convertedVal.append("0").append(
						argDateVal.substring(argDateVal.indexOf("/") + 1, argDateVal
								.lastIndexOf("/")));
			} else {
				convertedVal.append(argDateVal.substring(argDateVal.indexOf("/") + 1,
						argDateVal.lastIndexOf("/")));
			}
		} else if ("yyyyMMdd".equalsIgnoreCase(argFromFormat)
				&& "mm/dd/yyyy".equalsIgnoreCase(argToFormat)) {
			convertedVal.append(argDateVal.substring(4, 6)).append(
					argDateVal.substring(6, 8)).append(argDateVal.substring(0, 4));
		}
		return convertedVal.toString();

	}

	/**
	 * Gets Today's date in MM/dd/yyyy format
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		String currentDate = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
		currentDate = dFormat.format(cal.getTime());

		return currentDate;
	}

	public static String formatEligSearchYear(String argYear) {
		int iYear = 0;
		String sYear = "";
		try {
			iYear = Integer.parseInt(argYear);
			if (iYear < 100) {
				sYear = "1900" + iYear;
			} else {
				sYear = argYear;
			}
		} catch (Exception e) {

		}

		return sYear;
	}

	/**
	 * This method will append the leading character (created for Cosmos
	 * pcpTaxid)
	 * 
	 * @return java.lang.String
	 * @param string
	 *            java.lang.String
	 * @param totalStringLength
	 *            int
	 */
	public static String buffWithLeadingCharacter(String argString, char argChar,
			int argTotalStringLength) {

		int stringLength = 0;

		if (argString == null) {
			stringLength = 0;
		} else {
			stringLength = argString.length();
		}

		if (stringLength < argTotalStringLength) {
			// Buff with leading zeroes
			StringBuffer sNewStr = new StringBuffer();

			for (int cnt = 1; cnt <= argTotalStringLength - stringLength; cnt++) {
				sNewStr.append(argChar);
			}

			if (argString != null) {
				argString = sNewStr.toString() + argString;
			} else {
				argString = sNewStr.toString();
			}

		}

		return argString;

	}

	/**
	 * This method will append the leading zeroes (created for Cosmos pcpTaxid)
	 * Created by Seema Kaushal Creation date: (7/13/2001 11:58:26 AM)
	 * 
	 * @return java.lang.String
	 * @param string
	 *            java.lang.String
	 * @param totalStringLength
	 *            int
	 */
	public static String buffWithLeadingZeroes(String argString,
			int argTotalStringLength) {

		return buffWithLeadingCharacter(argString, '0', argTotalStringLength);

	}

	/**
	 * Insert the method's description here. Creation date: (8/29/2002 1:44:21
	 * PM)
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	protected static boolean contains(String argString, int argType) {

		if (argString == null || argString.trim().length() <= 0) {
			return false;
		}

		char[] charArray = argString.toCharArray();
		int size = charArray.length;
		Character character = new Character(' ');

		for (int i = 0; i < size; i++) {
			int charType = Character.getType(charArray[i]);

			if (argType == charType) {
				return true;
			}

		}

		return false;

	}

	/**
	 * This method checks to see if the string variable contains a letter.
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	public static boolean containsLetter(String argString) {

		return contains(argString, Character.UPPERCASE_LETTER)
				|| contains(argString, Character.LOWERCASE_LETTER);

	}

	/**
	 * This method checks to see if the string variable contains a lower case
	 * letter.
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	public static boolean containsLowerCaseLetter(String argString) {
		return contains(argString, Character.LOWERCASE_LETTER);
	}

	/**
	 * This method checks to see if the string variable contains a number.
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	public static boolean containsNumber(String argString) {
		return contains(argString, Character.DECIMAL_DIGIT_NUMBER);
	}

	/**
	 * This method checks to see if the string variable contains a special
	 * character.
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	public static boolean containsSpecialChar(String argString) {

		if (argString == null) {
			return false;
		}

		char[] charArray = argString.toCharArray();
		int size = charArray.length;
		Character character = new Character(' ');

		for (int i = 0; i < size; i++) {
			int charType = Character.getType(charArray[i]);

			if (!(charType == Character.LOWERCASE_LETTER
					|| charType == Character.UPPERCASE_LETTER 
					|| charType == Character.DECIMAL_DIGIT_NUMBER)) {
				return true;
			}

		}

		return false;

	}

	/**
	 * This method checks to see if the string variable contains an upper case
	 * letter.
	 * 
	 * @return boolean
	 * @param string
	 *            java.lang.String
	 */
	public static boolean containsUpperCaseLetter(String argString) {
		return contains(argString, Character.UPPERCASE_LETTER);
	}

	public static boolean containsOnlyThisChar(String argString, char argChar) {

		if (argString != null) {
			int length = argString.length();

			for (int i = 0; i < length; i++) {

				if (argString.charAt(i) != argChar) {
					return false;
				}
			}

			return true;
		}

		return false;

	}

	/*
	 * Expects the Date String in mm/dd/yyyy format
	 */
	public static String formatDateString(String argDate) {

		StringBuffer response = new StringBuffer(10);
		if (argDate != null && argDate.length() == 10) {
			response.append(argDate.substring(6)).append(argDate.substring(0, 2))
					.append(argDate.substring(3, 5));
			return response.toString();

		}

		return argDate;

	}

	public static String formatDateStringSwipe(String argDate) {

		StringBuffer response = new StringBuffer(10);
		if (argDate != null && argDate.length() == 8) {
			response.append(argDate.substring(4, 6)).append("/").append(
					argDate.substring(6, 8)).append("/").append(
							argDate.substring(0, 4));
			return response.toString();

		}

		return argDate;

	}

	public static final boolean isZeros(String argInput) {

		if (isNull(argInput)) {
			return false;
		}

		boolean flag = true;
		for (int i = 0; i < argInput.length(); i++) {

			if (argInput.charAt(i) != '0') {
				flag = false;
				break;
			}
		}
		return flag;
	}
	public static final boolean isAllZerosORNull(String argInput) {

		boolean flag = true;
		if (!isNull(argInput)) {
			for (int i = 0; i < argInput.length(); i++) {

				if (argInput.charAt(i) != '0') {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	public static final String getMonthStartDate() {
		String monthStartDate = "";
		Calendar calendar = Calendar.getInstance();

		int date = calendar.getMinimum(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		Date startDate = new Date(year - 1900, month, date);
		SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
		monthStartDate = dFormat.format(startDate);
		return monthStartDate;
	}

	public static final String getMonthEndDate() {
		String monthEndDate = "";
		Calendar calendar = Calendar.getInstance();
		int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		Date startDate = new Date(year - 1900, month, date);
		SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
		monthEndDate = dFormat.format(startDate);
		return monthEndDate;
	}

	/**
	 * Input = mm/dd/yyyy date Output = yyyy-mm-dd
	 * 
	 */
	public static final String getFormattedDate(String argDate) {

		String formattedDate = null;

		if (argDate != null && argDate.length() == 10) {
			formattedDate = argDate.substring(6) + "-" + argDate.substring(0, 2)
					+ "-" + argDate.substring(3, 5);

		}

		return formattedDate;
	}

	public static String formatString(String argStr) {

		return (argStr != null) ? argStr.trim() : "";
	}

	public static String formatStringWithHTMLSpace(String argStr) {

		return (argStr != null && argStr.trim().length() > 0) ? argStr.trim() : "&nbsp;";
	}

	public static String formatStringToUppercase(String argStr) {

		return (argStr != null) ? argStr.trim().toUpperCase() : "";
	}

	/**
	 * 
	 * @param valueforTrim
	 * @param c
	 * @return
	 */
	public static String trimCharacter(String argValueForTrim, char argChar) {
		if (argValueForTrim != null && !"".equals(argValueForTrim)) {

			int len = argValueForTrim.length();
			int count = argValueForTrim.length();
			char value[] = argValueForTrim.toCharArray();
			int st = 0;
			int off = 0;
			char[] val = value;

			while ((st < len) && (val[off + st] <= argChar)) {
				st++;
			}
			while ((st < len) && (val[off + len - 1] <= argChar)) {
				len--;
			}
			return ((st > 0) || (len < count)) ? argValueForTrim
					.substring(st, len) : argValueForTrim;
		}
		return argValueForTrim;
	}

	/**
	 * @param valueforTrim
	 * @param c
	 * @return
	 */
	public static String trimFrontCharacter(String argValueForTrim, char argChar) {
		if (argValueForTrim != null && !"".equals(argValueForTrim)) {

			int len = argValueForTrim.length();
			int count = argValueForTrim.length();
			char value[] = argValueForTrim.toCharArray();
			int st = 0;
			int off = 0;
			char[] val = value;

			while ((st < len) && (val[off + st] <= argChar)) {
				st++;
			}
			return ((st > 0) || (len < count)) ? argValueForTrim
					.substring(st, len) : argValueForTrim;
		}
		return argValueForTrim;
	}

	/**
	 * Returns last number of(limit) characters
	 * 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static String getLastCharacters(String argValue, int argLimit) {
		if (argValue != null) {
			int len = argValue.length();
			if (argValue.length() > argLimit) {
				return argValue.substring(len - argLimit);
			} else {
				return argValue;
			}
		}
		return argValue;

	}

	public static String padZeroesInFront(String argValue, int argHowMany) {
		for (int i = 0; i < argHowMany; i++) {
			argValue = '0' + argValue;
		}
		return argValue;
	}

	/**
	 * Added for SPRF 11181 This method is used to format the String Amount 
	 * with Commas
	 * 
	 * @param paymentAmount
	 *            String return sAmt String
	 */
	public static String formatAmount(String argPaymentAmount) {
		String sAmt = argPaymentAmount;

		if (sAmt.startsWith("$")) {
			sAmt = sAmt.substring(1, sAmt.length());
		}

		try {
			if (sAmt.startsWith("$")) {
				sAmt = sAmt.substring(1, sAmt.length());
			}

			double dVal = Double.parseDouble(sAmt);
			NumberFormat formatter = new DecimalFormat("##,##,###.###");
			String sfmtVal = formatter.format(dVal);
			int i = sfmtVal.indexOf(".");
			if (i == -1) {
				sfmtVal += ".00";
			} else {
				if (sfmtVal.substring(i + 1).length() == 1) {
					sfmtVal = sfmtVal + "0";
				}
			}
			
			return sfmtVal;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * Receives date in any format and returns Calendar Object
	 * 
	 * @param String
	 * @return String
	 */
	public static Calendar stringToDate(String argDate, String argFormat) {
		if (argDate == null || argFormat == null || argDate.trim().length() == 0
				|| argFormat.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(argFormat);
			Date dt = formatter.parse(argDate.trim());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal;
		} catch (Exception exp) {
		}
		return null;
	}

	public static String formatMemberId(String argStr) {
		if (argStr != null && argStr.length() > 0) {
			if (argStr.length() > 9) {
				if (argStr.length() == 10) {
					argStr = argStr.substring(1);
				}
				if (argStr.length() == 11) {
					argStr = argStr.substring(2);
				}

			}
		}
		return argStr;
	}
    /**
	* This method validates the year, month and day of the passed in date.
	* The date can be in one of the following formats (designated by the date
	* mask):
	* MM/DD/YY
	* MM/DD/YYYY
	* YY/MM/DD
	* YYYY/MM/DD
	* MMDDYY
	* MMDDYYYY
	* YYMMDD
	* YYYYMMDD
	*
	* For date masks with slashes: Dashes can be substituted for slashes in the
	* date field
	* One byte months and/or days are acceptable
	*/

	public static boolean isValidDate(String argDateField, String argDateMask) {
		int iMonth = 0, iDay = 0, iYear = 0;

		String sMonth = null, sDay = null, sYear = null;

		boolean bValidDateMask = false, bLeapYear = false;

		String sErrorMsg = "";

		if (argDateField == null) {
			sErrorMsg = "Date Field cannot be null";
			return false;
		}

		argDateField = argDateField.trim();
		argDateMask = argDateMask.trim();

		if (argDateField.length() < 1) {
			sErrorMsg = "Date Field cannot be blank";
			return false;
		}

		if ((argDateMask == null) | (argDateMask.length() < 1)) {
			sErrorMsg = "Date Mask cannot be blank";
			return false;
		}

		argDateMask = argDateMask.replace('-', '/').toUpperCase();
		argDateField = argDateField.replace('-', '/');

		if ((argDateMask.equals("MM/DD/YY")) || (argDateMask.equals("MM/DD/YYYY"))) {
			sMonth = argDateField.substring(0, argDateField.indexOf("/"));
			sDay = argDateField.substring(argDateField.indexOf("/") + 1, argDateField
					.lastIndexOf("/"));
			sYear = argDateField.substring(argDateField.lastIndexOf("/") + 1);
			bValidDateMask = true;
		}

		if ((argDateMask.equals("YY/MM/DD")) || (argDateMask.equals("YYYY/MM/DD"))) {
			sYear = argDateField.substring(0, argDateField.indexOf("/"));
			sMonth = argDateField.substring(argDateField.indexOf("/") + 1, argDateField
					.lastIndexOf("/"));
			sDay = argDateField.substring(argDateField.lastIndexOf("/") + 1);
			bValidDateMask = true;
		}

		if (argDateMask.equals("MMDDYY")) {
			if (argDateField.length() == 6) {
				sMonth = argDateField.substring(0, 2);
				sDay = argDateField.substring(2, 4);
				sYear = argDateField.substring(4);
				bValidDateMask = true;
			} else {
				sErrorMsg = "Invalid Date";
				return false;
			}
		}

		if (argDateMask.equals("MMDDYYYY")) {
			if (argDateField.length() == 8) {
				sMonth = argDateField.substring(0, 2);
				sDay = argDateField.substring(2, 4);
				sYear = argDateField.substring(4);
				bValidDateMask = true;
			} else {
				sErrorMsg = "Invalid Date";
				return false;
			}
		}

		if (argDateMask.equals("YYMMDD")) {
			if (argDateField.length() == 6) {
				sYear = argDateField.substring(0, 2);
				sMonth = argDateField.substring(2, 4);
				sDay = argDateField.substring(4);
				bValidDateMask = true;
			} else {
				sErrorMsg = "Invalid Date";
				return false;
			}
		}

		if (argDateMask.equals("YYYYMMDD")) {
			if (argDateField.length() == 8) {
				sYear = argDateField.substring(0, 4);
				sMonth = argDateField.substring(4, 6);
				sDay = argDateField.substring(6);
				bValidDateMask = true;
			} else {
				sErrorMsg = "Invalid Date";
				return false;
			}
		}

		if (!bValidDateMask) {
			sErrorMsg = "Invalid Date Mask";
			return false;
		}

		if (!(isNumeric(sMonth))) {
			sErrorMsg = "Invalid Month: Month is not numeric";
			return false;
		}

		if (!(isNumeric(sDay))) {
			sErrorMsg = "Invalid Day: Day is not numeric";
			return false;
		}

		if (!(isNumeric(sYear))) {
			sErrorMsg = "Invalid Year: Year is not numeric";
			return false;
		}

		iMonth = Integer.parseInt(sMonth);
		iDay = Integer.parseInt(sDay);
		iYear = Integer.parseInt(sYear);

		if (iMonth < 1 || iMonth > 12) {
			sErrorMsg = " Date Month must be 01 through 12";
			return false;
		}
		if (iDay < 1) {
			sErrorMsg = " Date Day must be greater than 00";
			return false;
		}
		if (sYear!= null && sYear.length() > 2) {
			if (iYear < 1900 || iYear > 2100) {
				sErrorMsg = " Date Year must be 1900 through 2100";
				return false;
			}
		}

		switch (iMonth) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (iDay > 31) {
				sErrorMsg = " Date Day must be 01 through 31 for month " +
								"supplied";
				return false;
			}
			break;
		case 2: // Determine if year is a Leap Year
			// If year is divisible by 4, it is a leap year UNLESS the year is
			// divisible by 100. Then, if it is also divisible by 400, it is a
			// leap year.
			// Otherwise, not.

			if ((iYear % 4 == 0) && ((iYear % 100 != 0) || (iYear % 400 == 0))) {
				bLeapYear = true;
			} else {
				bLeapYear = false;
			}

			if (bLeapYear) {
				if (iDay > 29) {
					sErrorMsg = " Date Day must be 01 through 29 for " +
										"February Leap Year";
									
					return false;
				}
			} else {
				if (iDay > 28) {
					sErrorMsg = " Date Day must be 01 through 28 for " +
										"February non-Leap Year";
									
					return false;
				}
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (iDay > 30) {
				sErrorMsg = " Date Day must be 01 through 30 for " +
								"month supplied";
				return false;
			}
			break;
		}

		return true;
	}

	
	/**
	 * Returns true when amount is zero.
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean isAmountZero(float argAmount) {
		boolean isAmountZero = false;
		if ((int) Math.ceil(argAmount) == 0) {
			isAmountZero = true;
		}
		return isAmountZero;
	}

	/**
	 * Returns defaultDollar when amount is null.
	 * 
	 * @param amount
	 * @return
	 */
	public static String validateAmount(String argDollar) {
		String defaultDollar = "$0.00";

		if (isNull(argDollar)) {
			argDollar = defaultDollar;
		}

		return argDollar;

	}

	/*
	 * input yyyy-mm-dd output mm/dd/yyyy
	 */
	public static String eobFormattedDate(String argDate) {
		StringBuffer returnDate = new StringBuffer();
		if (!isNull(argDate) && argDate.length() == 10) {
			returnDate.append(argDate.substring(5, 7)).append("/").append(
					argDate.substring(8)).append("/").append(
							argDate.substring(0, 4));
		}
		return returnDate.toString();
	}

	
	/*
	 * converts the first character in the String to uppercase and 
	 * remaining characters to lowercase  
	 */
	public static String convertString(String argStringValue) {
		StringBuffer result = new StringBuffer();
		StringTokenizer tokens = new StringTokenizer(argStringValue," ");
			String tokenvalue = null;
			while (tokens.hasMoreTokens()) {
				tokenvalue = tokens.nextToken();
				result = result.append(tokenvalue.substring(0, 1).toUpperCase())
					.append(tokenvalue.substring(1).toLowerCase()).append(" ");
			}
		return result.toString();
	}


	public static int getSize(java.util.ArrayList argField) {
               return ((AppUtils.isNull(argField))?0:argField.size());
	}

	public static int parseInt(String argStr, int argVal) {
		try {
			return Integer.parseInt(argStr);
		} catch (NumberFormatException nfe) {
			return argVal;
		}
	}
	
	public static int parseInt(String argStr) {
		return parseInt(argStr, 0);
	}
	
	/**
	 * Used to format the string amount by adding dollar sign and 
	 * amount value with 2 decomal places.
	 * @param sValue
	 * @return formatted value
	 */
	public static String currencyFormat(String argValue) {

		StringBuffer sMoney = null;
		DecimalFormat formatter = new DecimalFormat("$###,###.##");
		
		try {
			float value = Float.parseFloat(argValue);
			sMoney = new StringBuffer(formatter.format(value));
			int i = sMoney.indexOf(".");
			
			if (i == -1) {
				sMoney.append(".00");
			} else {
				
				if (sMoney.substring(i+1).length()==1) {
					sMoney.append("0");
				}
			}
			
			return sMoney.toString();
		}catch (NumberFormatException e) {
			  return argValue;
	    }
		
	}
    
	public static String formatBilledAmount(String argBilledAmt){
		
		String sMoney = null;
		String defaultAmt = "00";
		if(AppUtils.isNull(argBilledAmt)){
			argBilledAmt = defaultAmt;
		}
		DecimalFormat formatter = new DecimalFormat("###,###.##");
		try {
			float value = Float.parseFloat(argBilledAmt);
			sMoney = formatter.format(value);

			int i = sMoney.indexOf(".");
			if (i == -1) {
				sMoney = sMoney + ".00";
			} else {
				if (sMoney.substring(i+1).length()==1) {
					sMoney = sMoney + "0";
				}
			}
		}
		catch(Exception e){
		}
		return sMoney;
   }

	/**
	 * This method returns a ArrayList which holds the same objects as the input list.
	 * @param list ArrayList to be returned based on this list
	 * @return <ol>
	 * 				<li>null if input list is null</li>
	 * 				<li>same input list if input list is an ArrayList</li>
	 * 				<li>A new ArrayList which holds same objetcs as of list</li>
	 * 			</ol>
	 * @since version Fortify REl II. 
	 * @author UHG India
	 */
	public static ArrayList getArrayList(List list){
		if(list == null)
			return null;
		if(list instanceof ArrayList){
			return (ArrayList) list;
		}
		return new ArrayList(list);
	}

	public static String trim(String str){
		if(str != null)
			str = str.trim();
		if("".equals(str))
			str = null;
		return str;
	}
    
	public static String getPreviousWeekDay(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		while(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
			|| c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
				c.add(Calendar.DAY_OF_MONTH, -1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(c.getTime());

	}
	public static String getNextWeekDay(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, +1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(c.getTime());

	}
/**
	 * @param reportLinksName
	 * @return
	 */
	public static String formatFileNameForSummaryReports(String fileName) {
		StringBuffer formattedString = new StringBuffer();
		formattedString.append("Hospital Notification Monitoring Report - ");
		String hospitalName = fileName.substring(fileName.indexOf('_')+1, fileName.lastIndexOf('_'));
		formattedString.append(hospitalName.replaceAll("_"," ")+ " ");
		String yearMonth = fileName.substring(fileName.lastIndexOf('_')+1,fileName.lastIndexOf('_')+7);
		yearMonth = yearMonth.substring(0,4).concat(" ").concat(yearMonth.substring(4));
		formattedString.append(yearMonth);
		return formattedString.toString();
	}
/**
	 * This method returns a String which holds the value without comma.
	 * @param list String to be returned based on this list
	 * @return inputStringWithoutCommas;
	 * @author Accenture Offshore
	 */
	public static String removeCommasFromInput(String amountValue){
		StringTokenizer st = new StringTokenizer(amountValue, ",");
		StringBuffer sb = new StringBuffer();
		while(st.hasMoreTokens())
		  sb.append(st.nextToken());
		String inputStringWithoutCommas = sb.toString();
		
		return inputStringWithoutCommas;
	}

	public static boolean validateDate(String date, boolean fullYear) {

		if (date != null && !date.equals("") && date.indexOf("/") != -1) {
			StringTokenizer st = new StringTokenizer(date, "/");
			String month = "";
			String day = "";
			String year = "";
			int i = 0;
			int countOfSeperator = date.length() - date.replaceAll("/","").length();
			
			if(countOfSeperator > 2) {
				return false;
			}
			
			while (st.hasMoreTokens()) {
				if (i == 0) {
					month = st.nextToken();
				} else if (i == 1) {
					day = st.nextToken();
				} else if (i == 2) {
					year = st.nextToken();
				}
				i++;
			}
			return validateDate(month, day, year, fullYear);
		}
		return false;
	}

	public static boolean validateDate(String month, String day, String year,
			boolean fullYear) {

		int iYear = 0;
		int iMonth = 0;
		int iDay = 0;

		try {
			iYear = Integer.parseInt(year);
			iMonth = Integer.parseInt(month);
			iDay = Integer.parseInt(day);
		} catch (Exception e) {
			return false;
		}

		if (fullYear) {
			if (year.length() != 4) {
				return false;
			}
		}

		if (iYear < 0 || iMonth < 0 || iDay < 0) {
			return false;
		}

		if (iYear == 0 || iMonth == 0 || iDay == 0) {
			return false;
		}

		if (iMonth > 12) {
			return false;
		}

		int maxDays = 31;

		if (iMonth == 2) {
			maxDays = 28;

			if (iYear % 4 == 0) {
				maxDays = 29;
			}

		}

		if (iDay > maxDays) {
			return false;
		}

		return true;

	}
	public static String formatAmtDecimal(String argAmount) {

		String newValue = null;
		if( argAmount != null ) {
			newValue =  argAmount ;
			if( newValue.indexOf( "." ) == newValue.length() - 2 )
				newValue += "0";
		}
		else 
			newValue = "$0.00";
	
		return newValue;		

	}
	
	//This method will return amount with $ sign, comma and decimal. e.g.$10,000.00
	//for MN AUC Project.
	public static String formatAmtWithCommaDecDol(String argPaymentAmount) {
		String sAmt = argPaymentAmount;

		if (sAmt.startsWith("$")) {
			sAmt = sAmt.substring(1, sAmt.length());
		}

		try {
			if (sAmt.startsWith("$")) {
				sAmt = sAmt.substring(1, sAmt.length());
			}

			double dVal = Double.parseDouble(sAmt);
			NumberFormat formatter = new DecimalFormat("##,##,###.###");
			String sfmtVal = formatter.format(dVal);
			int i = sfmtVal.indexOf(".");
			if (i == -1) {
				sfmtVal += ".00";
			} else {
				if (sfmtVal.substring(i + 1).length() == 1) {
					sfmtVal = sfmtVal + "0";
				}
			}
            sfmtVal="$"+sfmtVal;
			return sfmtVal;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	public static String formatPercent(String argPercent) {

		String newValue = null;
		if( argPercent != null ) {
			int idx = argPercent.indexOf('.');
			newValue =  (argPercent.substring(0, idx));
			
		}
		else 
			newValue = "0.0";//it will be default an dwill be ihgnored
	
		return newValue;		

	}
	
	public static String addDashes(String input){
		int INPUT_DATE_LENGTH = 8;
		char DASH = '-';
		String output = "";

		try{
			if(input.length() != INPUT_DATE_LENGTH){
				//logger.error("addDashes: Invalid input date " + input
				 //+ ". date expected in CCYYMMDD format. No dashes were added.");
				//output = input;
			}
			else{
				//create output date by adding dashes, viz.
				// CCYY + '-' + MM + '-' + DD
				output = input.substring(0,4) + DASH
				 + input.substring(4,6) + DASH
				 + input.substring(6,8);
			}
		}
		catch(Exception e){
			//logger.error("addDashes: Exception in adding dashes to input date "
			// + input + ". No dashes were added. Error description: " + e.getMessage());
			//output = input;
		}
		if(output.equalsIgnoreCase("")){
			//logger.error("addDashes: Dashes could not be added to input date.");
			//output = input;
		}
		return output;
	}
	public static String formatDateString(String targetFormat, String date, String srcFormat){
		if (targetFormat==null || targetFormat.trim().length()==0 
				|| date==null || date.trim().length()==0
				|| srcFormat==null || srcFormat.trim().length()==0) {

			return date;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(targetFormat);
		Date dt = StringToDate(date,srcFormat);
		return sdf.format(dt);
	}
	
	
	public static Date StringToDate(String s, String format) {
		if ((s == null) || (s.trim().length() == 0)) { return null; }
		if ((format == null) || (format.trim().length() == 0)) { format = "yyyyMMdd"; }
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(s);
		} catch (java.text.ParseException ex) {
			return null;
		}
	}
	
	public static boolean isNumeric(String value) {
		char c;

		if ((value == null) || (value.length() == 0)) {
			return false;
		}
		int len = value.length();

		for (int i = 0; i < len; i++) {
			c = value.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
		/**
	 *  below method will remove leading ,trailing and spaces exist in between string
	 * @param stValue
	 * @return
	 */
	public static String removeSpaces(String stValue) {
        int scan;
        // check to see if we have a string to review
        if (  (stValue == null) || (stValue.length() == 0)  )
            return stValue;           
      
        	stValue = stValue.trim();

        for (scan=0; scan < stValue.length(); scan++) {
            char cDigit = stValue.charAt(scan);
            switch (cDigit) {
                
                case 0x20:
                   // no doublspace
                    if (scan+1 < stValue.length())
                        if (stValue.charAt(scan + 1) == 0x20) {
                        	stValue =stValue.substring(0,scan+1)+(stValue.substring(scan+2,stValue.length()).trim());                        
                        	
                        }
                default:
                    break;
            }
        }
        return stValue;
    }
}
