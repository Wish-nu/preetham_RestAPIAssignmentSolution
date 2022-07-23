package com.webapp.employee.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.webapp.employee.repository.UserRepository;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserRepository userRepository;

	public WebSecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserRepository userRepository) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/h2/**")
		.antMatchers("/api/role")
		.antMatchers("/api/user");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic()
		.and().csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.antMatchers("/api/employees/**").hasAuthority("ADMIN")
		.antMatchers("/api/employees").hasAuthority("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.addFilter(new AuthenticationFilter(authenticationManager(), userRepository))
		.addFilter(new AuthorizationFilter(authenticationManager(), userRepository))
		.exceptionHandling()
		.authenticationEntryPoint(new CustomEntryPoint())
		.accessDeniedHandler(new CustomAccessDeniedHandler());
	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

}
