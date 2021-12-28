package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class User {
	private String name;

	//testing autowire inside an xml bean
	@Autowired
	private UserAuto userauto;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void checkAutowired() {
		userauto.setName("autowired");
	}
	
}
