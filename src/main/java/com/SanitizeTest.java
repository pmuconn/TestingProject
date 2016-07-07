package com;


public class SanitizeTest {

	public static void main(String[] args) {
		System.out.println("Start...");
		
		String emailreg = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		
		String email = "@aa.com";
		String semail = "";
//		semail = SanitizeTest.sanitize(email, "");
//		semail = SanitizeTest.sanitize(email, "[^\\w\\s]+");
//		semail = SanitizeTest.sanitize(email, "[\\r\\n]");
		semail = SanitizeTest.sanitize(email, "[\\r\\n\\s]");
//		semail = SanitizeTest.sanitize(email, emailreg);
//		semail = SanitizeTest.sanitize(email, "[\\r\\n]+\\w\\s");
		
		
		
		System.out.println(semail);
		System.out.println("End...");
	}

	public static String sanitize(String inStringToSanitize, String inExpression) {
		String returnValue = "";
		String defaultExpression = "[^A-Za-z0-9]";
		if (!isNullOrEmpty(inStringToSanitize)) {
			inExpression = isNullOrEmpty(inExpression) ? defaultExpression : inExpression;
			returnValue = inStringToSanitize.replaceAll(inExpression, "");
		}
		return returnValue;
	}	
	
	public static boolean isNullOrEmpty(String inString) {
		boolean returnValue = false;
		if (inString == null) {
			returnValue = true;
		} else {
			inString = inString.trim();
			returnValue = inString.isEmpty();
		}
		return returnValue;
	}
	
}
