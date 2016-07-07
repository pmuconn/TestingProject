/*
 * ********************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION 2006. This software and
 * documentation contain confidential and proprietary information owned by
 * UnitedHealth Group Corporation. Unauthorized use and distribution are
 * prohibited.
 * ********************************************************************
 */
package com;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AUP Team
 */
/**
 * @author pmorano
 * 
 */
public final class PaulUtil extends UtilTool {

  public static boolean isNull(Object argObject) {
    if (argObject == null)
      return true;
    else
      return false;
  }

  public static int convertToInt(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException numberFormatException) {
      return -100;
    }
  }

  public static float convertToFloat(String value) {
    try {
      return Float.parseFloat(value);
    } catch (NumberFormatException numberFormatException) {
      return -100F;
    }
  }

  public static double convertToDouble(String value) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException numberFormatException) {
      return -100D;
    }
  }

  public static Date convertToSqlDate(java.util.Date argUtilDate) {
    java.sql.Date sqlDate = null;
    if (isNull(argUtilDate)) {
      return null;
    } else {
      sqlDate = new java.sql.Date(argUtilDate.getTime());
    }

    return sqlDate;
  }

  public static java.util.Date toUtilDate(String argS) {
    java.util.Date date = null;
    SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
    try {
      date = (java.util.Date) newFormat.parse(argS);
    } catch (Exception e) {
    }
    return date;

  }

  /**
   * This method checks whether the input string is of predefined valid format
   * 
   * @param argDateFormat Format of date that is sent
   * @param argS Date value in string format
   * @return java.util.Date
   */
  public static java.util.Date toUtilDate(String argS, String argDateFormat) {
    java.util.Date date = null;
    SimpleDateFormat newFormat = new SimpleDateFormat(argDateFormat);
    try {
      date = (java.util.Date) newFormat.parse(argS);
    } catch (Exception e) {
    }
    return date;
  }

  public static java.util.Date toTimeStamp(String argS) {
    java.util.Date date = null;
    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    try {
      date = (java.util.Date) newFormat.parse(argS);
    } catch (Exception eException) {
    }
    return date;
  }

  public static boolean isEmptyOrNull(String argValue) {
    boolean isEmpty = false;
    if (argValue == null || argValue.trim().length() == 0) {
      isEmpty = true;
    } else {
      isEmpty = false;
    }
    return isEmpty;
  }

  /**
   * This method will convert double value into US Locale Currency value in
   * String
   * 
   * @param argDoubleValue
   * @return US Locale String
   */
  public static String getFormattedValue(double argDoubleValue) {
    BigDecimal empClaims = new BigDecimal(argDoubleValue);
    NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);
    String empFormatted = numFormat.format(empClaims);
    return empFormatted;
  }

  /**
   * This method checks whether the input string is of predefined valid format
   * 
   * @param argPattern The pattern to be checked with
   * @param argValue The element value to be checked for
   * @return boolean
   * @throws AUPException
   */
  public static boolean isValidPattern(Pattern argPattern, String argValue)  {
    Matcher matcher = null;
    if (argPattern == null) {
    }
    if (argValue == null) {
    }
    matcher = argPattern.matcher(argValue);
    return matcher.matches();
  }

  /**
   * This method converts SQL date to java.util.date
   * 
   * @param java.sql.Date argSqlDate
   * @return java.util.Date
   */
  public static java.util.Date convertToUtilDate(Date argSqlDate) {
    java.util.Date utilDate = null;
    if (PaulUtil.isNull(argSqlDate)) {
      return null;
    } else {
      utilDate = (java.util.Date) argSqlDate;
    }
    return utilDate;
  }

  /*
   * calculateAge -Calculates age of member @param String argDOB @return int
   * @exception
   */
  public static int calculateAge(String argDOB) {
    java.util.Date currDate = new java.util.Date();
    java.util.Date dobObject = null;
    java.util.Date diff = null;
    int age = 0;
    dobObject = PaulUtil.toUtilDate(argDOB);
    Calendar today = new GregorianCalendar();
    Calendar dob = new GregorianCalendar();
    today.setTime(currDate);
    dob.setTime(dobObject);
    age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
    if (age >= 0) {
      if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
        age--;
      } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)) {
        if (today.get(Calendar.DATE) < dob.get(Calendar.DATE)) {
          age--;
        }
      }
    }
    return age;
  }

  /**
   * Returns the original date incremented by the number of days requested. To
   * decrement, use a negative number for days to add.
   * 
   * @param argDate the non-null date to start from
   * @param argDaysToAdd the number of days to add
   * @return the original date incremented by the number of days to add
   * @exception IllegalArgumentException if called with a null argument date
   */
  public static java.util.Date utilDateAddDays(java.util.Date argDate, int argDaysToAdd) {
    if (argDate == null) // No null dates allowed.
      throw new IllegalArgumentException("Date cannot be null");

    GregorianCalendar calculationCalendar = new GregorianCalendar();

    calculationCalendar.setTime(argDate); // Change the argDate into a
    // GCalendar
    calculationCalendar.add(Calendar.DATE, argDaysToAdd); // Add the number
    // of days

    return calculationCalendar.getTime(); // return the date
  }

  /**
   * Replaces all instances of oldSubString with newSubString within a String
   * 
   * @return String the revised string
   * @param s the initial String
   * @param oldSubStr the old substring to be replaced
   * @param newSubStr the new substring
   */
  public static String replace(String s, String oldSubStr, String newSubStr) {
    int index = s.indexOf(oldSubStr);
    if (index == -1)
      return s;
    int currentIndex = 0;
    int oldLen = oldSubStr.length();
    StringBuffer buf = new StringBuffer();
    for (; index != -1; index = s.indexOf(oldSubStr, currentIndex)) {
      buf.append(s.substring(currentIndex, index));
      buf.append(newSubStr);
      currentIndex = index + oldLen;
    }

    buf.append(s.substring(currentIndex));
    return buf.toString();
  }

  /**
   * This method will convert US Locale Currency value into double valus
   * 
   * @param argFormattedValue
   * @return double value
   */
  public static double getDoubleFromFormattedValue(String argFormattedValue) {
    String number = "";
    StringBuffer numberStr = new StringBuffer();
    double doubleValue = 0.0;

    try {
      if (!PaulUtil.isEmptyOrNull(argFormattedValue)) {
        number = argFormattedValue.substring(1);
        StringTokenizer strTokenizer = new StringTokenizer(number, ",");

        while (strTokenizer.hasMoreTokens()) {
          numberStr.append(strTokenizer.nextToken());
        }
        doubleValue = Double.parseDouble(numberStr.toString());
      }
    } catch (NumberFormatException numberFormatException) {
      return -100D;
    }

    return doubleValue;
  }

  /**
   * Returns true if the input is a valid long value
   * 
   * @param value java.lang.String
   * @param isNullable boolean
   * @param length int
   */
  public static boolean validateLong(String value, boolean isNullable, int length) {
    boolean retVal = false;
    StringBuffer temp = new StringBuffer(value);

    try {
      if (isNullable) {
        if (!value.equals("")) {
          if ((temp.length() == length)) {
            Long.parseLong(value);
            retVal = true;
          }
        } else {
          retVal = true;
        }
      } else {
        if (!value.equals("")) {
          if ((temp.length() == length)) {
            Long.parseLong(value);
            retVal = true;
          }
        }
      }
    } catch (NumberFormatException nfe) {
      retVal = false;
    }

    return retVal;
  }

  /**
   * Gets the Elapsed Time
   * 
   * @param argBeginTime begin time
   * @param argEndTime end time
   * @return the Elapsed Time
   */
  public static long getElapsedTime(long argBeginTime, long argEndTime) {
    /** local variable to hold the elapsed time */
    long elapsedTime = 0;

    elapsedTime = argEndTime - argBeginTime;

    return elapsedTime;
  }

  /**
   * This method checks the maximum length of the column of type String in the
   * feed with the allowed maximum length.
   * 
   * @param argColumn
   * @param argMaxSize
   * @return returns true or false based on the condition.
   */
  public static boolean checkMaxSizeValidation(String argColumn, int argMaxSize) {
    int columnLength = 0;
    String column = argColumn;
    columnLength = column.length();
    if (columnLength > argMaxSize) {
      return false;
    }
    return true;
  }

  public static boolean checkMaxValidation(String argColumn, int argMaxSize) {
    int columnLength = 0;
    String firstNum = "";
    String secondNum = "";
    StringBuffer numberStr = new StringBuffer();
    StringTokenizer strTokenizer = new StringTokenizer(argColumn, ".");
    while (strTokenizer.hasMoreElements()) {
      firstNum = (strTokenizer.nextToken());
      secondNum = (strTokenizer.nextToken());
    }

    if (firstNum.length() > 4) {
      return false;
    }
    if (secondNum.length() > 1) {
      return false;

    }
    return true;
  }

  /**
   * This method will return true if the column is not of type float
   * 
   * @param argFormattedValue
   * @return double value
   */
  public static boolean getFloatFromFormattedValue(String argFormattedValue) {
    boolean retValue;
    String number = "";

    try {
      Float.parseFloat(argFormattedValue.toString());
      retValue = true;
    } catch (Exception e) {
      retValue = false;
    }

    return retValue;
  }

  /**
   * This method will return true if the column is not of type int
   * 
   * @param argFormattedValue
   * @return double value
   */
  public static boolean getIntFromFormattedValue(String argFormattedValue) {
    boolean retValue;

    try {
      Integer.parseInt(argFormattedValue);
      retValue = true;
    } catch (Exception e) {
      retValue = false;
    }

    return retValue;
  }

  /**
   * This method checks whether the input string is of predefined valid format
   * 
   * @param argPattern The pattern to be checked with
   * @param argValue The element value to be checked for
   * @return boolean
   * @throws AUPException
   */
  public static boolean isValidPatternCheck(Pattern argPattern, String argValue)

  {
    Matcher matcher = null;
    matcher = argPattern.matcher(argValue);
    return matcher.matches();
  }

  /**
   * gets the field name as string and returns the float value as string
   * 
   * @param p_fieldName
   * @return
   */
  public static String getAsRequiredFloat(String p_fieldName) {
    float result = 0;
    String value = null;
    try {
      result = Float.parseFloat(p_fieldName);
      value = String.valueOf(result);
      value = value.trim();
    } catch (Exception e) {
      return value;
    }
    return value;
  }// End of getAsRequiredFloat()

  /**
   * gets the field name as string and returns the integer value as string
   * 
   * @param p_fieldName
   * @return
   */
  public static String getAsRequiredInt(String argFieldName) {
    float result = 0;
    String value = null;
    try {
      result = Integer.parseInt(argFieldName);
      value = String.valueOf(result);
      value = value.trim();
    } catch (Exception e) {
      return value;
    }
    return value;

  } // End of getAsRequiredInt()

  /**
   * gets the field name as Double and returns the double value as string
   * 
   * @param p_fieldName
   * @return
   */
  public static String getAsStringFromDouble(double argFieldName) {
    String result = null;
    try {
      result = String.valueOf(argFieldName);
      result = result.trim();
    } catch (Exception e) {
      return result;
    }
    return result;

  } // End of getAsStringFromDouble()

  /**
   * gets the field name as Double and returns the double value as string
   * 
   * @param p_fieldName
   * @return
   */
  public static String getAsStringFromDate(java.util.Date argFieldName) {
    String result = null;
    try {
      result = String.valueOf(argFieldName);
      result = result.trim();

    } catch (Exception e) {
      return result;
    }
    return result;

  } // End of getAsRequiredInt()

  public static String getMonthAndYear(String s) {
    String year;
    String month;
    int day;
    int firstDash;
    int secondDash;
    String monthAndYear = null;

    if (s == null)
      throw new java.lang.IllegalArgumentException();

    firstDash = s.indexOf('-');
    secondDash = s.indexOf('-', firstDash + 1);
    if ((firstDash > 0) & (secondDash > 0) & (secondDash < s.length() - 1)) {
      year = s.substring(0, firstDash);
      month = s.substring(firstDash + 1, secondDash);
      //day = Integer.parseInt(s.substring(secondDash+1));
      monthAndYear = month + year;
    } else {
      throw new java.lang.IllegalArgumentException();
    }

    return monthAndYear;
  }

  public static int convertToInteger(String argValue) {
    int result = 0;
    StringTokenizer st = new StringTokenizer(argValue, ".");
    try {

      while (st.hasMoreTokens()) {
        result = Integer.parseInt(st.nextToken());
        st.nextToken();
      }

      return result;
    } catch (NumberFormatException numberFormatException) {
      return result;
    }
  }

  public static String convertToStringFromDouble(String argValue) {
    String result = null;
    String token = null;
    StringTokenizer st = new StringTokenizer(argValue, ".");
    try {
      if ((argValue != null) && (argValue.trim().length() != 0)) {
        result = st.nextToken();
        if (st.hasMoreTokens()) {
          token = st.nextToken();
          if (token.charAt(0) != '0')
            result += token;

        }
      } else {
        result = argValue;
      }
      return result;
    } catch (NumberFormatException numberFormatException) {
      return result;
    }
  }

  public static String toStringFromUtilDate(java.util.Date argDate) {

  	if (argDate == null)
  		return "null";
    String result = null;
    SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
    result = newFormat.format(argDate);
    return result;
  }

  public static boolean getAsRequiredDate(String argDate, SimpleDateFormat p_formatDate) {

    boolean result;
    java.util.Date retDate = null;

    if (argDate == null || argDate.trim().length() == 0) {
      throw new IllegalArgumentException("p_tag cannot be null or empty");
    }
    String patternValue = p_formatDate.toPattern();
    int patLength = patternValue.length();
    try

    {
      //java.util.Date date = new java.util.Date(argDate);
      retDate = p_formatDate.parse(argDate);
      return true;
    }

    catch (Exception e)

    {

      return false;

    }

  } // End of getAsRequiredDate()

  /**
   * converts string values to float.Used when setting feed values in Domain
   * objects
   * 
   * @param value
   * @return
   */
  public static float convertToFloatValue(String value) {
    float result = 0;
    try {
      result = Float.parseFloat(value);
      return result;
    } catch (Exception e) {
      return result;
    }
  }

  /**
   * gets the field name as string and returns the integer value as string
   * 
   * @param p_fieldName
   * @return
   */
  public static String getAsRequiredIntValue(String argFieldName) {
    int result = 0;
    String value = null;
    try {
      result = Integer.parseInt(argFieldName);
      value = String.valueOf(result);
      value = value.trim();
    } catch (Exception e) {
      return value;
    }
    return value;

  } // End of getAsRequiredIntValue()

  /**
   * Converts date arguments to required date format.
   * 
   * @param argDate
   * @return String -String in Month Year Format
   */
  public static String toRendateFromUtilDate(java.util.Date argDate) {
    String result = "";
    if (argDate != null) {
      SimpleDateFormat newFormat = new SimpleDateFormat("MM/yyyy");
      result = newFormat.format(argDate);
    }
    return result;
  }

  //Added on 25/Oct/06-Starts here
  /**
   * This method is used to get the Due date with MM/DD/YYYY format
   * 
   * @param argDueDt
   * @return String - strDate
   */

  public static String getDueDate(java.util.Date argDueDt) {

    String strDate = "";
    if (argDueDt != null) {
      SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");

      strDate = dtFormat.format(argDueDt);
    }

    return strDate;

  }

  //	Added on 25/Oct/06-Ends here
  //	Added for FUW KA Online -starts
  /**
   * This method is used to convert date to the specified String format
   * 
   * @param argDate
   * @return
   */
  public static String convertDateToString(java.sql.Date argDate) {
    String strDate = "";
    if (argDate != null) {
      SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");
      strDate = dtFormat.format(argDate);
    }
    return strDate;
  }

  /**
   * This method converts timestamp to the specified datetime format
   * 
   * @param argRenDt
   * @return
   */
  public static String getDateTime(Timestamp argRenDt) {

    String strDate = null;
    if (argRenDt != null) {
      SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      strDate = dtFormat.format(argRenDt);
    }
    return strDate;

  }

  /**
   * This method is used to retrieve the year
   * 
   * @param argDate
   * @return int
   * @throws DAOException
   */
  public static int getYear(String argDate) {
    SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy");
    Calendar calendar = Calendar.getInstance();
    int year = 0;
    try {
      calendar.setTime(dtFormat.parse(argDate));
      year = calendar.get((Calendar.YEAR)) - 1;
    } catch (ParseException eParse) {
    }

    return year;
  }

  /**
   * This method is used to retrieve the roles.
   * 
   * @param argRoleType
   * @return String[]
   */
  public static String[] getRoles(String argRoleType) {
    String roles[] = null;
    if (argRoleType.indexOf(',') > 0) {
      roles = argRoleType.split(",");
    } else {
      roles = new String[1];
      roles[0] = argRoleType;
    }
    return roles;

  }

  //Added for KARA SYNCH PROCESS - ends

  //Some methods that do numeric/String conversions using DecimalFormat
  //All are null-safe
  public static String fmtBdToString(BigDecimal bd, DecimalFormat df) {
    if (bd == null)
      return "";
    return df.format(bd.doubleValue());
  }

  public static double fmtBdToDouble(BigDecimal bd) {
    if (bd == null)
      return 0.0;
    return bd.doubleValue();
  }

  public static int fmtBdToInt(BigDecimal bd) {
    if (bd == null)
      return 0;
    return bd.intValue();
  }

  public static long fmtBdToLong(BigDecimal bd) {
    if (bd == null)
      return 0;
    return bd.longValue();
  }

  public static String fmtDoubleToString(double d, DecimalFormat df) {
    BigDecimal bd = new BigDecimal(d);
    return fmtBdToString(bd, df);
  }

  public static double fmtNumStringToDouble(String s) {
    double d = 0.0;
    if (s == null || s.trim().length() == 0)
      return 0.0;
    DecimalFormat DF_4_DP = new DecimalFormat("###,##0.0000");
    DecimalFormat DF_2_DP = new DecimalFormat("###,##0.00");
    //first try parsing as currency
    DecimalFormat df = DF_2_DP;
    try {
      d = df.parse(s).doubleValue();
      return d;
    }
    //if that doesn't work, try the decimal formatter
    catch (ParseException pe) {
    }
    df = DF_4_DP;
    try {
      d = df.parse(s).doubleValue();
    }
    //throw an exception
    catch (ParseException pe) {
      d = 0.0;
    }
    return d;
  }

  public static long fmtNumStringToLong(String s)  {
      double d = fmtNumStringToDouble(s);
      return (long) Math.round(d);
  }

  public static BigDecimal fmtNumStringToBd(String s) {
    BigDecimal bd;
      bd = new BigDecimal(fmtNumStringToDouble(s));
    return bd;
  }

  public static java.util.Date addMonths(java.util.Date inDate, int numMonths) {
    if (inDate == null || numMonths == 0)
      return inDate;
    Calendar cal = Calendar.getInstance();
    cal.setTime(inDate);
    cal.add(Calendar.MONTH, numMonths);
    return cal.getTime();
  }

  /**
   * Performs round of a double value.
   * @param a is the double value to be rounded
   * @param scale is the round precision (ie. 2 for 2 places)
   * @return the rounded double.
   */
  public static double roundDecDigits(double a, int scale) {
    double num = scale;
    double _deka_ = 10.0;
    double c = Math.pow(_deka_, num);
    double d = a * c;
    double result1 = java.lang.Math.round(d);
    double the_result;
    the_result = result1 / c;
    return the_result;
  }
  public static String dateAsMmyyyyStr(java.util.Date date) {
    if (date == null)
      return "null";
    SimpleDateFormat sdf = new SimpleDateFormat ("MM-yyyy");
    return sdf.format(date);
  }
  
  /*
   * Converts TreeSet to an ArrayList, with the objects in 
   * the sequence maintained by the TreeSet
   * @author ablasen
   *
   */
  
  public static List cvtTreeSetToList (TreeSet treeSet) {
  	List list = new ArrayList();
  	if (treeSet != null) {
  		Iterator iter = treeSet.iterator();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
  	}
  	return list;
  }

  /*
   * Converts List to an TreeSet, with the objects in 
   * the sequence maintained by the TreeSet
   * @author ablasen
   *
   */
  
  public static TreeSet cvtListToTreeSet (List list) {
  	TreeSet treeSet = new TreeSet();
  	if (treeSet != null) {
  		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			treeSet.add(iter.next());
		}
  	}
  	return treeSet;
  }

	/**
	 * 
	 * @param Object
	 * @return String
	 */
