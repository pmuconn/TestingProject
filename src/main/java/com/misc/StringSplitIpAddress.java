package com.misc;

public class StringSplitIpAddress {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String ip = "apsep1763.ms.ds.uhc.com";
		//String ip = "apsep1763";
		String ipp1 = ip.substring(0, ip.indexOf('.',ip.indexOf('.') + 1) + 1);
		
		int index = ip.indexOf('.') == -1 ? ip.length() : ip.indexOf('.');
		String ipp2 = ip.substring(0, index);
		
		System.out.println("new ip: " + ipp2);
	}

}
