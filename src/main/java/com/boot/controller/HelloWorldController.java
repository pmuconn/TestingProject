package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	//Testing autowire from bean from xml 
	@Autowired
	@Qualifier("myTestUser")
	private User myTestUser;
	
	@RequestMapping("/helloworld")
    String hello() {
		
		System.out.println("here");
		
		myTestUser.checkAutowired();
		
        return "Hello World, Spring Boot!";
    }
}
