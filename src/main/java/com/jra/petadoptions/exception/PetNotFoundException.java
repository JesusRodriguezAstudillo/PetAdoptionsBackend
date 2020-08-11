package com.jra.petadoptions.exception;

public class PetNotFoundException extends RuntimeException{
	
	public PetNotFoundException(Integer id) {
		super("Unable to find the pet with id: " + id);
	}
}
