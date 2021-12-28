package com.boot.controller;

import org.springframework.stereotype.Component;

@Component
public class UserAuto {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
