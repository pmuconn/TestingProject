package com;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternTest {

	public static void main(String[] args) {
		//old email address pattern
		String pattern0 = "[a-zA-Z0-9_.\\-']*[a-zA-Z0-9]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}";
		
		//new email address pattern
		String pattern1 = "[A-Za-z0-9]+([_.\\-][A-Za-z0-9]+)*@([A-Za-z0-9][A-Za-z0-9_-]*\\.)+[A-Za-z]{2,}";
		
		String pattern = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[A-Za-z0-9-]*[A-Za-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		
		//email tests
		List<String> emailList = new ArrayList<String>();

		//These should match
		emailList.add("cas@paul.build");
		emailList.add("ww5@a.b.c.org");
		emailList.add("something.else@a2.com");
		emailList.add("something_else@something.else.com");
		emailList.add("something-else@et-tu.com");
		emailList.add("dmerkal@gmail.com");
		emailList.add("m.modi@gmail.com");
		emailList.add("1@a.b.c.org");
		emailList.add("p@p.build");
		emailList.add("_@a.org");
		emailList.add("something@a.o-rg");
		emailList.add("leigh.st.__onge@paul.com");
		emailList.add("John._Doe@Example.com");
		emailList.add("\"john..doe\"@example.org");
		emailList.add("maureen.a.o'connor@paul.com");
		emailList.add("SPSPV@admin.paul.com");
		
		//just spacer
		emailList.add("************************");
		
		//These don't match.
		emailList.add("");
		emailList.add("a");
		emailList.add("@a.b.c.org");
		emailList.add("1@org");
		emailList.add("1@a");
		emailList.add(".@a.org");
		emailList.add("john..doe@example.org");
		emailList.add("this\\ still\\\"not\\\\allowed@example.com");
		
		Pattern p = Pattern.compile(pattern);
		for (String email : emailList) {
			if (RegexPatternTest.isValidPattern(p,email)) {
				System.out.println(email + " valid (YES)");
			} else {
				System.out.println(email +" not valid (NO)");
			}
		}
	}


	/**
	 * This method checks whether the input string is of predefined valid format
	 * 
	 * @param argPattern The pattern to be checked with
	 * @param argValue The element value to be checked for
	 * @return boolean
	 */
	public static boolean isValidPattern(Pattern argPattern, String argValue) {
		boolean result = false;
		Matcher matcher = null;
		if (argPattern == null) {
		}
		if (argValue == null) {
		}
		matcher = argPattern.matcher(argValue);
		result = matcher.matches();
		return result;
	}
}
