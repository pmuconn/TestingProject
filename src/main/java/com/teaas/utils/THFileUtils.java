package com.teaas.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @DESC   This class provides the common utilities for the application
 * @author UHG
 */
public class THFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(THFileUtils.class);

	/**
	 * Get file from the filesystem as a string. This also will strip any tabs in the file.
	 * @param directory
	 * @param fileName
	 * @return String representation of the file. If an error occurs, an empty string is returned.
	 */
	public static String getFileSystemFileAsString(String directory, String fileName) {

		String returnValue = "";
		logger.debug("File Name: " + fileName);

		try {
			returnValue = readFile(directory + fileName);
			returnValue = stripTabsFromString(returnValue);
		} catch (IOException ioErr) {
			// Ignore IO Errors on file read.
			logger.error("IO Error from Utility readFile method in getPlanReplyFromFileSystem trying to read path/file " + directory + fileName);
			returnValue = "";
		}

		return returnValue;
	}

	public static String getFileSystemFileAsString(String fullPathToFile) {
		String returnValue = "";
		logger.debug("File Name: " + fullPathToFile);
		try {
			returnValue = readFile(fullPathToFile);
			returnValue = stripTabsFromString(returnValue);
		} catch (IOException ioErr) {
			// Ignore IO Errors on file read.
			logger.error("IO Error from Utility readFile method in getPlanReplyFromFileSystem trying to read path/file " + fullPathToFile);
			returnValue = "";
		}

		return returnValue;
	}

	/**
	 * Read a file from the file system based on the supplied path and file and
	 * return a string of the file contents.
	 * 
	 * @param String
	 * @return String
	 * @exception java.io.IOException
	 */
	public static String readFile(String path) throws IOException {

		StringBuffer buffer = new StringBuffer();
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		String line = null;
		try {
			fileInputStream = new FileInputStream(path);
			bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException ioe) {
			logger.error("Unable to read file based on supplied path/file of " + path + "  IOError is: " + ioe.getMessage());
			throw ioe;
		}

		finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
				throw new IOException("bean method raised unchecked exception during FINALLY process of readFile method.  IOError is: " + ioe.getMessage());
			}
		}

		return buffer.toString();
	}

	/**
	 * Removes all tabs from a String
	 * 
	 * @param string
	 * @return String
	 */
	private static String stripTabsFromString(String string) {
		char[] charArray = string.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char theCharacter = charArray[i];
			if (theCharacter == '\t') {
			} else
				buffer.append(theCharacter);
		}
		return buffer.toString();
	}
	
	/**
	 * Gets a system resource file as a reader
	 * 
	 * Once you have the reader, you can read line by line. For example:
	 * 
	 * <pre>
	 * {@code
	 * String strLine;
	 * while ((strLine = bufferedReader.readLine()) != null)   {
	 * 	System.out.println (strLine);
	 * }</pre>
	 * 
	 * @param resourceName is the filename of the system resource
	 * @return BufferedReader
	 */
	public static BufferedReader getResource(String resourceName) {
		BufferedReader returnValue = null;

		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(is);
		returnValue = new BufferedReader(new InputStreamReader(in));
		
		//Close the input stream
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}
 
	/**
	 * Removes a File object. This can be a full directory or an individual file. If directory is not empty, it will still be removed.
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				logger.info("Directory is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			logger.info("File is deleted : " + file.getAbsolutePath());
		}
	}
	
	/**
	 * write UTF-8 encoded data into a temporary file 
	 * @param filename
	 * @param extension
	 * @param data
	 * @return
	 */
	public static File createTempUTF8File(String filename, String extension, String data) {
		File f = null;
		try {
			f = File.createTempFile(filename, extension);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
			out.write(data);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			logger.error("Error creating file, " + filename + extension + " :" + e.getMessage());
		} catch (IOException e) {
			logger.error("Error creating file, " + filename + extension + " :" + e.getMessage());
		} catch (Exception e) {
			logger.error("Error creating file, " + filename + extension + " :" + e.getMessage());
		}
		return f;
	}
	
	/**
	 * Removes all files matching the filename wildcard. ie. Test*.vsi from the root directory and all subdirectories  
	 * @param directory
	 * @param fileWildCard case sensitive wildcard.
	 */
	public static void deleteFilesByWildcard(String directory, String fileWildCard) {
		File dir = new File(directory);
		List<File> files = (List<File>) FileUtils.listFiles(dir, new WildcardFileFilter(fileWildCard), TrueFileFilter.INSTANCE);
		for (File file : files) {
			try {
				logger.info("Deleting file: " + file.getCanonicalPath());
				file.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
