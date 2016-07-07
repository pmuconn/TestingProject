package com;

import java.util.ArrayList;
import java.util.List;

public class ArrayContains {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList();
		
		list.add("F15K");
		list.add("0A");
		
		if (list.contains("0A")) {
			System.out.println("list contains 0A");
		}

		if (list.contains("0a")) {
			System.out.println("list contains 0a");
		}
	}

}
