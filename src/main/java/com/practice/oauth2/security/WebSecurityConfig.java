package com.practice.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.practice.oauth2.entity.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/","index","/").permitAll() //allwoing default
		.antMatchers("/api/**").hasAnyRole(Roles.ADMIN.name()) //allowing /api path to restricted roles only
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
	
	
	@Override
	protected UserDetailsService userDetailsService() {   //In MemoryDatabase
		UserDetails sampleUser = User.builder()
				.username("chy")
				.password(passwordEncoder.encode("password"))
				.roles(Roles.SAMPLE.name()) //ROLE_SAMPLE
				.build();
		
		UserDetails sampleUser2 = User.builder()
				.username("chy02")
				.password(passwordEncoder.encode("password123"))
				.roles(Roles.ADMIN.name()) //ROLE_ADMIN
				.build();
		
		UserDetails sampleUser3 = User.builder()
				.username("xyz")
				.password(passwordEncoder.encode("password123"))
				.roles(Roles.XYZ.name()) //ROLE_XYZ
				.build();
		
		
		return new InMemoryUserDetailsManager(sampleUser,sampleUser2,sampleUser3);
		
	}

}
