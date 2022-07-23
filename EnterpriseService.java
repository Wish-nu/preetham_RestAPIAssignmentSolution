package com.webapp.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.employee.exception.GeneralException;
import com.webapp.employee.model.Employee;
import com.webapp.employee.model.Roles;
import com.webapp.employee.model.User;
import com.webapp.employee.repository.EmployeeRepository;
import com.webapp.employee.repository.RolesRepository;
import com.webapp.employee.repository.UserRepository;

@Service
@Transactional
public class EnterpriseService {

	@Autowired
	private EmployeeRepository empRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	public List<Employee> getEmployees() {
		return empRepository.findAll();
	}

	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = empRepository.findById(id);
		if(optional.isPresent()) return optional.get();
		else return null;

	}

	public Employee addEmployee(Employee employee) {
		return empRepository.save(employee);

	}

	public void deleteEmployee(long id) {
		this.empRepository.delete(getEmployeeById(id));

	}

	public List<Roles> getRoles() {
		return rolesRepository.findAll();
	}

	public void saveRoles(Roles roles) {
		rolesRepository.save(roles);
	}

	public boolean findUser(User user) {
		User userDB = this.userRepository.findByUsername(user.getUsername());
		return userDB != null ? true : false;
	}

	public void registerUser(User user) {
		try {
			user.setPassword(encoder.encode(user.getPassword()));
			this.userRepository.save(user);
		} catch (Exception e) {
			new GeneralException(e.getMessage());
		}
	}

	public List<Employee> getEmployeeByFirstName(String firstName) {
		return empRepository.findByFirstName(firstName);
	}

	public List<Employee> getSortEmployeeByFirstName(String order) {
		if(order.equals("asc")) return empRepository.findByOrderByFirstNameAsc();
		else if(order.equals("desc")) return empRepository.findByOrderByFirstNameDesc();
		return new ArrayList<Employee>();
	}

}
