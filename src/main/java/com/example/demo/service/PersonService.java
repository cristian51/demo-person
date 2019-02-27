package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Person;
import com.example.demo.exception.PersonExistException;

@Service
public interface PersonService {

	public Person addPerson(Long personId, Person p) throws PersonExistException;

	public List<Person> getPersons();
}
