package com.practice.exception;

public class ValidationFailureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationFailureException(String message) {
		super(message);
	}

}
