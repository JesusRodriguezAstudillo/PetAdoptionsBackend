package com.jra.petadoptions.service;

import java.util.List;

import com.jra.petadoptions.models.Pet;
import com.jra.petadoptions.models.PetEntity;

public interface PetService {
	public List<Pet> getAllPets();
	public Pet entityToModel(PetEntity entity);
	public PetEntity modelToEntity(Pet pet);
	public Pet getPet(Integer id);
	public String addPet(Pet pet);
	public String deletePet(Integer id);
	public String editPet(Pet pet);
	public String reservePet(Integer userId, Integer petId);
}
