package com.security.config;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.security.model.UserPrinipal;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService implements IJwtTokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Override
	public boolean validateToken(String token) {

		Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		return true;
	}

	@Override
	public String getUserNameFromJwt(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	@Override
	public String generateToken(Authentication authentication) {

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.DAY_OF_MONTH, 1);
		UserPrinipal userPrinipal = (UserPrinipal) authentication.getPrincipal();

		return Jwts.builder().
				setExpiration(now.getTime())
				.signWith(SignatureAlgorithm.HS512, secret)
				.setIssuedAt(new Date())
				.setSubject(userPrinipal.getUsername())
				.compact();
	}

}
