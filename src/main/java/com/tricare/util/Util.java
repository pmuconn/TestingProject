/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <2012>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
 */
package com.tricare.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * This is a utility tool for common functionality.
 * @since Q1 2013 - TriCare West
 */
public final class Util {

	/**
	 * Determine if all of the Objects are null
	 * @param Object... - the array of Objects to check
	 * @return boolean - true if all Objects are null, false otherwise
	 */
	public static boolean areAllNull(Object... inObjectArray) {
		boolean returnValue = true;

		for (Object object: inObjectArray) {
			returnValue &= (object == null);
		}

		return returnValue;
	}
	
	/**
	 * Determine if any of the Objects are null
	 * @param Object... - the array of Objects to check
	 * @return boolean - true if at least one is null, false otherwise
	 */
	public static boolean areAnyNull(Object... inObjectArray) {
		boolean returnValue = false;

		for (Object object: inObjectArray) {
			if (object == null) {
				returnValue = true;
				break;
			}
		}

		return returnValue;
	}
	
	/**
	 * Substitutes an Asterisk (*) for each character of the supplied String.
	 * @param String - will be masked by asterisks.
	 * @return String - asterisks filled.
	 */
    public static String asteriskSubstitute(String inStringToMask) {
        char[] asterisk = new char[inStringToMask.length()];
        
        Arrays.fill(asterisk, '*');
        
        return new String(asterisk);                
    }

	/**
	 * Determine if all the supplied booleans are true.
	 * @param boolean - the array of booleans to check
	 * @return boolean - true if all are true, false otherwise
	 */
	public static boolean booleanAnd(boolean... inBooleanArray) {
		boolean returnValue = true;

		for (boolean bool: inBooleanArray) {
			if (!bool) {
				returnValue = false;
				break;
			}
		}

		return returnValue;
	}


	/**
	 * Determine if one of the supplied booleans is true.
	 * @param boolean - the array of booleans to check
	 * @return boolean - true if at least one is true, false otherwise
	 */
	public static boolean booleanOr(boolean... inBooleanArray) {
		boolean returnValue = false;

		for (boolean bool: inBooleanArray) {
			if (bool) {
				returnValue = true;
				break;
			}
		}

		return returnValue;
	}

	/**
	 * Retrieve the boolean representation of an indicator string.
	 * This method is null-safe and case-insensitive.
	 * @param String - the indicator string to check
	 * @return boolean - true if "Y", "YES", "TRUE", or "1", false otherwise
	 */
	public static boolean booleanValue(String inIndicator) {
		boolean returnValue = false;

		if (!isNullOrEmpty(inIndicator)) {
			inIndicator.trim().toLowerCase();
			returnValue |= inIndicator.equals(Constant.BOOLEAN_TRUE);
			returnValue |= inIndicator.equals(Constant.YES_INDICATOR);
			returnValue |= inIndicator.equals(Constant.YES);
			returnValue |= inIndicator.equals(Constant.ONE);
		}

		return returnValue;
	}

	/**
	 * Capitalize each word in the given string
	 * @param String - the string to capitalize words in
	 * @return String - the string with words capitalized
	 */
	public static String capitalizeWords(String inStringToCapitalize) {
		String returnValue = Constant.EMPTY_STRING;
		StringBuffer buffer;
		StringTokenizer tokenizer;
		String currentToken = Constant.EMPTY_STRING;

		buffer = new StringBuffer();
		tokenizer = new StringTokenizer(inStringToCapitalize, Constant.WHITE_SPACE);

		while (tokenizer.hasMoreTokens()) {
			currentToken = tokenizer.nextToken();
			buffer.append(currentToken.substring(0, 1).toUpperCase());
			buffer.append(currentToken.substring(1));
			buffer.append(Constant.WHITE_SPACE);
		}

		returnValue = buffer.toString().trim();

		return returnValue;
	}

	/**
	 * @param input
	 * @param numChars
	 * @return true if numChars in a row was found in the string, false otherwise
	 */
	public static boolean checkCharsInRow(String input, int numChars){
		char[] chars = input.toCharArray();
		boolean returnValue = false;
		for(int i = 0; i < chars.length-numChars+1; i++ ){			
			for(int j=i+1; j<i+numChars; j++){
				if(chars[i] != chars[j]){
					returnValue = true;
					break;
				}
			}			
		}
		return returnValue;
	}

