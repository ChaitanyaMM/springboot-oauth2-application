package com.security.outh2.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.authorizeRequests().anyRequest().permitAll()
//		.and().formLogin().and().httpBasic();
//
//	}

//before spring 5.7

@Configuration
public class ProjectSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		/**
		 * Custom configurations as per our requirement
		 */
		http.authorizeHttpRequests((auth) -> auth.antMatchers("/api/user", "/myBalance", "/myLoans", "/myCards")
				.authenticated().antMatchers("/notices", "/contact").permitAll()).httpBasic(Customizer.withDefaults());
		return http.build();
	}

	// Approach 1 where we use withDefaultPasswordEncoder() method
	// while creating the user details
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails admin = User.withDefaultPasswordEncoder().username("chaitanya").password("12345")
//				.authorities("admin").build();
//		UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("12345").authorities("admin")
//				.build();
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	// Approach 2 where we don't define password encoder
	// while creating the user details. Instead a separate
	// PasswordEncoder bean will be created.
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails admin = User.withUsername("chaitanya").password("12345").authorities("admin").build();
		UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
		userDetailsService.createUser(admin);
		userDetailsService.createUser(user);
		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
