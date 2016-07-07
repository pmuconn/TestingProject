/*
 * Created on Jul 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.DateValidator;

//import com.uhc.uw.fin.util.FUWUtil;

/**
 * @author pmorano
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DateTest
{

	public static void main(String[] args)
	{
		checkDate("012/10/200", "Date", "MM/dd/yyyy");
	}

	public static Object checkDate(String value, String dataType, String pattern)
	{
		/** Pattern for the decimal formatter */
		//		String pattern = "";
		String errorMessage = "Invalid date.";
		/** Data Type - integer or date need to be hardcoded */
		//		String dataType = "";
		String DATATYPE_STRING = "String";
		String DATATYPE_DATE = "Date";

		String ERROR_INV_TYPE = "Invalid data type";
		String ERROR_NOT_VALID = "Date is missing or not valid";

		Date date = null;
		boolean validPattern = false;
		boolean isError = false;

		ParsePosition pos = new ParsePosition(0);
		DateValidator validator = DateValidator.getInstance();

		String regExMMddyyyy = "([0-9][0-9])[/]([0-9][0-9])[/]([0-9][0-9][0-9][0-9])";
		try
		{
			//Checks the pattern and length. Boolean is Strict/lenient.
			//If strict is true, then the length will be checked 
			validPattern = validator.isValid(value, pattern, true);

			if (validPattern) {
				String regEx = createRegExFromPattern(pattern);
				Pattern testPattern = Pattern.compile(regEx);
				validPattern = isValidPattern(testPattern,value);
			}

			if (validPattern)
			{
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				sdf.setLenient(false);
				date = sdf.parse(value, pos);
				DateFormatSymbols symb = sdf.getDateFormatSymbols();
				String sdfpattern = sdf.toPattern();
				System.out.println("the lenient flag: " + sdf.isLenient());

				//Check all possible things that signal a parsing error
				if ((date == null) || (pos.getErrorIndex() != -1))
				{
					isError = true;
					if (date == null)
					{
						isError = true;
					}
					if (pos.getErrorIndex() != -1)
					{
						isError = true;
					}
				}
				if (pos.getIndex() != value.length())
				{
					//encounters a non-numeric and stops the parse.
					//The position index will not match the value length.
					isError = true;
				}
			}
			else
			{
				isError = true;
			}
		}
		catch (IllegalArgumentException ex)
		{
			//Checks if pattern is valid. Thrown by SimpleDateFormat
			isError = true;
		}
		catch (NullPointerException ex)
		{
			//Checks null from pattern. Thrown by SimpleDateFormat
			isError = true;
		}
		catch (Exception ex)
		{
			//anything we missed.
			isError = true;
		}

		if (isError)
		{
			String returnMessage = null;
			if (errorMessage != null)
			{
				returnMessage = errorMessage;
			}
			else
			{
				returnMessage = ERROR_NOT_VALID;
			}
			System.out.println(returnMessage);
		}

		if (DATATYPE_STRING.equalsIgnoreCase(dataType))
		{
			System.out.println("output date: " + date);
			return value;
		}
		else if (DATATYPE_DATE.equalsIgnoreCase(dataType))
		{
			System.out.println("output date: " + date);
			return date;
		}
		else
		{
			System.out.println(ERROR_INV_TYPE);
			return "";
		}
	}

	public static boolean isValidPattern(Pattern argPattern, String argValue)
	{
		Matcher matcher = null;
		if (argPattern == null)
		{
			return false;
		}
		if (argValue == null)
		{
			return false;
		}
		matcher = argPattern.matcher(argValue);
		return matcher.matches();
	}

	public static String getSeparator(String pattern) {
		char chArray[] = pattern.toCharArray();
		for (int i = 0; i < chArray.length; i++) {
			if (chArray[i] != 'd' && chArray[i] != 'D' && chArray[i] != 'm'
					&& chArray[i] != 'M' && chArray[i] != 'y'
					&& chArray[i] != 'Y') {
				return String.valueOf(chArray[i]);
			}
		}
		return "";
	}

	public static String createRegExFromPattern(String pattern) {
		final String NUMBER = "[0-9]";
		char separator = ' ';
		StringBuffer regEx = new StringBuffer();
		String regExMMddyyyy = "([0-9][0-9])[/]([0-9][0-9])[/]([0-9][0-9][0-9][0-9])";
		
//		if (!FUWUtil.isEmptyOrNull(getSeparator(pattern)) ) {
//			separator = getSeparator(pattern).charAt(0);
//		}

		char chArray[] = pattern.toCharArray();
		regEx.append("("); //start the expression
		for (int i = 0; i < chArray.length; i++) {
			if (chArray[i] != separator) {
				regEx.append(NUMBER);
			} else {
				regEx.append(")");
				regEx.append("[" + separator + "]");
				regEx.append("(");
			}
		}
		regEx.append(")"); //end the expression
		return regEx.toString();
	}

}
