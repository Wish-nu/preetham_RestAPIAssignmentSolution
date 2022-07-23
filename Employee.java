package com.webapp.employee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id =1;
	
	@NotEmpty
    @Size(min = 5, max = 250)
	private String firstName;
	
	@NotEmpty
    @Size(min = 5, max = 250)
	private String lastName;
	
	@Email
	@NotEmpty
	private String email;
	

}
