package com.reflect.tests;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

public class CheckEmptyStrings {

	String firstString = "";
	String secondString = "second";

	/**
	 * A test using reflection that checks all the fields on a class to see if any of the strings are empty
	 * If any string is not empty, true is returned.
	 * 
	 * @return
	 */
	public boolean isValid() {
		boolean result = false;
		for (Field field : this.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(String.class)) {
				try {
					String value = (String) field.get(this);
					if (StringUtils.isNotEmpty(value)) {
						result = true;
					}
				}
				catch (IllegalArgumentException ex) {
				}
				catch (IllegalAccessException ex) {
				}
			}
		}
		return result;
	}

public static void main(String[] args) {
	CheckEmptyStrings c = new CheckEmptyStrings();
	System.out.println("class has any filled strings? " + c.isValid());

	c.secondString = "";
	System.out.println("class has any filled strings? " + c.isValid());
	
}
	
}
