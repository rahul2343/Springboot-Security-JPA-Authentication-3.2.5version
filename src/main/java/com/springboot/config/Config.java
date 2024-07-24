package com.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.springboot.service.CustomUserDetailService;

@Configuration
public class Config {

	@Autowired
	CustomUserDetailService customUserDetailService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User
				.withUsername("Poonam")
				.password(passwordEncoder().encode("123456"))
				.roles("NORMAL")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	*/

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		System.out.println("AuthenticationManager");
		return configuration.getAuthenticationManager();

	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;

	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/public/**")
			.permitAll()
			.requestMatchers("/signin").permitAll()
			.anyRequest().
			authenticated()
			.and()
			.formLogin()
			.loginPage("/signin").
			loginProcessingUrl("/doLogin").
			defaultSuccessUrl("/users/")
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/signin?logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
			//.userDetailsService(userDetailsService());
			.authenticationProvider(daoAuthenticationProvider());
		
		return http.build();

	}

}
