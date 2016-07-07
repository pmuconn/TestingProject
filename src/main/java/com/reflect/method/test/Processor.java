package com.reflect.method.test;

public class Processor {

	protected String helloWorld() {
		System.out.println("Hello World");
		return "Success";
	}
	
	public String helloWorld(String name) {
		System.out.println("Hello World " + name);
		return "Success";
	}
}
