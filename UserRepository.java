package com.webapp.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.employee.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

	@Query
	User findByUsername(String username);
	
}
