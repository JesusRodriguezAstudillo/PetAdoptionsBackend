package com.jra.petadoptions.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jra.petadoptions.models.Pet;
import com.jra.petadoptions.service.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	private final static Logger logger = LoggerFactory.getLogger(PetController.class);
	
	@Autowired
	private PetService service;
	
	@GetMapping("/viewAllPets")
	public ResponseEntity<List<Pet>> getAllPets() {
		
		List<Pet> petList = service.getAllPets();
		
		return new ResponseEntity<>(petList, HttpStatus.OK);
	}
	
	@GetMapping("/viewPet/{id}")
	public ResponseEntity<Pet> getPet(@PathVariable Integer id) {
		Pet pet = service.getPet(id);
		return new ResponseEntity<>(pet, HttpStatus.OK);
	}
	
	@PostMapping("/addPet")
	public ResponseEntity<String> addPet(@RequestBody @Valid Pet newPet) {
		String petName = service.addPet(newPet);
		
		return new ResponseEntity<>(petName, HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePet")
	public ResponseEntity<String> deletePet(@RequestParam Integer id) {
		String name = service.deletePet(id);

		return new ResponseEntity<>(name, HttpStatus.OK);
	}
	
	@PutMapping("/editPet/{id}")
	public ResponseEntity<String> editPet(@RequestBody @Valid Pet pet) {
		String response = service.editPet(pet);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/reservePet")
	public ResponseEntity<String> reservePet(@RequestBody Map<String, Integer> request) {
		String response = service.reservePet(request.get("userId"), request.get("petId"));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
