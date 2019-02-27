package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Se utiliza para mostrar los errores del la aplicación
 * 
 * @author Cristian
 *
 */
public class ErrorInfo {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String details;
	private HttpStatus status;

	public ErrorInfo() {
		timestamp = LocalDateTime.now();
	}

	public ErrorInfo(String message, String details, HttpStatus status) {
		this();
		this.message = message;
		this.details = details;
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
