package com.security.outh2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.outh2.entity.LoginResponse;

@Repository
public interface TokenRepository extends CrudRepository<LoginResponse, Long>{

	void deleteByUserId(Long userId);

	LoginResponse findByUserId(Long userId);

}
