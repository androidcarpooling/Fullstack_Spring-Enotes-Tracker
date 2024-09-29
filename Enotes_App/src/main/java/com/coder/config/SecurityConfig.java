package com.coder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
        // htttp://localhost:8080/register ,sigin 
        //htttp://localhost:8080/user/addNotes , viewNotes
		/*
		 * disable csrf,if url is like /user/ then checlk for if role is user, anything
		 * else is allowed, for login mapping will be /signin as defined and page will
		 * be userLogin,after success direct to addNotes
		 */
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests -> requests.requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/**").permitAll())
                .formLogin(login -> login.loginPage("/signin")
                        .loginProcessingUrl("/userLogin")
                        .defaultSuccessUrl("/user/addNotes").permitAll());
	return	http.build();
		
	}

}