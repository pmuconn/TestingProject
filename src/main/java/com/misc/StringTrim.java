package com.misc;

public class StringTrim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "/mng-rest-web/services/rest/";
		
		if (path.startsWith("/")) {
			String path1 = path.substring(1, path.length());
			System.out.println(path1);
		}

		if (path.endsWith("/")) {
			String path1 = path.substring(0, path.length()-1);
			System.out.println(path1);
		}
		
		
		String line = "10 QS26-MDCR-COB-SW                 PIC X VALUE 'N'.";
		int idxv = line.indexOf("VALUE");
		String nline = line.substring(0, idxv);
		System.out.println(nline);
		String nnline = nline.trim() + ".";
		System.out.println(nnline);
		
		
	}

}
