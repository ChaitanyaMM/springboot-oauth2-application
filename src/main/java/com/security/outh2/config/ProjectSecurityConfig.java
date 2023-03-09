package com.security.outh2.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity

public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		/**
		 * Custom configurations as per our requirement
		 */
		http.cors().configurationSource(new CorsConfigurationSource() { // for enabling cors on paritucalr domain & port
																		// whole app wide
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		})
				// .and().csrf().ignoringAntMatchers("/notices")// to allow the apis which are
				// not sensitive

				.and().csrf().ignoringAntMatchers("/notices")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

//		 .and().csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)).ignoringAntMatchers("/contact").  //XSRF-TOKEN //X-XSRF-TOKEN
//        csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // for validating the csrf token generated from browser 
//		.and().csrf().disable()
				.and().authorizeHttpRequests((auth) -> auth.antMatchers("/api/user").permitAll()
						.antMatchers("/notices", "/customer").permitAll())
				.httpBasic(Customizer.withDefaults());

		http.authenticationProvider(new AppUserNamePasswordAuthenticator());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
