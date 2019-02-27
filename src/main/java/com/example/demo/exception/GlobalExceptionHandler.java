package com.example.demo.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Manejador de Excepciones
 * 
 * @author Cristian
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Se lanza cuando ya existe la persona con mismo DNI
	 */
	@ExceptionHandler(PersonExistException.class)
	public ResponseEntity<Object> handlePersonExistException(PersonExistException ex) {
		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Repeated value", HttpStatus.CONFLICT),
				HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * Se lanza cuando JSON es invalido
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new ErrorInfo(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * 
	 * Se lanza cuando la validacion @Valid falla
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Validation error", HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Se lanza cuando el argumento es invalido
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Invalid parameter", HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Se lanza cuando la validacion @Validated falla
	 * 
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {

		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Validation error", HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * Se lanza cuando no encuentra la entidad
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {

		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Not Found", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * Se lanza cuando el request JSON esta malformado
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new ErrorInfo(ex.getMessage(), "Malformed JSON request", HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Excepción Generica
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleDefaultException(Exception ex) {
		ex.printStackTrace();
		System.err.println("Error!, consulte al area de sistemas");
	}

}
