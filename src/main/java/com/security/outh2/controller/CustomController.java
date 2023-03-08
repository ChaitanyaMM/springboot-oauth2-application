package com.security.outh2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.outh2.entity.Customer;
import com.security.outh2.entity.LoginRequest;
import com.security.outh2.entity.LoginResponse;
import com.security.outh2.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/signup")
	public ResponseEntity<Customer> save(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		Customer savedCustomer = customerService.findByEmail(loginRequest.getEmail());
		if (savedCustomer != null) {
			return new ResponseEntity<>(new LoginResponse(), HttpStatus.OK);
		}

		return null;

	}

	@GetMapping("")
	public String check() {
		return "just checking !..........";
	}

}
