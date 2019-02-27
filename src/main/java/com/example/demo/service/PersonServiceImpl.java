package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Person;
import com.example.demo.exception.PersonExistException;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepo;

	@Override
	public Person addPerson(Long idPerson, Person p) {
		if (this.idValidation(idPerson)) {
			p.setDni(idPerson);
			return personRepo.save(p);
		} else {
			throw new PersonExistException("Already exist a person with dni: " + idPerson.toString());
		}
	}

	private boolean idValidation(Long idPerson) {
		return (idPerson != null && personRepo.findByDni(idPerson) == null);

	}

	@Override
	public List<Person> getPersons() {

		return personRepo.findAll();
	}

}
