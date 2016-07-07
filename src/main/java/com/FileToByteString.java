package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class FileToByteString {

	public static String getEncodedString(String filename) {
		String result = "";
		try {
			File file = new File(filename);
			DataSource source = new FileDataSource(file);
			DataHandler dh = new DataHandler(source);
			source.getInputStream();
		    String stringToWrite = IOUtils.toString(dh.getInputStream(), "UTF-8");
		    byte[] encode = Base64.encodeBase64(IOUtils.toByteArray(dh.getInputStream()));
		    IOUtils.toByteArray(dh.getInputStream());
		    String ns = new String(encode);		    
//		    System.out.println(ns);
			result = ns;
		} catch (Exception e) {
			
		}
		return result;
	}

	public static byte[] getEncodedByte(String filename) {
		byte[] result = null;
		try {
			File file = new File(filename);
			DataSource source = new FileDataSource(file);
			DataHandler dh = new DataHandler(source);
			source.getInputStream();
		    String stringToWrite = IOUtils.toString(dh.getInputStream(), "UTF-8");
//		    System.out.println(stringToWrite);
		    byte[] encode = Base64.encodeBase64(IOUtils.toByteArray(dh.getInputStream()));
		    System.out.println("encoded byte size: " + encode.length);
		    result = encode;
		} catch (Exception e) {
		}
		return result;
	}

	public static void main(String[] args) {
		
	//	File file = new File("C:\\Users\\pam16105\\Downloads\\ParkingLotMap-8.05.15-11x172.pdf");
	//	File file = new File("C:\\paul\\test.txt");
	//	File file = new File("C:\\paul\\testdoc.docx");
		File file = new File("C:\\paul\\test1meg.zip");
		
	//File file = new File("C:\\paul\\test.zip");
		
		try {
			//encode
			DataSource source = new FileDataSource(file);
			DataHandler dh = new DataHandler(source);
			source.getInputStream();
		    String stringToWrite = IOUtils.toString(dh.getInputStream(), "UTF-8");
		    byte[] encode = Base64.encodeBase64(IOUtils.toByteArray(dh.getInputStream()));
		    IOUtils.toByteArray(dh.getInputStream());
		    System.out.println("encoded byte array size: " + encode.length);
		    String ns = new String(encode);
		    System.out.println("encoded byte array STRING size: " + ns.length());
	
		    //display the encoded string
//		    System.out.println(ns);
		    
		    //decode
		    byte[] in = Base64.decodeBase64(ns);
		    System.out.println("decoded byte array size: " + in.length);
		    
		    System.out.println();
//		    ByteArrayInputStream bais = new ByteArrayInputStream(in);
		    
		    DataSource inSource = new ByteArrayDataSource(in,"application/octet-stream");
		    DataHandler dh1 = new DataHandler(inSource);
		    
            InputStream is = (InputStream)dh1.getContent();
            int size = is.available();
		    System.out.println("decoded input stream size: " + size);
            
//            String mimeType = URLConnection.guessContentTypeFromStream(bais);
//            System.out.println(mimeType);
            
            String mimeType2 = URLConnection.guessContentTypeFromName(file.getName());
            System.out.println(mimeType2);
            
//            MimetypesFileTypeMap mt = new MimetypesFileTypeMap();
//            String mimeType3 = mt.getContentType("test.doc");
//            String ext = FilenameUtils.getExtension("test.doc");            
//            System.out.println(ext + " is " +mimeType3);
            
            String out = "c:\\paul\\testout.zip";
            IOUtils.copy(is, new FileOutputStream(out));

		    System.out.println(dh1.getContentType() + " " + size);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //convert your DataHandler to String
		
	}
	
}
