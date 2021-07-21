package com.practice.oauth2.entity;

import java.util.List;

public class AuthToken { 
	
	private Long  id;
	private String authToken;
	private String expiry;
	private String refreshToken;
	private List<String> hasRoles;
	private String scope;
	

}
