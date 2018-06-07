package com.inlinecomparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringPrefixSort {

	public static void main(String[] args) {
		List<String> items = new ArrayList<String>();
		items.add("11-name");
		items.add("02-name");
		items.add("05-name");
		items.add("01-name");
		items.add("12-name");
		items.add("10-name");
		items.add("03-name");
		
		Collections.sort(items);
		
//        Collections.sort(items, new Comparator<String>() {
//            @Override
//            public int compare(String i1, String i2) {
//            	return i1.compareTo(i2);
//            }
//        });
		
        System.out.println(items);
		
	}

}
