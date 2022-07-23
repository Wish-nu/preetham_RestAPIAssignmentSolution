package com.webapp.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository <Employee, Long>{
	
	List<Employee> findByFirstName(String firstName);
	
	List<Employee> findByOrderByFirstNameAsc();
	
	List<Employee> findByOrderByFirstNameDesc();
	
}
