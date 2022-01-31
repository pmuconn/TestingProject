package com.boot.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BatchLogTester {
	
	private final Logger logger = LoggerFactory.getLogger(BatchLogTester.class.getName());

	private String helloworld = "hello world";

	public String getHelloworld() {
		logger.info("Getting hello world value, " + helloworld);
		return helloworld;
	}

	public void setHelloworld(String helloworld) {
		this.helloworld = helloworld;
	}
	
	
}

