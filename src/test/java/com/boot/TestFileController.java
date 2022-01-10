package com.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFileController {
	
	@Autowired
	Environment environment;
	
	@LocalServerPort
    int serverPort;
	
	TestRestTemplate restTemplate;
    URL base;
    
    @Value("classpath:gl_collectorflatfile_kfs_SAE_20211228-09-16-41-680.data")
    Resource resourceFile;

    @Before
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate();
        base = new URL("http://localhost:" + serverPort + "/testingproject/test/validateFile");
    }    
    
    @Test
	public void testFileUpload() throws IllegalStateException, IOException {

    	MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
    	parts.add("file", resourceFile);

    	//If reponse entity was just a String.
    	//ResponseEntity<String> response = restTemplate.postForEntity(base.toString(), parts, String.class);

    	//using list reponse.
    	ResponseEntity<String[]> response = restTemplate.postForEntity(base.toString(), parts, String[].class);

    	
		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertTrue(response.getBody().contains("188682"));
	}
    

}
