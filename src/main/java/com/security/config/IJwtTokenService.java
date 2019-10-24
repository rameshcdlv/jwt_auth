package com.security.config;

import org.springframework.security.core.Authentication;

public interface IJwtTokenService {
	
	boolean validateToken(String token);
	String generateToken(Authentication authentication);
	String getUserNameFromJwt(String token);

}
