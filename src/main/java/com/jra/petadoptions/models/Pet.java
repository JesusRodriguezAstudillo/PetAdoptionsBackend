package com.jra.petadoptions.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Pet {
	
	@Positive
	private Integer id;
	
	@NotBlank
	private String name;
	
	private String species = "Unknown";
	private String breed = "Unknown";
	
	private Character gender;
	
	@PositiveOrZero
	private Integer ageYears;
	
	@PositiveOrZero
	private Integer ageMonths;
	
	private byte[] image;
	private String imageExt;
	private Boolean vaccinated = false;
	
	@PositiveOrZero
	private Integer reservedBy = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public Integer getAgeYears() {
		return ageYears;
	}
	public void setAgeYears(Integer ageYears) {
		this.ageYears = ageYears;
	}
	public Integer getAgeMonths() {
		return ageMonths;
	}
	public void setAgeMonths(Integer ageMonths) {
		this.ageMonths = ageMonths;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getImageExt() {
		return imageExt;
	}
	public void setImageExt(String imageExt) {
		this.imageExt = imageExt;
	}
	public Boolean getVaccinated() {
		return vaccinated;
	}
	public void setVaccinated(Boolean vaccinated) {
		this.vaccinated = vaccinated;
	}
	public Integer getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(Integer reservedBy) {
		this.reservedBy = reservedBy;
	}
}