//	public static final String serialize(Object obj) {
//	   ByteArrayOutputStream baos = null;
//	   GZIPOutputStream zipper = null;
//	   ObjectOutputStream o = null;
//	   try {
//		   baos = new ByteArrayOutputStream();
//		   zipper = new GZIPOutputStream(baos);
//			
//		   o = new ObjectOutputStream(zipper);
//		   o.writeObject(obj);
//		   o.flush();
//	   } catch(IOException ioe){
//		 //  m_logger.error("IOException", ioe);
//	   } finally {
//		   if(o != null){
//			   try {o.close();} catch(Exception ex){/**/}
//		   }
//		   if(zipper != null){
//			   try {
//				   zipper.finish();
//				   zipper.close();
//			   } catch(Exception ex){/**/}
//		   }
//		   if(baos != null){
//			   try {baos.close();} catch(Exception ex){/**/}
//		   }
//	   }
//	   return(new String(Base64.encode(baos.toByteArray())));
// }
//	
//	/**
//	 * 
//	 * @param String
//	 * @return Object
//	 */
//  public static final Object deserialize(String data) {
//	   Object obj = null;
//	   ByteArrayInputStream bais = null;
//	   GZIPInputStream unzipper = null;
//	   ObjectInputStream o = null;
//	 
//	   try {
//		   bais = new ByteArrayInputStream(Base64.decode(data));
//		   unzipper = new GZIPInputStream(bais);
//		   o = new ObjectInputStream(unzipper);
//		   obj = o.readObject();
//	   } catch(IOException ioe){
//		 //  m_logger.error("IOException", ioe);
//	   } catch(ClassNotFoundException cnfee){
//		//   m_logger.error("ClassNotFoundException", cnfee);
//	   } finally {
//		   if(o != null){
//			   try {o.close();} catch(Exception ex){/**/}
//		   }
//		   if(unzipper != null){
//			   try {unzipper.close();} catch(Exception ex){/**/}
//		   }
//		   if(bais != null){
//			   try {bais.close();} catch(Exception ex){/**/}
//		   }
//	   }
//	   
//	   return(obj);
// }
 
}