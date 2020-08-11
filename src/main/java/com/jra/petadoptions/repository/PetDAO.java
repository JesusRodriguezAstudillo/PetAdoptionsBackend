package com.jra.petadoptions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jra.petadoptions.models.PetEntity;

public interface PetDAO extends JpaRepository<PetEntity, Integer> {

}
