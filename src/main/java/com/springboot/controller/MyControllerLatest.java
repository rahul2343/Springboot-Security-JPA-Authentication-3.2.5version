package com.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyControllerLatest {

	@GetMapping("/home")
	public ResponseEntity<String> home(){
		System.out.println("This is home");
		return ResponseEntity.ok("This is home");
	}
	
}
