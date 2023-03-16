package com.security.outh2.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "token_response")
public class LoginResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String accessToken;
	private String refreshToken;
	private Long userId;
	private Date ttl;
	private boolean isActive;

}
