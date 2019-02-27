package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Clase que representa la Persona
 * 
 * @author Cristian
 *
 */
@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = -7850366009387160931L;

	@Id
	@Column(name = "Dni")
	private Long dni;

	@Column(name = "Name")
	@NotBlank
	@Size(min = 2, max = 15)
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String name;

	@Column(name = "SurName")
	@NotBlank
	@Size(min = 2, max = 15)
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String surname;

	@Column(name = "Age")
	@Max(100)
	@PositiveOrZero
	private Integer age;

	@Override
	public String toString() {
		return new StringBuilder().append("Person [dni=").append(dni).append(" - name=").append(name)
				.append(" - surname=").append(surname).append(" - age=").append(age).append("]").toString();
	}

	// Getters and setters

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}