package com.security.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2418592872706739175L;
	private int id;
	private String userName;
	private String password;
	List<GrantedAuthority> authorities;

	public UserPrinipal() {
	}

	public UserPrinipal(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.authorities = Collections.unmodifiableList(getRoleList(user));
	}

	private List<GrantedAuthority> getRoleList(User user) {
		List<GrantedAuthority> autorities = new ArrayList<>();
		user.getUserRoles().forEach(role -> autorities.add(new SimpleGrantedAuthority(role.getUserRole())));
		return autorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {

		return this.password;
	}

	@Override
	public String getUsername() {

		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
