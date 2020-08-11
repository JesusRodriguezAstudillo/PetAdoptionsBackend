package com.jra.petadoptions.exception;

public class PetsNotFoundException extends RuntimeException {
	public PetsNotFoundException() {
		super("Unable to retrieve data. Try again later.");
	}
}
