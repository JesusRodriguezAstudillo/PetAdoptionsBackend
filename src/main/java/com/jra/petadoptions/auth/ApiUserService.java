package com.jra.petadoptions.auth;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jra.petadoptions.models.User;
import com.jra.petadoptions.models.UserEntity;
import com.jra.petadoptions.models.UserRoleEntity;

public interface ApiUserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	public Set<? extends GrantedAuthority> getGrantedAuthorities(Set<UserRoleEntity> roles);
	public String createUser(User user);
	public UserEntity modelToEntity(User user);
	public User entityToModel(UserEntity entity);
	public List<User> getUsers();
	public String deleteUser(String username);
}
