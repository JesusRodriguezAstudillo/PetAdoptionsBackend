package com.jra.petadoptions.exception;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<String> handlePetNotFoundException(PetNotFoundException e){
		String errorMsg = Optional.of(e.getMessage()).orElse(e.getClass().getName());
		
		return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PetsNotFoundException.class)
	public ResponseEntity<String> handlePetsNotFoundException(PetsNotFoundException e){
		String errorMsg = Optional.of(e.getMessage()).orElse(e.getClass().getName());
		
		return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>("Some Exception", HttpStatus.BAD_REQUEST);
	}
}
