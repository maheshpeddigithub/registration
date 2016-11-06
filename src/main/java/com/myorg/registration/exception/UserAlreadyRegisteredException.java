package com.myorg.registration.exception;

public class UserAlreadyRegisteredException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public UserAlreadyRegisteredException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public UserAlreadyRegisteredException() {
		super();
	}
}
