package com.teaas.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipHelper {
	private final static int BUFFER_SIZE = 2048;  
	private final static String ZIP_EXTENSION = ".zip"; 
	private static final Logger logger = LoggerFactory.getLogger(ZipHelper.class);

	public static String unzipToTempDir(String srcZipFileName) { 
		return unzip(srcZipFileName,System.getProperty("java.io.tmpdir"));
	}
	
	public static String unzip(String srcZipFileName, 
			   String destDirectoryName) { 
			  try { 
			  
			   // open archive for reading 
			   File file = new File(srcZipFileName);
			   
			   BufferedInputStream bufIS = null; 
				
			   
			   String justFileName = file.getName();
			   
			   justFileName = justFileName.substring(0,justFileName.lastIndexOf("."));
			   
			   String unzipDir = destDirectoryName+File.separator+justFileName;
			   
			   // create the destination directory structure (if needed) 
			   File destDirectory = new File(unzipDir); 
			   destDirectory.mkdirs(); 
			   
			   ZipFile zipFile = new ZipFile(file, ZipFile.OPEN_READ);
			   
			   //for every zip archive entry do 
			   Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries(); 
			   while (zipFileEntries.hasMoreElements()) { 
			    ZipEntry entry = (ZipEntry) zipFileEntries.nextElement(); 
			    System.out.println("\tExtracting entry: " + entry); 
			  
			    //create destination file 
			    File destFile = new File(destDirectory, entry.getName()); 
			  
			    //create parent directories if needed 
			    File parentDestFile = destFile.getParentFile();     
			    parentDestFile.mkdirs();     
			      
			    if (!entry.isDirectory()) { 
			     bufIS = new BufferedInputStream( 
			       zipFile.getInputStream(entry)); 
			     int currentByte; 
			  
			     // buffer for writing file 
			     byte data[] = new byte[BUFFER_SIZE]; 
			  
			     // write the current file to disk 
			     FileOutputStream fOS = new FileOutputStream(destFile); 
			     BufferedOutputStream bufOS = new BufferedOutputStream(fOS, BUFFER_SIZE); 
			  
			     while ((currentByte = bufIS.read(data, 0, BUFFER_SIZE)) != -1) { 
			      bufOS.write(data, 0, currentByte); 
			     } 
			  
			     // close BufferedOutputStream 
			     bufOS.flush(); 
			     bufOS.close(); 
			  
			     // recursively unzip files 
			     if (entry.getName().toLowerCase().endsWith(ZIP_EXTENSION)) { 
			      String zipFilePath = destDirectory.getPath() + File.separatorChar + entry.getName(); 
			  
			      unzip(zipFilePath, zipFilePath.substring(0,  
			              zipFilePath.length() - ZIP_EXTENSION.length())); 
			     } 
			    } 
			   } 
			   bufIS.close(); 
			   return unzipDir; 
			  } catch (Exception e) { 
			   e.printStackTrace(); 
			   return null; 
			  } 
	 }

	
	
	/**
	 * Zip it
	 * 
	 * @param zipFile
	 *   output ZIP file location
	 */
	public static void zipToFile(String sourceDirPath,String destFileName) {
		
		
		byte[] buffer = new byte[BUFFER_SIZE];
	
		try {
			
			
			
			ByteArrayOutputStream zipFileAsStream = zipToStream(sourceDirPath);
			
			
			ByteArrayInputStream in = new ByteArrayInputStream(zipFileAsStream.toByteArray());
			
			FileOutputStream fos = new FileOutputStream(destFileName);
			
			int len;
			while ((len = in.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			
			fos.flush();
			fos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

	/**
	 * Zip it
	 * 
	 * @param zipFile
	 *   output ZIP file location
	 */
	public static ByteArrayOutputStream zipToStream(String sourceDirPath) {
		
		byte[] buffer = new byte[BUFFER_SIZE];

		try {

			ArrayList<String> fileList = new ArrayList<String>();
					
			generateFileList(sourceDirPath,null,fileList);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(bos);


			for (String file : fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(sourceDirPath + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
			
			return bos;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * This method returns the byteArrayOutputStream object for the given file.
	 * @param fileName
	 * @return bos - ByteArrayOutputStream
	 */
	public static ByteArrayOutputStream zipToStream(File file) {
				
		byte[] buffer = new byte[BUFFER_SIZE];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			
			ZipOutputStream zos = new ZipOutputStream(bos);
			System.out.println("File Added : " + file);
			ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);

			FileInputStream in = new FileInputStream(file);

			int len;
			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}

			in.close();
			zos.closeEntry();
			// remember close it
			zos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return bos;
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList
	 * 
	 * @param node
	 *            file or directory
	 */
	private static void generateFileList(String actualPath , String currentPath, ArrayList<String> fileList) {

		if(currentPath == null){
			currentPath = actualPath;
		}
		
		if(fileList == null){
			fileList = new  ArrayList<String>();
		}
	
		File sourceDir = new File(currentPath);
		
		// add file only
		if (sourceDir.isFile()) {
			fileList.add(generateZipEntry(actualPath , sourceDir.getAbsoluteFile().toString()));
		}else { // must be a directory
			String[] subNote = sourceDir.list();
			for (String filename : subNote) {
				File childFile = new File(sourceDir, filename);
				generateFileList(actualPath , childFile.getAbsolutePath() , fileList);
			}
		}
		
		
	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private static String generateZipEntry(String sourceDirPath, String  zipEntryPath) {
		return zipEntryPath.substring(sourceDirPath.length() + 1, zipEntryPath.length());
	}
	
	/**
	 * This method is used to extract a zipfile containing request and response pairs as text files.
	 * @param zipInputStream
	 * @return Map keyed by filename with the string contents of the file. Map will be empty if file was unable to be extracted.
	 */
	public static Map<String, String> getZipRRContents(InputStream zipInputStream) {
		Map<String,String> RRData = new HashMap<String,String>();
		String filename = "";
		try {
			//put below code someplace else. Return a map with key being filename, value being string data.
			ZipInputStream zis = new ZipInputStream(zipInputStream);
			ZipEntry entry = null;
					
			//The getNextEntry places the ZipImputStream at the next entry point in the stream.
			while ((entry = zis.getNextEntry()) != null) {
				filename = entry.getName();
				logger.debug("Filename: " + filename);
				StringBuffer sb = new StringBuffer();

				//if the compression method is not DEFLATED or STORED, we cannot handle it.
				if (entry.getMethod() == ZipEntry.DEFLATED || entry.getMethod() == ZipEntry.STORED) {
					InputStream entryInputStreamPointer = zis;
					Scanner sc = new Scanner(entryInputStreamPointer);
				    while(sc.hasNextLine()) {
				        String s = sc.nextLine();
				        sb.append(s);
				    }
				    RRData.put(filename, sb.toString());
				    logger.debug("File data: "+sb);
				} else {
					logger.error("Attempting to unzip yet the compression method is not supported.");
					break; //don't bother with processing the rest of the file.
				}
			}
			zis.close();
		} catch (Exception e) {
			logger.error("Error extracting data from zipfile file:, "+ filename+ " error: " + e.getMessage());
			//If an error occured, don't return partial data. Return empty map.
			RRData.clear();
		}
		return RRData;
	}
	
	/**
	 * Checks zip input stream for supported compression types.
	 * @param zipInputStream
	 * @return true if supported, false otherwise
	 */
	public static boolean isSupportedCompression(InputStream zipInputStream) {
		boolean returnValue = false;
		try {
			//put below code someplace else. Return a map with key being filename, value being string data.
			ZipInputStream zis = new ZipInputStream(zipInputStream);
			ZipEntry entry = null;
					
			//The getNextEntry places the ZipImputStream at the next entry point in the stream.
			while ((entry = zis.getNextEntry()) != null) {
				//if the compression method is not DEFLATED or STORED, we cannot handle it.
				if (entry.getMethod() == ZipEntry.DEFLATED || entry.getMethod() == ZipEntry.STORED) {
					returnValue = true;
					break;
				}
			}
			zis.close();
		} catch (Exception e) {
			logger.error("Error analyzing data from zipfile file, error: " + e.getMessage());
		}
		return returnValue;
	}
	
	public static Map<String,String> getFilesFromMAR(InputStream zipInputStream) {
		Map<String,String> RRData = new HashMap<String,String>();
		String filename = "";
		try {
			//put below code someplace else. Return a map with key being filename, value being string data.
			ZipInputStream zis = new ZipInputStream(zipInputStream);
			ZipEntry entry = null;
					
			//The getNextEntry places the ZipImputStream at the next entry point in the stream.
			while ((entry = zis.getNextEntry()) != null) {
				filename = entry.getName();
				logger.debug("Filename: " + filename);
				StringBuffer sb = new StringBuffer();

				//if the compression method is not DEFLATED or STORED, we cannot handle it.
				if (entry.getMethod() == ZipEntry.DEFLATED || entry.getMethod() == ZipEntry.STORED) {
					InputStream entryInputStreamPointer = zis;
					Scanner sc = new Scanner(entryInputStreamPointer);
				    while(sc.hasNextLine()) {
				        String s = sc.nextLine();
				        sb.append(s);
				    }
				    if(filename.contains(".vsm") || filename.contains(".vsi") || filename.contains(".config") || filename.contains(".xml")) {
				    	RRData.put(filename, sb.toString());
				    	logger.debug("File data: "+sb);
				    }
				} else {
					logger.error("Attempting to unzip yet the compression method is not supported.");
					break; //don't bother with processing the rest of the file.
				}
			}
			zis.close();
		} catch (Exception e) {
			logger.error("Error extracting data from zipfile file:, "+ filename+ " error: " + e.getMessage());
			//If an error occured, don't return partial data. Return empty map.
			RRData.clear();
		}
		return RRData;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		final File initialFile = new File("C:\\Users\\pam16105\\Downloads\\DependentTuitionWaiver.nitro_s");
	      final InputStream targetStream = 
	        new DataInputStream(new FileInputStream(initialFile));
		
	      Map<String,String> zipContents = ZipHelper.getFilesFromMAR(targetStream);
	      
	      System.out.println("done.");
	}
	
}