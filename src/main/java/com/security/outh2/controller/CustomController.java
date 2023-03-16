package com.security.outh2.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.outh2.config.AppUserNamePasswordAuthenticator;
import com.security.outh2.config.JwtTokenGenerator;
import com.security.outh2.entity.Customer;
import com.security.outh2.entity.LoginRequest;
import com.security.outh2.entity.LoginResponse;
import com.security.outh2.repository.TokenRepository;
import com.security.outh2.response.MessageResponse;
import com.security.outh2.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private AppUserNamePasswordAuthenticator appUserNamePasswordAuthenticator;

	@PostMapping("/signup")
	public ResponseEntity<Customer> save(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
	}

	private LoginResponse saveTokenResponse(String email, String jwtToken, String refreshToken, Date ttl, long id) {
		LoginResponse response = new LoginResponse();
		if (id != 0L) {
			response.setId(id);
		}
		response.setAccessToken(jwtToken);
		response.setActive(true);
		response.setRefreshToken(refreshToken);
		response.setTtl(ttl);
		response.setUserId(customerService.findByEmail(email).getId());
		LoginResponse savedReponse = tokenRepository.save(response);
		return savedReponse;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = appUserNamePasswordAuthenticator.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Customer customer = customerService.findByEmail(authentication.getName());

		LoginResponse response = jwtTokenGenerator.getRefreshToken(customer.getId());
		String jwtToken = jwtTokenGenerator.generateJwtToken(authentication);
		String refreshToken = jwtTokenGenerator.createRefreshToken();
		Date ttl = jwtTokenGenerator.getExpirationDateFromToken(jwtToken);
		LoginResponse savedResponse = null;
		if (response == null) {
			savedResponse = saveTokenResponse(authentication.getName(), jwtToken, refreshToken, ttl, 0L);
		} else {
			savedResponse = saveTokenResponse(authentication.getName(), jwtToken, refreshToken, ttl, response.getId());
		}
		return new ResponseEntity<>(savedResponse, HttpStatus.OK);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = null;
		if (authentication.getName().toString() != "anonymousUser") {
			customer = customerService.findByEmail(authentication.getName());
		}
		jwtTokenGenerator.getCleanJwtAndRefreshToken(customer.getId());
		return new ResponseEntity<>(new MessageResponse("You've been signed out!"), HttpStatus.OK);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		Customer customer = customerService.findByEmail(request.getUserPrincipal().getName());
		LoginResponse response = jwtTokenGenerator.getRefreshToken(customer.getId());

		if ((response != null) && (response.getRefreshToken().length() > 0)) {
			String jwtToken = jwtTokenGenerator.generateJwtToken(securityContext.getAuthentication());
			String refreshToken = jwtTokenGenerator.createRefreshToken();
			Date ttl = jwtTokenGenerator.getExpirationDateFromToken(jwtToken);
			saveTokenResponse(customer.getEmail(), jwtToken, refreshToken, ttl, response.getId());
			return new ResponseEntity<>(new MessageResponse("Token is refreshed successfully!"), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new MessageResponse("Refresh token is not in database!"), HttpStatus.NOT_FOUND);

		}

	}

}