	/**
	 * Checks to see if String1 and String2 share any inCharCount number of characters
	 * @param inString1
	 * @param inString2
	 * @param inCharCount
	 * @return true if they do share inCharCount number of characters, false otherwise
	 */
	public static boolean checkSequenceShared(String inString1, String inString2, int inCharCount) {
		boolean returnValue = false;		
		StringBuffer firstString = new StringBuffer(inString1);
		while (firstString.length() >= inCharCount) {
			if (inString2.contains(firstString.substring(0, inCharCount))) {
				returnValue = true;
				break;
			}
			firstString.deleteCharAt(0);
		}		
		return returnValue;
	}

	/**
	 * Check if a string contains special characters excluding wildcard.
	 * @param String
	 * @return boolean
	 */
	public static boolean containsSpecialCharacters(String inInput) {
		boolean returnValue = false;
		//MQC defect 3466  PM10110499/IM14413173
		Pattern p = Pattern.compile("[^a-z0-9 '-]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(inInput);
		returnValue = m.find();
		return returnValue;
	}	

	/**
	 * Check whether the given String contains any whitespace characters.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not empty and
	 * contains at least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(String inStr) {
		boolean returnValue = false;
		
		if (!hasLength(inStr)) {
			returnValue = false;
		}
		int strLen = inStr.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(inStr.charAt(i))) {
				returnValue= true;
			}
		}
		
		return returnValue;
	}

	/**
	 * Check if a string contains special characters excluding wild card.
	 * @param String
	 * @return boolean
	 */
	public static boolean containsWildcard(String inInput) {
		boolean returnValue = false;
		Pattern p = Pattern.compile("[*]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(inInput);
		returnValue = m.find();
		return returnValue;
	}

	/**
	 * Converts a big decimal to a $100.00 dollar format
	 * @param BigDecimal
	 * @return String
	 */
	public static String convertBigDecimalToDollar(BigDecimal inBigDecimal) {
		String returnValue = Constant.EMPTY_STRING;

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		returnValue = formatter.format(inBigDecimal);

		return returnValue;
	}

	/**
	 * Converts a date string in MM/DD/YYYY format to a date object
	 * @param inDateString
	 * @return Date object
	 */
	public static Date convertStringToDate(String inDateString){		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date returnValue = new Date();
		try {
			returnValue = df.parse(inDateString);
		} catch (ParseException e) {			
			//e.printStackTrace();
		}
		return returnValue;
	    
	}		

	/**to convert a string to double
	 * @param doubleVal
	 * @return
	 */
	public static double convertStringToDouble(String doubleVal) {
		double d = 0;
		try {
			d = Double.valueOf(doubleVal.trim()).doubleValue();
		} catch (Exception ex) {
			d = 0;
		}
		return d;
	}

	/**
	 * Convert String to integer
	 * @param intVal
	 * @return
	 */
	public static int convertStringToInteger(String intVal) {
		int i = 0;
		try {
			i = Integer.valueOf(intVal.trim()).intValue();
		} catch (Exception ex) {
			i = 0;
		}
		return i;
	}

	/**to convert a string to long
	 * @param longValStr
	 * @return
	 */
	public static long convertStringToLong(String longValStr) {
		long longVal = 0;
		try {
			longVal =Long.parseLong(longValStr);
		} catch (Exception ex) {
			longVal = 0;
		}
		return longVal;
	}

	/**
	 * Converts a date string in MM/DD/YYYY format to ISO date format yyyy-MM-dd
	 * @param inDateString
	 * @return String date in format yyyy-MM-dd
	 */
	public static String convertToUTCDate(String inDateString){	
		
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd");
		String returnValue="";
		Date dt = null ;
		try{
			dt = convertStringToDate(inDateString);
			returnValue = utcFormat.format(dt);
		}catch(Exception e){
			//Eat the exception
		}
		
		return returnValue;
	    
	}

	/*
	 * Converts a Date String in format  UTC date yyyy-MM-dd to MM/dd/yyyy
	 * @param inDateString date as string in format MM/dd/yyyy
	 * @return String date in format yyyy-MM-dd
	 */
	public static String convertUTCtoDate(String inDateString){	
		
		SimpleDateFormat outFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd");
		String returnValue="";
		Date dt = null ;
		try{
			dt = utcFormat.parse(inDateString);
			returnValue = outFormat.format(dt);
			
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		return returnValue;
	    
	}

	/**
	 * Converts a date string in yyyy-MM-dd format to MM/dd/yyyy
	 * @param inDateString
	 * @return Date object
	 */
	public static String convertyyyyMMddToMMddyyyy(String inDateString){		
		Date unchanged;
		String returnValue = inDateString;
		try {
			unchanged = new SimpleDateFormat("yyyy-MM-dd").parse(inDateString);
			returnValue = new SimpleDateFormat("MM/dd/yyyy").format(unchanged);
		} catch (ParseException e) {		
			LoggerUtil.debug("can't parse " + "to yyyy-MM-dd dateobject");
			//e.printStackTrace();
		}		
		return returnValue;
	    
	}

	/**
	 * Determine if two objects are equal (null-safe)
	 * @param Object - object 1
	 * @param Object - object 2
	 * @return boolean - true if object 1 and object 2 are equal, false otherwise
	 */
	public static boolean equals(Object inObjectOne, Object inObjectTwo) {
		boolean returnValue = false;

		if (inObjectOne == null ^ inObjectTwo == null) {
			returnValue = false;
		} else if (inObjectOne == null && inObjectTwo == null) {
			returnValue = true;
		} else {
			returnValue = inObjectOne.equals(inObjectTwo);
		}

		return returnValue;
	}

	/**
	 * Format the given string to the phone format
	 * (XXX) XXX-XXXX.  Formatting is only considered
	 * for strings 7 or 10 characters in length.  When length
	 * is greater than 10 characters, return the given string as is.
	 * When length is less than 7 characters, return empty string.
	 *  
	 * @param inPhoneNumber - assumed phone # string to be formatted
	 * @return String - formatted string based on pattern
	 */
	public static String formatPhoneNumber(String inPhoneNumber)
	{			
		String areaCode = Constant.EMPTY_STRING;
		String prefix = Constant.EMPTY_STRING;
		String suffix = Constant.EMPTY_STRING;
		 
		// Scrub the given string - using the default expression [^A-Za-z0-9]
		String sanitizedPhoneNumber = Util.sanitize(inPhoneNumber, null);
		
		int phoneNumberLength = sanitizedPhoneNumber.length();
		if (phoneNumberLength == 10) {
			areaCode = sanitizedPhoneNumber.substring(0, 3);
			prefix = sanitizedPhoneNumber.substring(3, 6);
			suffix = sanitizedPhoneNumber.substring(6, 10);
		} else if (phoneNumberLength == 7) {
			prefix = sanitizedPhoneNumber.substring(0, 3);
			suffix = sanitizedPhoneNumber.substring(3, 7);
		} else if (phoneNumberLength > 10) {
			String message = "Phone number provided will not be formatted, returning [" + inPhoneNumber + "]";
			LoggerUtil.warn(message);
			return inPhoneNumber;			
		} else {			
			String message = "Phone number provided is an invalid length [" + phoneNumberLength + "]";			
			LoggerUtil.warn(message);
			return Constant.EMPTY_STRING;
		}
		
		StringBuffer buffer = new StringBuffer();					
		if (!areaCode.isEmpty()) {
			buffer.append("(").append(areaCode).append(")");
			buffer.append(Constant.WHITE_SPACE);
		}
		buffer.append(prefix);
		buffer.append(Constant.HYPHEN);
		buffer.append(suffix);
				
		return buffer.toString();
	}

	/**
	 * Formats the input value to currency pattern
	 * @param double - amount to be formatted
	 * @return String - the formatted dollar value
	 */
	public static String getFormattedDollarValue(double inDollarAmount) {
		String returnValue = Constant.EMPTY_STRING;
		NumberFormat format;

		format = NumberFormat.getCurrencyInstance();
		returnValue = format.format(inDollarAmount);

		return returnValue;
	}

	/**
	 * Get a partially masked social security number (***-**-####)
	 * @param String - the social security number
	 * @return String - the masked social security number
	 */
	public static String getFormattedSsn(String inSsn) {
		String returnValue;

		if (!Util.isNullOrEmpty(inSsn)) {
			returnValue = "***-**-" + inSsn.substring(5, 9);
		} else {
			returnValue = Constant.EMPTY_STRING;
		}

		return returnValue;
	}
	
	/**
	 * Get a partially masked social security number (*****####) that doesn't include dashes
	 * @param String - the social security number
	 * @return String - the masked social security number
	 */
	public static String getFormattedSsnNoDash(String inSsn) {
		String returnValue;

		if (!Util.isNullOrEmpty(inSsn)) {
			returnValue = "*****" + inSsn.substring(5, 9);
		} else {
			returnValue = Constant.EMPTY_STRING;
		}

		return returnValue;
	}

	/**
	 * Converts string to a integer value else returns 0
	 * @param String
	 * @return int - numeric value
	 */
	public static int getIntValue(String inString) {
		int retVal = 0;

		try {
			Integer.parseInt(inString);
		} catch (NumberFormatException e) {
			// do nothing return 0
		}

		return retVal;
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param String - check to determine if it has any length - (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(String inStr) {
		return (inStr != null && inStr.length() > 0);
	}

	/**
	 * Determine if a string is null or if it is empty - spaces are trimmed.
	 * @param String - the string to check
	 * @return boolean - true if null or empty, false otherwise
	 */
	public static boolean isNullOrEmpty(String inString) {
		boolean returnValue = false;

		if (inString == null) {
			returnValue = true;
		} else {
			returnValue = inString.trim().isEmpty();
		}

		return returnValue;
	}

	/**
	 * Determines if the supplied String is ALL numbers.
	 * @param String
	 * @return boolean - when true the supplied String contains all Numeric characters.
	 */
	public static boolean isNumeric(String inString) {
		boolean returnValue;
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);

		if (inString.isEmpty()) {
			// String is EMPTY.
			returnValue = false;
		} else {
			// By parsing the string and having the parser position set to end of the 
			// string, the entire string is assumed to be numeric.
			formatter.parse(inString, pos);   
			returnValue = inString.length() == pos.getIndex();
		}

		return returnValue;
	}

	
	public static boolean isString(Object o) {
	    return o != null && o instanceof String && ((String)o).trim().length() > 0;
	  }

	/**
	 * Checks if a string is a valid email address
	 * @param inEmail
	 * @return boolean
	 */
	public static boolean isValidEmailAddress(String inEmail) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(inEmail);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	/**
	 * 9-20 characters.
		Must contain at least one uppercase letter.
		Must contain at least one lowercase letter.
		Must contain at least one number.
		Must contain at least one special character.
		Cannot contain your username.
		TODO Cannot contain more than three sequential characters from the previous password. 
		Cannot contain more than two identical characters in a row within the same password.
		HANDLED BY LDAP: Cannot be one of the previous 10 passwords.
	 * 
	 * 
	 * @param 
	 * @return boolean whether or not password is valid
	 */
	public static boolean isValidNISTPassword(String inPassword, String inUserName) {
		boolean returnValue = true;
		if (Util.hasLength(inPassword)) {
			if (!Pattern.compile("[0-9]").matcher(inPassword).find()) {
				returnValue = false;
			} else if (!Pattern.compile("[a-z]").matcher(inPassword).find()) {
				returnValue = false;
			} else if (!Pattern.compile("[A-Z]").matcher(inPassword).find()) {
				returnValue = false;
			} else if (!Util.containsSpecialCharacters(inPassword)) {
				returnValue = false;
			} else if (inPassword.length() < 9 || inPassword.length() > 20) {
				returnValue = false;
			//} else if (Util.checkCharsInRow(inPassword, 3)) { 
			//	returnValue = false;
			} else if (inPassword.contains(inUserName)) {
				returnValue = false;
			}
		}
		return returnValue;
	}

	/**
	 * Mask the supplied TIN with Asterisks if its length is greater than 4.
	 * @param String - TIN to mask
	 * @return String - Masked TIN value.
	 */
	public static String maskTin(String inTin) {
		String returnValue;
		
		if (isString(inTin) && 4 <= inTin.length()) {
	      returnValue = String.format("*****%s", inTin.substring(inTin.length()-4));
	    } else {
	      returnValue = "N/A";
	    }
		
	    return returnValue;
	}
	
	/**
	 * Perform a null-substitute on a string
	 * @param String - the string to check if null or empty
	 * @param String - the string to substitute if <code>inStringToCheck</code> is null or empty
	 * @return String - <code>inStringToSubstitute</code> if <code>inStringToCheck</code> is null or empty
	 */
	public static String nullSubstitute(String inStringToCheck, String inStringToSubstitute) {
		String returnValue;

		if (isNullOrEmpty(inStringToCheck)) {
			returnValue = inStringToSubstitute;
		} else {
			returnValue = inStringToCheck;
		}

		return returnValue;
	}
	
	/**
	 * Convert the supplied Object into a string for logging purposes.
	 * @param Object - the Object to convert to a string
	 * @return String - the string representation of the Object
	 */
	public static String objectToString(Object inObject) {
		String returnValue = Constant.EMPTY_STRING;

		StringBuffer buffer = new StringBuffer();
		buffer.append(inObject.getClass().getSimpleName()).append(" {");

		Class<?> c = inObject.getClass();

		int fieldCount = 0;
		while (c != null) {
			for (Field field: c.getDeclaredFields()) {
				field.setAccessible(true);
				int modifiers = field.getModifiers();
				if ((Modifier.isPrivate(modifiers) 
						|| Modifier.isProtected(modifiers))
						&& !Modifier.isFinal(modifiers)
						&& !Modifier.isStatic(modifiers)
						&& !Modifier.isTransient(modifiers)
						&& !Modifier.isInterface(modifiers)) {
					if (fieldCount > 0) {
						buffer.append(", ");
					}
					buffer.append(field.getName()).append(": ");
					try {
						Object value = field.get(inObject);
						if (value != null) {
							String valueString = value.toString();
							if (value instanceof String || value instanceof Date) {
								buffer.append(Constant.DOUBLE_QUOTE);
								buffer.append(valueString);
								buffer.append(Constant.DOUBLE_QUOTE);
							} else {
								buffer.append(valueString);
							}
						}
					} catch (IllegalArgumentException e) {
						LoggerUtil.error("Illegal argument exception: " + e);
					} catch (IllegalAccessException e) {
						LoggerUtil.error("Illegal access exception: " + e);
					}			
					finally {
						field.setAccessible(false);
					}
					fieldCount++;
				}
			}
			c = c.getSuperclass();
		}

		buffer.append("}");
		returnValue = buffer.toString();

		return returnValue;
	}
	
	/**
	 * Prepend zeros to a number to achieve a desired length
	 * @see #prependZeros(String, int)
	 * @param int - the number to prepend to
	 * @param String - the desired length of the number string
	 * @return String - the number with prepended zeros
	 */
	public static String prependZeros(int inNumber, int inLength) {
		return prependZeros(String.valueOf(inNumber), inLength);
	}
	
	/**
	 * Prepend zeros to a number string to achieve a desired length
	 * @param String - the number to prepend to
	 * @param String - the desired length of the number string
	 * @return String - the number with prepended zeros
	 */
	public static String prependZeros(String inNumber, int inLength) {
		String returnValue = Constant.EMPTY_STRING;

		if (isNullOrEmpty(inNumber)) {
			for (int i=0; i<inLength; i++) {
				returnValue += Constant.ZERO;
			}
		} else if (inNumber.length() >= inLength) {
			returnValue = inNumber;
		} else {
			for (int i=0, j=(inLength - inNumber.length()); i<j; i++) {
				returnValue += Constant.ZERO;
			}
			returnValue += inNumber;
		}

		return returnValue;
	}
	
	/**
	 * Sanitize a string of a regular expression.  The default 
	 * expression used is "[^A-Za-z0-9]", if {@code inExpression} is null.
	 * @param String - the string to sanitize of the expression
	 * @param String - the expression to match that will be removed
	 * @return String - the string with the expression removed
	 */
	public static String sanitize(String inStringToSanitize, String inExpression) {
		String returnValue = Constant.EMPTY_STRING;
		String defaultExpression = "[^A-Za-z0-9]";
		String derivedExpression;

		if (!isNullOrEmpty(inStringToSanitize)) {
			derivedExpression = isNullOrEmpty(inExpression) ? defaultExpression : inExpression;
			returnValue = inStringToSanitize.replaceAll(derivedExpression, Constant.EMPTY_STRING);
		}

		return returnValue;
	}
	
	/**
	 * Retrieve the String representation of a boolean value.
	 * @param boolean - the boolean value to convert to a representative String (YES or NO).
	 * @return String - "Yes" when true, "No" otherwise
	 */
	public static String stringValueYesNo(boolean inBooleanToConvert) {
		String returnValue = Constant.NO;

		if (inBooleanToConvert){
			returnValue = Constant.YES;
		}

		return returnValue;
	}
	
	/**
	 * Retrieve the UPPER CASE String representation of a boolean value.
	 * @param boolean - the boolean value to convert to a representative String (YES or NO).
	 * @return String - "YES" when true, "NO" otherwise
	 */
	public static String stringValueYESNO(boolean inBooleanToConvert) {
		String returnValue = Constant.NO.toUpperCase();

		if (inBooleanToConvert){
			returnValue = Constant.YES.toUpperCase();
		}

		return returnValue;
	}
	/**
	 * Retrieve the UPPER CASE string representation of an indicator value or mixed case constant.
	 * Also account for the requested upper case value being provided already.
	 * @param String - The String value to convert to a representative String (YES, NO or empty string).
	 * @return String - "YES" when "Y" or "yes", "NO" when "N" or "No" - default to "empty string"
	 */
	public static String stringValueYESNOEmptyString(String inIndicatorValueToConvert) {
		String returnValue = Constant.EMPTY_STRING;

		if (inIndicatorValueToConvert.equals(Constant.YES_INDICATOR) || 
				inIndicatorValueToConvert.equals(Constant.YES.toUpperCase()) ||
				inIndicatorValueToConvert.equals(Constant.YES)){
			returnValue = Constant.YES;
		} else if (inIndicatorValueToConvert.equals(Constant.NO_INDICATOR)  || 
				inIndicatorValueToConvert.equals(Constant.NO.toUpperCase()) ||
				inIndicatorValueToConvert.equals(Constant.NO)){
			returnValue = Constant.NO;
		}

		// Convert to upper case
		returnValue = returnValue.toUpperCase();

		return returnValue;
	}
	
	/**
	 * Retrieve the UPPER CASE string representation of an indicator value or mixed case constant.
	 * Also account for the requested upper case value being provided already.
	 * @param String - The String value to convert to a representative String (YES, NO or UNKNOWN).
	 * @return String - "YES" when "Y" or "yes", "NO" when "N" or "No" - default to "UNKNOWN"
	 */
	public static String stringValueYESNOUnknown(String inIndicatorValueToConvert) {
		String returnValue = Constant.UNKNOWN;

		if (inIndicatorValueToConvert.equals(Constant.YES_INDICATOR) || 
				inIndicatorValueToConvert.equals(Constant.YES.toUpperCase()) ||
				inIndicatorValueToConvert.equals(Constant.YES)){
			returnValue = Constant.YES;
		} else if (inIndicatorValueToConvert.equals(Constant.NO_INDICATOR)  || 
				inIndicatorValueToConvert.equals(Constant.NO.toUpperCase()) ||
				inIndicatorValueToConvert.equals(Constant.NO)){
			returnValue = Constant.NO;
		}

		// Convert to upper case
		returnValue = returnValue.toUpperCase();

		return returnValue;
	}
	
	/**
	 * Retrieve the string indicator representation of a boolean value.
	 * @param boolean - the boolean value to convert to a representative String (YES or NO).
	 * @return String - "Y" when true, "N" otherwise
	 */
	public static String stringValueYN(boolean inBooleanToConvert) {
		return inBooleanToConvert ? Constant.YES_INDICATOR : Constant.NO_INDICATOR;
	}

	/** Default Constructor */
	private Util() { }

}