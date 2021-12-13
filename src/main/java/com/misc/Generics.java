package com.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Generics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//test ? value
		Map <String,List<?>> testMap = new HashMap();
		List testList1 = new ArrayList<String>();
		testList1.add("String");
		testMap.put("key1", new ArrayList());
		testMap.put("key2", new LinkedList());

		//test new looping with generics
		List<String> stringList = new ArrayList();
		stringList.add("String1");
		stringList.add("String2");
		
		//for loop. No iterator since we don't need iterator methods.
		for (String s : stringList) {
		    System.out.println(s);
		}
		
		//for loop with an iterator, so we can use the iterator methods.
		for (Iterator<String> it = stringList.iterator(); it.hasNext(); ) {
		    String s = it.next();  // No downcasting required.
		    System.out.println(s);
		}

		//while loop. Not as neat as the for loops now.
	    Iterator<String> i = stringList.iterator();
	    while(i.hasNext()) {
	      String item = i.next(); //no downcasting
	      System.out.println("Item: "+item);
	    }
		
	    //for loop over map
	    Map<String,String> newMap = new HashMap();
	    newMap.put("Hello", "World");
	    newMap.put("Bye", "World");
	    for (Map.Entry<String,String> entry : newMap.entrySet()) {
	        String key = entry.getKey();
	        String value = entry.getValue();
	        //do stuff here
	        System.out.println("Key: " + key + " Value: " + value);
	    }
	    for (String key : newMap.keySet()) {
	        System.out.println("Key: " + key );	    	
	    }
	    
	    //test new for loop with non-generic list
	    //nope, doesnt work.
//	   List nonGenList = new ArrayList();
//	   nonGenList.add("1");
//	   nonGenList.add("2");
//	   nonGenList.add("3");
//	   for (String s : nonGenList) {
//		    System.out.println(s);
//	   }
	}

}
