package com.misc;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String aString = "0.60";
		
		System.out.println(String.valueOf(Double.valueOf(aString)*100));
		
		
        String addrSeqNum = "8";
        if((addrSeqNum != null) && (addrSeqNum.length() < 9)) {
            addrSeqNum = "000000000".substring(0, (9 - addrSeqNum.length())) + addrSeqNum;
        }
        System.out.println("seq: " +addrSeqNum);

        
        //testing a - strip from a date
        String testDate = "2012-02-20";
        //String testDate = "";
        String stripped = testDate.replace("-", "");
        System.out.println("old date: " + testDate + " new date: " + stripped);
        
        //pad value test
        int hours = 9;
        String shours = String.format("%02d", hours);
        System.out.println("string hours: " +shours);
        
        //tested substring
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int max = 5;
        if (str.length() < max) {
        	max = str.length();
        }
        System.out.println("parsed string: " + str.substring(0,max));
        
        
	}

}
