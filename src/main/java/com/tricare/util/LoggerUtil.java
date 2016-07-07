/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <3011>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
*/
package com.tricare.util;

import org.apache.log4j.Logger;

/**
 * This is a utility tool for logging functionality.
 * @author wprevos
 * @since Q1 2013 - TriCare West
 */
public final class LoggerUtil {
	
	/** Default Constructor */
	private LoggerUtil() { }
	
	/**
	 * The index in the stack trace to retrieve 
	 * the caller's class and method that invoked 
	 * the LoggerUtil to log a message
	 */
	private static final int STACK_TRACE_START_INDEX = 4;
	
	/**
	 * Retrieve a logger for the given class, if the class
	 * is not provided, then the root logger will be returned
	 * @param Class<?> - the caller
	 * @return Logger - the logger to append to
	 */
	public static Logger getLogger() {
		Logger returnValue = null;

		try {
			returnValue = Logger.getLogger(Class.forName(getFullyQualifiedClassName()));
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException in LoggerUtil; error is: "+e.getMessage());
		}

		return returnValue;
	}
	
	/**
	 * Format the class name and method name for logging
	 * @return String - formatted as such: ClassName.methodName - 
	 */
	private static String formatClassAndMethodName() {
		String returnValue = Constant.EMPTY_STRING;
		StackTraceElement[] stackTrace;
		String className = Constant.EMPTY_STRING;
		String methodName = Constant.EMPTY_STRING;
		int index = STACK_TRACE_START_INDEX, lastDotIndex = 0;
		StringBuffer prependMessage;
		
		stackTrace = Thread.currentThread().getStackTrace();
		tryStackTraceAgain: {
			try {
				className = stackTrace[index].getClassName();
				methodName = stackTrace[index].getMethodName();
			} catch (IndexOutOfBoundsException e) {
				index--;
				break tryStackTraceAgain;
			}
		}
		
		lastDotIndex = className.lastIndexOf(".");
		className = className.substring(lastDotIndex + 1);
		
		prependMessage = new StringBuffer();
		prependMessage.append(className);
		prependMessage.append(".");
		prependMessage.append(methodName);
		prependMessage.append(Constant.WHITE_SPACE);
		prependMessage.append(Constant.HYPHEN);
		prependMessage.append(Constant.WHITE_SPACE);
		returnValue = prependMessage.toString();
		
		return returnValue;
	}
	
	/**
	 * gets fully qualified class name of the class that called LoggerUtil
	 * @return
	 */
	private static String getFullyQualifiedClassName() {
		StackTraceElement[] stackTrace;
		String className = Constant.EMPTY_STRING;
		int index = STACK_TRACE_START_INDEX;
				
		stackTrace = Thread.currentThread().getStackTrace();
		tryStackTraceAgain: {
			try {
				className = stackTrace[index].getClassName();
			} catch (IndexOutOfBoundsException e) {
				index--;
				break tryStackTraceAgain;
			}
		}
		return className;
	}
	
//	/**
//	 * Log an AUDIT message
//	 * @param Object - message to audit
//	 */
//	public static void audit(Object inMessage) {
//		Logger logger = getRootLogger();
//		String prependMessage = formatClassAndMethodName();
//		logger.log(AuditLevel.AUDIT, prependMessage + inMessage);
//	}
//	
//	/**
//	 * Log a TRACE message only if TRACE is enabled
//	 * @param Object - the message to trace
//	 */
//	public static void trace(Object inMessage) {
//		Logger logger = getRootLogger();
//		String prependMessage = formatClassAndMethodName();
//		if (logger.isTraceEnabled()) {
//			logger.trace(prependMessage + inMessage);
//		}
//	}
	
	/**
	 * Log a DEBUG message only if DEBUG is enabled
	 * @param Object - the message to debug
	 */
	public static void debug(Object inMessage) {
		Logger logger = getLogger();
		if (logger.isDebugEnabled()) {
			logger.debug(formatClassAndMethodName() + inMessage);
		}
	}
	
	/**
	 * Log an INFO message only if INFO is enabled
	 * @param Object - the message to info
	 */
	public static void info(Object inMessage) {
		Logger logger = getLogger();
		if (logger.isInfoEnabled()) {
			logger.info(formatClassAndMethodName() + inMessage);
		}
	}
	
	/**
	 * Log a WARN message
	 * @param Object - the message to warn
	 */
	public static void warn(Object inMessage) {
		getLogger().warn(formatClassAndMethodName() + inMessage);
	}
	
	/**
	 * Log an ERROR message
	 * @param Object - the message to error
	 */
	public static void error(Object inMessage) {
		getLogger().error(formatClassAndMethodName() + inMessage);
	}
	
	/**
	 * Log a FATAL message
	 * @param Object - the message to fatal
	 */
	public static void fatal(Object inMessage) {
		getLogger().fatal(formatClassAndMethodName() + inMessage);
	}
	
}
