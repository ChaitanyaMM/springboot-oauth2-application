package com.security.outh2.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.security.outh2.entity.Customer;
import com.security.outh2.entity.LoginResponse;
import com.security.outh2.repository.CustomerRepository;
import com.security.outh2.repository.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenGenerator implements Serializable {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private static String jwtSecret = "jwtsecretKey";

	private static int jwtExpirationMs = 86400000;

	private static final long serialVersionUID = -6914680628799247127L;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateJwtToken(Authentication authentication) {
		Customer userDetails = customerRepository.findByEmail(authentication.getName()).get(0);

		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getName());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	@Transactional
	public void getCleanJwtAndRefreshToken(Long userId) {
		tokenRepository.deleteByUserId(userId);
	}

	public String createRefreshToken() {
		return UUID.randomUUID().toString();
	}

	public LoginResponse getRefreshToken(long userId) {
		return tokenRepository.findByUserId(userId);
	}

//
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(JwtConstants.SIGNING_KEY).parseClaimsJws(token).getBody();
//	}
//
//	private Boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}
//
//	@SuppressWarnings("deprecation")
//	public String generateJwtToken(Authentication authentication) {
//
//		Customer userPrincipal = customerRepository.findByEmail(authentication.getName()).get(0);
//		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//		return Jwts.builder().setSubject(userPrincipal.getName()).setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//	}
//
//	public String getUserNameFromJwtToken(String token) {
//		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//	}
//
//	public boolean validateJwtToken(String authToken) {
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//			return true;
//		} catch (MalformedJwtException e) {
//			log.error("Invalid JWT token: {}", e.getMessage());
//		} catch (ExpiredJwtException e) {
//			log.error("JWT token is expired: {}", e.getMessage());
//		} catch (UnsupportedJwtException e) {
//			log.error("JWT token is unsupported: {}", e.getMessage());
//		} catch (IllegalArgumentException e) {
//			log.error("JWT claims string is empty: {}", e.getMessage());
//		}
//
//		return false;
//	}
//
//	public Boolean validateToken(String token, UserDetails userDetails) {
//		final String username = getUsernameFromToken(token);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//

}
