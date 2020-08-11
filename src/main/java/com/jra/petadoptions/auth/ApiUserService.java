package com.jra.petadoptions.auth;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jra.petadoptions.models.UserRoleEntity;

public interface ApiUserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	public Set<? extends GrantedAuthority> getGrantedAuthorities(Set<UserRoleEntity> roles);
}
