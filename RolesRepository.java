package com.webapp.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.employee.model.Roles;

public interface RolesRepository extends JpaRepository <Roles, Long>{
	
}
