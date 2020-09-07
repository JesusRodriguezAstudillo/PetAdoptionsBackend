package com.jra.petadoptions.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity(name="pet")
public class PetEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String species;
	private String breed;
	private Character gender;
	
	@Column(name="age_years")
	private Integer ageYears;
	
	@Column(name="age_months")
	private Integer ageMonths;
	
	@Column(name="image")
	@Lob
	private byte[] image;
	
	@Column(name="imageext")
	private String imageExt;
	private Boolean vaccinated;
	
	@OneToOne(cascade=CascadeType.ALL,optional=true)
	@JoinColumn(name="reserved_by",referencedColumnName="id")
	private UserEntity reservedBy;
	
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
	public UserEntity getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(UserEntity reservedBy) {
		this.reservedBy = reservedBy;
	}
}
