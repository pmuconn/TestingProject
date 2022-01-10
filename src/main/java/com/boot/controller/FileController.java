package com.boot.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
	
	
	@PostMapping("test/validateFile")
	public ResponseEntity<String[]> uploadFile(@RequestPart("file") MultipartFile file) {
		if (null == file.getOriginalFilename()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			
			String content = new String(file.getBytes(), StandardCharsets.UTF_8);			
			
			
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);
			System.out.println(path.getFileName());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		List<String> messages = new ArrayList<String>();
		
		messages.add("Good Job");
		messages.add("Another pat on the back");
		
		//response string array.
		String[] array = messages.toArray(new String[0]);
		
		
		return new ResponseEntity<>(array, HttpStatus.OK);
	}	
	

}
