package com.jra.petadoptions.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {
		super("Unable to find the user");
	}
}
