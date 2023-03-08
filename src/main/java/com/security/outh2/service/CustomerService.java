package com.security.outh2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.security.outh2.entity.Customer;
import com.security.outh2.repository.CustomerRepository;

@Component
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Customer saveCustomer(Customer customer) {
		Customer encrypte = customer;
		encrypte.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepo.save(encrypte);
	}


	public Customer findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
