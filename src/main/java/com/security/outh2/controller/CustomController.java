package com.security.outh2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.outh2.config.JwtTokenGenerator;
import com.security.outh2.entity.Customer;
import com.security.outh2.entity.LoginRequest;
import com.security.outh2.entity.LoginResponse;
import com.security.outh2.service.CustomerService;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;

	@PostMapping("/signup")
	public ResponseEntity<Customer> save(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		Customer savedCustomer = customerService.findByEmail(loginRequest.getEmail());
		if (savedCustomer != null) {
			String pwd = passwordEncoder.encode(savedCustomer.getPassword());
			if (!pwd.equalsIgnoreCase(loginRequest.getPassword())) {
				return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			log.info("login successful !.");

			jwtTokenGenerator.generateToken(savedCustomer);
			return new ResponseEntity<>(new LoginResponse(), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@GetMapping("")
	public String check() {
		return "just checking !..........";
	}

}
