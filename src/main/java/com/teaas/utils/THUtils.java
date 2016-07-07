package com.teaas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @DESC   This class provides the common utilities for the application
 * @author UHG
 */
public class THUtils {
	private static final Logger logger = LoggerFactory.getLogger(THUtils.class);
	/**
	 * @Desc  This method returns true if the value is null 
	 * @param   param
	 * @return boolean
	 */
	public static boolean isNull(String value) {
		boolean result = Boolean.FALSE;
		if (value == null || value.trim().isEmpty()) {
			result = Boolean.TRUE;
		}
		return result;
	}
	
	/**
	 * Checks if an object is null
	 * @param argObject
	 * @return true if null, false otherwise
	 */
	public static boolean isNull(Object argObject) {
	    if (argObject == null)
	      return true;
	    else
	      return false;
	}

	/**
	 * @Desc  This method returns true if the value is not null 
	 * @param   param
	 * @return boolean
	 */
	public static boolean isNotNull(String value) {
		boolean result = Boolean.FALSE;
		if (value != null && !value.isEmpty()) {
			result = Boolean.TRUE;
		}
		return result;
	}
	/**
	 * @Desc  This method returns true if the value is not null 
	 * @param   param
	 * @return boolean
	 */
	public static boolean isNotNull(Date value) {
		boolean result = Boolean.FALSE;
		if (value != null) {
			result = Boolean.TRUE;
		}
		return result;
	}
	
	public static String getCollectionAsString(Collection<String> stringValues, String delim) {
		StringBuffer finalVal = new StringBuffer();
		boolean appendComma = false;
		for (String value : stringValues) {
			if (appendComma) {
				finalVal.append(delim);
			}
			finalVal.append(value);
			appendComma = true;
		}
		return finalVal.toString();
	}
	
