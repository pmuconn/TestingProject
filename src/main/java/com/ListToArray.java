package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListToArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer integer = null;
		
		List list = new ArrayList();
		integer = new Integer(1);
	    list.add(integer);

	    integer = new Integer(2);
	    list.add(integer);

	    integer = new Integer(3);
	    list.add(integer);

	    // Convert a collection to Object[], which can store objects
	    // of any type.
	    Object[] ol = list.toArray();
	    System.out.println("Array of Object has length " + ol.length);
	    for (int i=0;i< ol.length;i++) {
	    	System.out.println(ol[i]);
	    }

	    Integer[] sl = (Integer[]) list.toArray(new Integer[0]);
	    System.out.println("Array of String has length " + sl.length);
	    for (int i=0;i< sl.length;i++) {
	    	System.out.println(sl[i]);
	    }
	    
	    //NO TO THE ABOVE..use something like this:
		//Convert to Array to mirror UPM soap object.
    	//returnValue = (SourcePrescriptionPlan[]) srcPresList.toArray(new SourcePrescriptionPlan[0]);
	    
	    
	    //Now test an array to a List and see if it trims the first element.
	    String[] testArray = new String[10];
	    testArray[1] = "Hello";
	    testArray[2] = "World";
	    
	    List testList = new ArrayList(Arrays.asList(testArray));
	    testList.removeAll(Collections.singleton(null));
	    System.out.println(testList.toString());
	}

}
