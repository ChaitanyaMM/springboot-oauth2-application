package com.security.outh2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.outh2.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByEmail(String username);

}
