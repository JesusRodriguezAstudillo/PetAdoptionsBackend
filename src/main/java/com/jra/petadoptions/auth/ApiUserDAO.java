package com.jra.petadoptions.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jra.petadoptions.models.UserEntity;

@Repository
public interface ApiUserDAO extends JpaRepository<UserEntity, Integer>{
	public Optional<UserEntity> findByUsername(String username);
}
