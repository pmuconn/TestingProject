package com.misc;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class TestListIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = Arrays.asList("zero", "one", "two");

		ListIterator iter = list.listIterator();
		
		while (iter.hasNext()) {
			System.out.println("index: " + iter.nextIndex() + " value: " + iter.next());
		//	System.out.println("value: " + iter.next() + " index: " + iter.nextIndex());
		}
	}

}
