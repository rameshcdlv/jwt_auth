package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);

}
