package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Person;
import com.example.demo.exception.PersonExistException;
import com.example.demo.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping("/persona/{id_persona}")
	public Person addPersons(@PathVariable(value = "id_persona") Long personId, @RequestBody @Valid Person p)
			throws PersonExistException {

		return personService.addPerson(personId, p);

	}

	@GetMapping("/personas")
	public List<Person> getAllPersons() {
		return personService.getPersons();

	}

}