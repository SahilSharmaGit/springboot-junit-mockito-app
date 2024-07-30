package com.practice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.exception.NoEmployeesFoundException;
import com.practice.exception.ValidationFailureException;

@RestControllerAdvice
public class ExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(ValidationFailureException.class)
	public ResponseEntity<String> handleValidationFailureException(ValidationFailureException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(NoEmployeesFoundException.class)
	public ResponseEntity<String> handleNoEmployeesFoundException(NoEmployeesFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// You can add more exception handlers here

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return new ResponseEntity<>("Somthing went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
