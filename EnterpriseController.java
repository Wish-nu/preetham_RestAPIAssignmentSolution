package com.webapp.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapp.employee.model.Employee;
import com.webapp.employee.model.Response;
import com.webapp.employee.model.Roles;
import com.webapp.employee.model.User;
import com.webapp.employee.service.EnterpriseService;

@Controller
@RequestMapping("/api/")
@CrossOrigin
public class EnterpriseController {

	@Autowired
	private EnterpriseService service;

	@PostMapping("/employees")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		Employee dbEmp = service.addEmployee(employee);
		return new ResponseEntity<Employee>(dbEmp, HttpStatus.CREATED);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		Employee dbEmp = service.getEmployeeById(id);
		if (dbEmp != null) {
			dbEmp.setFirstName(employee.getFirstName());
			dbEmp.setLastName(employee.getLastName());
			dbEmp.setEmail(employee.getEmail());
		}

		service.addEmployee(dbEmp);
		return new ResponseEntity<Employee>(dbEmp, HttpStatus.OK);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Response> deleteEmployee(@PathVariable long id) {
		Response response = new Response();
		service.deleteEmployee(id);
		response.setMessage("Deleted employee id - " + id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee emp = service.getEmployeeById(id);
		if (emp != null)
			return new ResponseEntity<Employee>(emp, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/employees/search/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeeById(@PathVariable String firstName) {
		List<Employee> empList = service.getEmployeeByFirstName(firstName);
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}
	
	@GetMapping("/employees/sort")
	public ResponseEntity<List<Employee>> getSortEmployees(@RequestParam String order) {
		List<Employee> empList = service.getSortEmployeeByFirstName(order);
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<>(service.getEmployees(), HttpStatus.OK);
	}

	@PostMapping("/role")
	public ResponseEntity<Roles> saveRole(@RequestBody Roles roles) {
		service.saveRoles(roles);
		return new ResponseEntity<>(roles, HttpStatus.CREATED);
	}

	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		Response response = new Response();
		if (!service.findUser(user)) {
			service.registerUser(user);
			response.setMessage("user saved");
			return new ResponseEntity<Object>(user, HttpStatus.CREATED);
		} else {
			response.setMessage("An account already exists for this username.");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
