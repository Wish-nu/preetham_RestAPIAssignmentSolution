package com.webapp.employee.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotEmpty
	@Size(min = 5, max = 250)
	private String username;

	@NotEmpty
    @Size(min = 5, max = 250)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_has_roles",
        joinColumns = {
        		@JoinColumn(name = "users_id", referencedColumnName = "id")
        		},
        inverseJoinColumns = {
        		@JoinColumn(name = "roles_id", referencedColumnName  = "id")
        		}
        )
	@NotEmpty
    private Set<Roles> roles = new LinkedHashSet<Roles>();
	

}
