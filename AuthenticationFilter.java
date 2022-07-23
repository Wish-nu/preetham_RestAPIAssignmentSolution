package com.webapp.employee.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.employee.model.Roles;
import com.webapp.employee.repository.UserRepository;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;

	public AuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws InternalAuthenticationServiceException {
		try {
			com.webapp.employee.model.User creds = new ObjectMapper().readValue(request.getInputStream(),
					com.webapp.employee.model.User.class);
			com.webapp.employee.model.User dbUser = userRepository.findByUsername(creds.getUsername());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (Roles role : dbUser.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), grantedAuthorities));
		} catch (IOException e) {
			throw new RuntimeException("Could not read request" + e);
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) {
		String user = ((User) authentication.getPrincipal()).getUsername();
		com.webapp.employee.model.User dbUser = userRepository.findByUsername(user);
		response.addHeader("Authorization", "Bearer " + dbUser.getUsername());
	}

}
