package com.webapp.employee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    @Column
    private String name;
    
}
