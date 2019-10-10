package com.spring.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-service.xml");

		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		
		obj.setMessage("blah");
		obj.getMessage();
		
		//try using the application context provider
		
		HelloWorld obj2 = (HelloWorld) ApplicationContextProvider.getApplicationContext().getBean("helloWorld");
		obj.setMessage("blah using context provider.");
		obj.getMessage();
	}

}
