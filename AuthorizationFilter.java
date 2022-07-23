package com.webapp.employee.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.webapp.employee.model.Roles;
import com.webapp.employee.model.User;
import com.webapp.employee.repository.UserRepository;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	private UserRepository userRepository;

	public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {

			User user = userRepository.findByUsername(token.split(" ")[1]);
			if (user != null) {
				List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
				for (Roles role : user.getRoles()) {
					grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
				}
				return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
						grantedAuthorities);
			} else
				return null;
		}
		return null;

	}

}
