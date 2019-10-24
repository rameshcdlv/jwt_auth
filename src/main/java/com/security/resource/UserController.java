package com.security.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.config.JwtTokenService;
import com.security.dto.UserPayload;
import com.security.model.User;
import com.security.model.UserRole;
import com.security.repo.UserRepo;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenService tokenService;

	@GetMapping("/status")
	public ResponseEntity<String> health() {
		return new ResponseEntity<>("Alive", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserPayload payload) {

		User user = new User();
		user.setUserName(payload.getName());
		user.setPassword(passwordEncoder.encode(payload.getPassword()));
		user.setEmail(payload.getEmail());
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		if (payload.isAdmin()) {
			userRole.setUserRole("ROLE_ADMIN");

		} else {
			userRole.setUserRole("ROLE_USER");
		}
		userRoles.add(userRole);
		user.setUserRoles(userRoles);
		userRepo.save(user);
		return new ResponseEntity<String>("User Registered", HttpStatus.CREATED);
	}

	@PostMapping("/token")
	public ResponseEntity<?> getAccessTOken(@RequestBody UserPayload loginPayload){
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginPayload.getName(), loginPayload.getPassword()));
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>(tokenService.generateToken(authentication),HttpStatus.OK);
	}
}
