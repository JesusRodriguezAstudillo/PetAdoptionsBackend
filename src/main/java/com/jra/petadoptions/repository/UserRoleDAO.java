package com.jra.petadoptions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jra.petadoptions.models.UserRoleEntity;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRoleEntity, Integer>{
	public List<UserRoleEntity> findByRoleName(String roleName);
	
	@Modifying
	@Query(value="delete from rolepermission rp where rp.roleid = :roleId and rp.permissionid = :permissionId",
			nativeQuery=true)
	public void deletByRoleidAndPermissionid(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);
}
