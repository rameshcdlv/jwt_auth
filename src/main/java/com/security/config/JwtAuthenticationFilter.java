package com.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.UserService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private IJwtTokenService tokenService;
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String bearerToken =getBearerToken(request);
		if(StringUtils.hasText(bearerToken) && tokenService.validateToken(bearerToken)) {
			
			String userName=tokenService.getUserNameFromJwt(bearerToken);
			UserDetails loadUserByUsername = userService.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loadUserByUsername, null, loadUserByUsername.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);
		}

	}

	private String getBearerToken(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
			return token.substring(7);
		}
		return null;
	}

}