	/**
	 * @Desc   Adds the specified number of years to the given date  
	 * @param  date
	 * @param  years
	 * @return java.util.Date
	 */
    public static Date addYearsToDate(final Date date, final int years) {
        Date  calculatedDate = null;
        if (date != null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, years);
            calculatedDate = new Date(calendar.getTime().getTime());
        }
        return calculatedDate;
    }

	/**
	 * @Desc   This method escapes only < and > chars if present.  
	 * @param  original
	 * @return String
	 */
	public static String escapeOnlyTags(String original) {
		if (original == null)
			return null;
		if (original.contains("<") || original.contains(">")) {
			StringBuffer out = new StringBuffer("");
			char[] chars = original.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				boolean found = true;
				switch (chars[i]) {
				case 60:
					out.append("&lt;");
					break; // <
				case 62:
					out.append("&gt;");
					break; // >
				default:
					found = false;
					break;
				}
				if (!found)
					out.append(chars[i]);
			}
			return out.toString();
		}
		return original;
	}
    /**
     * @Desc   This method returns date from a date with time stamp.
     * @param  date
     * @return java.util.Date
     */
    public static Date getDateWithoutTime(Date date) {
    	Date modifiedDate = null;
    	if (isNotNull(date)) {
    		modifiedDate = DateUtils.truncate(date, Calendar.DATE);
    		logger.debug("Modified Date in the getDateWithoutTime: " + modifiedDate);
    	}
  	   return modifiedDate;
    }
    /**
     * @Desc   This method returns a list of objects with a given delimited string value (eg:1,2,3,4 to [1,2,3,4])
     * @param  delimiter
     * @param  delimiterSeperatedValues
     * @return java.util.Collection<Object>
     */
    public static Collection<Object> getListFromDelimitedValueString(String delimiter, String delimiterSeperatedValues) {
    	List<Object> resultList = null;
    	if (isNotNull(delimiterSeperatedValues)) {
    		resultList = new ArrayList<Object>(Arrays.asList(delimiterSeperatedValues.split(delimiter))); 
    	}
    	return resultList;
    }
    /**
     * @Desc   This method returns a string of a given delimiter and a list of values  (eg:[1,2,3,4] to 1,2,3,4)
     * @param  delimiter
     * @param  listOfValues
     * @return java.lang.String
     */
    public static String getDelimitedValueStringFromList(String delimiter, Collection<Object> listOfValues) {
    	String resultValue = "";
    	if (listOfValues != null && !listOfValues.isEmpty()) {
    		resultValue = StringUtils.join(listOfValues.toArray(), delimiter);
    	}
    	return resultValue;
    }
    
	/**
	 * Covert an object into a string for logging purposes.
	 * @param Object - the object to convert to a string
	 * @return String - the string representation of the object
	 */
	public static String objectToString(Object inObject) {
		String returnValue = "";
		
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
								buffer.append("\"");
								buffer.append(valueString);
								buffer.append("\"");
							} else {
								buffer.append(valueString);
							}
						}
					} catch (IllegalArgumentException e) {
				    	logger.error("Illegal argument exception: " + e);
					} catch (IllegalAccessException e) {
						logger.error("Illegal access exception: " + e);
					}			
					field.setAccessible(false);
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
	 * Splits a list into smaller sublists.
	 * The original list remains unmodified and changes on the sublists are not propagated to the original list.
	 *
	 *
	 * @param original
	 *            The list to split
	 * @param maxListSize
	 *            The max amount of element a sublist can hold.
	 * @param listImplementation
	 *            The implementation of List to be used to create the returned sublists
	 * @return A list of sublists
	 * @throws IllegalArgumentException
	 *             if the argument maxListSize is zero or a negative number
	 * @throws NullPointerException
	 *             if arguments original or listImplementation are null
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final <T> List<List<T>> split(final List<T> original, final int maxListSize, final Class<? extends List> listImplementation) {
	    if (maxListSize <= 0) {
	        throw new IllegalArgumentException("maxListSize must be greater than zero");
	    }

	    final T[] elements = (T[]) original.toArray();
	    final int maxChunks = (int) Math.ceil(elements.length / (double) maxListSize);

	    final List<List<T>> lists = new ArrayList<List<T>>(maxChunks);
	    for (int i = 0; i < maxChunks; i++) {
	        final int from = i * maxListSize;
	        final int to = Math.min(from + maxListSize, elements.length);
	        final T[] range = Arrays.copyOfRange(elements, from, to);

	        lists.add(createSublist(range, listImplementation));
	    }

	    return lists;
	}

	/**
	 * Splits a list into smaller sublists. The sublists are of type ArrayList.
	 * The original list remains unmodified and changes on the sublists are not propagated to the original list.
	 *
	 *
	 * @param original
	 *            The list to split
	 * @param maxListSize
	 *            The max amount of element a sublist can hold.
	 * @return A list of sublists
	 */
	public static final <T> List<List<T>> split(final List<T> original, final int maxListSize) {
	    return split(original, maxListSize, ArrayList.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> List<T> createSublist(final T[] elements, final Class<? extends List> listImplementation) {
	    List<T> sublist;
	    final List<T> asList = Arrays.asList(elements);
	    try {
	        sublist = listImplementation.newInstance();
	        sublist.addAll(asList);
	    } catch (final InstantiationException e) {
	        sublist = asList;
	    } catch (final IllegalAccessException e) {
	        sublist = asList;
	    }

	    return sublist;
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
	
	/**
	 * Reads the contents of a url.
	 * @param url
	 * @return the contents of the passed url 
	 */
	public static String readUrlContents(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			URL u = new URL(url);
	        BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            System.out.println(inputLine);
	            sb.append(inputLine);
	        }
	        in.close();
		} catch (Exception e) {
			logger.error("Error reading from url, " + url + " error: " + e.getMessage());
			sb = new StringBuffer();
		}
		
		return sb.toString();
	}
	
	/**
	 * converts a string to an InputStream
	 * @param inString
	 * @return
	 * @throws IOException
	 */
	public static InputStream toInputStream(String inString) throws IOException {
		InputStream in = IOUtils.toInputStream(inString, "UTF-8");
		return in;
	}
	
	/**
	 * Converts an Inputstream to string.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String fromInputStream(InputStream is) throws IOException {
		String returnString = IOUtils.toString(is, "UTF-8");
		return returnString;
	}
	
	/**
	 * @return system specific newline separator.
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}
	
	/**
	 * @param urlToCheck String represention of a url
	 * @return true if the url returned a good connection (active and live), false otherwise.
	 */
	public static boolean isUrlLive(String urlToCheck) {
		boolean returnValue = false;
		try {
			URL url = new URL(urlToCheck);
			int code = 0;
			if (url.getProtocol().equalsIgnoreCase("https")) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

				HttpsURLConnection huc = (HttpsURLConnection) url.openConnection();
				code = huc.getResponseCode();
//				print_https_cert(huc);
//				print_content(huc);
			} else {
				HttpURLConnection huc =  ( HttpURLConnection )  url.openConnection (); 
				huc.setRequestMethod ("GET");
				huc.connect () ; 
				code = huc.getResponseCode() ;
			}
			System.out.println(code);		
			if (200 == code) {
				returnValue = true;
			} else {
				returnValue = false;
			}
		} catch (MalformedURLException e) {
			logger.error("Checked url, " + urlToCheck + " caused error: " + e.getMessage());
			returnValue = false;
		} catch (Exception e) {
			logger.error("Checked url, " + urlToCheck + " caused error: " + e.getMessage());
			returnValue = false;
		}
		return returnValue;
	}
	
	private static void print_https_cert(HttpsURLConnection con) {
		if (con != null) {
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	 
	private static void print_content(URLConnection con) {
		if (con != null) {
			try {
				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static int getSubstringIndex(String str, String subString, int n) {
	    int times = 0, index = 0;

	    while (times < n && index != -1) {
	        index = str.indexOf(subString, index+1);
	        times++;
	    }

	    return index;
	}
	
	public static String removeLineFeeds(String inputString) {
		return inputString.replaceAll("(\\r|\\n|\\t)", "");
	}
	
    public static String parseDateToString(String inDatePattern, Date inDate) {   
    	String returnValue = "";
    	SimpleDateFormat dateFormatter = new SimpleDateFormat();
    	
        if (inDate != null && THUtils.isNotNull(inDatePattern)) {
        	dateFormatter.applyPattern(inDatePattern);
        	returnValue = dateFormatter.format(inDate);
        } else {
        	logger.error("Failed to parse date to string, date argument is null");
        }
        
        return returnValue;
    }
    
    public static Date stringToDate(String inDate, String inFormat)	{ 
		Date returnValue;
		SimpleDateFormat dateFormat = new SimpleDateFormat(inFormat);
	    try {
	    	returnValue = dateFormat.parse(inDate);
	    } catch (Exception e) {
	    	returnValue = new Date();
	    	logger.error("Failed to parse inDate string value to a date: " + e + ":Returning current date instead: " + new Date().toString());
	    }

	    return returnValue;	     
	}   
    
    
    public static String getRightEndofString(String source, String identifier) {
    	String subString = source.substring(0, source.indexOf(identifier) + identifier.length());
    	String finalStr = source.substring(subString.length());
    	return finalStr;
    }
    
    
    public static String getRootNodeName(String xPath, String elementRequest) {
    	String rootNodeName = null;
    	String tempStr = null;
    	String finalStr = null;
    	if(xPath.contains(elementRequest)) {
    		tempStr = xPath.substring(0, xPath.indexOf(elementRequest)+elementRequest.length());		
    		finalStr = xPath.substring(tempStr.length()+1);
    		rootNodeName = finalStr.substring(0, finalStr.indexOf("/"));
    	}
    	return rootNodeName;
    }
    
}
