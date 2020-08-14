package com.jra.petadoptions.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jra.petadoptions.models.UserEntity;

@Repository
public interface ApiUserDAO extends JpaRepository<UserEntity, Integer>{
	public Optional<UserEntity> findByUsername(String username);
	
	@Modifying
	@Query(value="delete from userrole ur where ur.userid = :userId and ur.roleid= :roleId",
			nativeQuery=true)
	public void deleteByUserIdAndRoleId(Integer userId, Integer roleId);
}
