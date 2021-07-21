package com.practice.oauth2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.oauth2.entity.User;
import com.practice.oauth2.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {

		return userRepository.save(user);
	}

	public Optional<User> findById(Long userId) {

		return userRepository.findById(userId);
	}

}
