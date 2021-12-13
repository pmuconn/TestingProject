package com.misc;

public class StartsWithTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a = "Hello There";
		String b = "hello There";
		final String HELLO_LC = "Hello";
		final String HELLO_UC = "HELLO";
		
		if (a.startsWith(HELLO_LC)) {
			System.out.println(a + " starts with " + HELLO_LC);
		}
		if (a.startsWith(HELLO_UC)) {
			System.out.println(a + " starts with " + HELLO_UC);
		}

		System.out.println("Finished the startsWith test.");
	}

}
