package com.jra.petadoptions.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jra.petadoptions.auth.ApiUserDAO;
import com.jra.petadoptions.exception.PetNotFoundException;
import com.jra.petadoptions.models.Pet;
import com.jra.petadoptions.models.PetEntity;
import com.jra.petadoptions.models.UserEntity;
import com.jra.petadoptions.repository.PetDAO;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDAO;
	
	@Autowired
	private ApiUserDAO userDAO;
	
	public List<Pet> getAllPets() {
		List<PetEntity> entityList = petDAO.findAll();
		List<Pet> petList = entityList.stream().map(entity -> entityToModel(entity)).collect(Collectors.toList());
		
		return petList;
	}

	public Pet entityToModel(PetEntity entity) {
		Pet pet = new Pet();
		
		pet.setId(entity.getId());
		pet.setName(entity.getName());
		pet.setBreed(entity.getBreed());
		pet.setGender(entity.getGender());
		pet.setSpecies(entity.getSpecies());
		pet.setAgeYears(entity.getAgeYears());
		pet.setAgeMonths(entity.getAgeMonths());
		pet.setImageLink(entity.getImageLink());
		pet.setVaccinated(entity.getVaccinated());
		
		if(entity.getReservedBy() != null) {
			pet.setReservedBy(entity.getReservedBy().getId());
		}
		
		return pet;
	}
	
	public PetEntity modelToEntity(Pet pet) {
		PetEntity entity = new PetEntity();
		
		entity.setName(pet.getName());
		entity.setBreed(pet.getBreed());
		entity.setGender(pet.getGender());
		entity.setSpecies(pet.getSpecies());
		entity.setAgeYears(pet.getAgeYears());
		entity.setAgeMonths(pet.getAgeMonths());
		entity.setImageLink(pet.getImageLink());
		entity.setVaccinated(pet.getVaccinated());
		entity.setReservedBy(userDAO.findById(pet.getReservedBy()).orElse(null));
		
		return entity;
	}
	
	public Pet getPet(Integer id) {
		
		return petDAO.findById(id)
				.map(entity -> entityToModel(entity))
				.orElseThrow(() -> new PetNotFoundException(id));
	}
	
	public String addPet(Pet pet) {
		PetEntity entity = modelToEntity(pet);
		String petName = petDAO.save(entity).getName();
		
		return petName;
	}
	
	public String deletePet(Integer id) {
		Optional<PetEntity> petEntity = petDAO.findById(id);
		petEntity.ifPresent(e -> petDAO.delete(e));
		
		return petEntity
				.map(e -> e.getName())
				.orElseThrow(() -> new PetNotFoundException(id));
	}
	
	public String editPet(Pet pet) {
		Optional<PetEntity> entity = petDAO.findById(pet.getId());
		String name = entity
				.map(p -> {
					p = modelToEntity(pet);
					String pName = p.getName();
					petDAO.save(p);
					return pName;
				})
				.orElseThrow(() -> new PetNotFoundException(pet.getId()));
		
		return name;
	}
	
	public String reservePet(Integer userId, Integer petId) {
		Optional<UserEntity> userEntity = userDAO.findById(userId);
		Optional<PetEntity> petEntity = petDAO.findById(petId);
		
		String response = new String();
		response = petEntity.map(pet -> {
			String result = new String();
			
			if(pet.getReservedBy() == null) {
				userEntity.ifPresent(user -> {
					pet.setReservedBy(user);
					petDAO.save(pet);
				});
				
				result = userEntity
						.map(userName -> "You reserved " + pet.getName() + "!")
						.orElse("Could not find your credentials. Try again later");
			}
			else {
				result = "Sorry, " + pet.getName() + " is reserved by someone else! Check back later.";
			}
			
			return result;
		}).orElseThrow(() -> new PetNotFoundException(petId));
		
		return response;
	}
}
