package com.practice.oauth2.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {
	

	@GetMapping("/test")
	public String sample() {
		
		return "Oauth2 get sample working !";
	}
	
	
	@PostMapping("/")
	public String samplePost() {
		
		return "create worked!";
	}
	
	
	@DeleteMapping("/")
	public String sampleDelete() {
		
		return "delete worked !";
	}
	
	
	@PutMapping("/")
	public String sampleUpdate() {
		
		return "Update worked !";
	}
	
	
	

}
