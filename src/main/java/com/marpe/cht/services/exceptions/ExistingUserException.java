package com.marpe.cht.services.exceptions;

public class ExistingUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExistingUserException(Object obj) {
		super("User already registered in database.");
	}
}
