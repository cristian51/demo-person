package com.example.demo.exception;

public class PersonExistException extends RuntimeException {

	private static final long serialVersionUID = -4831427885585521508L;

	public PersonExistException(String dni) {
		super(dni);
	}
}
