/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class SecurityProperties extends BaseProperties {

	@Value("${ssl.port}")
	private String port;

	/**
	 * Defined in a property file as

		#To add another, simply add another row
		#autorizedUsers[key]=value
		autorizedUsers[sampleUser]=samplePassword
		autorizedUsers[sampleUser2]=samplePassword2

	 */
	protected Map<String, String> autorizedUsers = new HashMap<String,String>();
	
    @ConfigurationProperties("autorizedUsers")	
	public Map<String, String> getAutorizedUsers() {
		return autorizedUsers;
	}
	
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
