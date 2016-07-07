package com.staticTest;

import java.util.HashMap;
import java.util.Map;

public class MyConstants {

	private static Map dataMap = new HashMap();
	
	static {
		System.out.println("Entered static block.");
		dataMap.put("Hello",new Integer(1));
		dataMap.put("World",new Integer(2));
	}
	
	public static int getValue(String key) {
		int value = 0;
		
		if (dataMap.containsKey(key)) {
			value = (Integer)dataMap.get(key);
		}
		
		return value;
	}
}
