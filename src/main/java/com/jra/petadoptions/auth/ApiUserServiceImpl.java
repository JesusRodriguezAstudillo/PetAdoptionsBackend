package com.jra.petadoptions.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jra.petadoptions.models.PermissionEntity;
import com.jra.petadoptions.models.UserEntity;
import com.jra.petadoptions.models.UserRoleEntity;

@Service
public class ApiUserServiceImpl implements ApiUserService, UserDetailsService {
	
	@Autowired
	private ApiUserDAO dao;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = dao.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("could not find user"));

		return new ApiUser(user.getUsername(),
					user.getPassword(),
					true, true, true, true,
					getGrantedAuthorities(user.getRoles()));
	}
	
	public Set<? extends GrantedAuthority> getGrantedAuthorities(Set<UserRoleEntity> userRoles) {
		Set<SimpleGrantedAuthority> authSet = new HashSet<>();
	
		for(UserRoleEntity role : userRoles) {
			authSet.addAll(getPermissions(role));
			authSet.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authSet;
	}
	
	public static Set<SimpleGrantedAuthority> getPermissions(UserRoleEntity role) {
		Set<SimpleGrantedAuthority> permissions = new HashSet<>();
		
		for(PermissionEntity permission : role.getPermissions()) {
			permissions.add(new SimpleGrantedAuthority(permission.getName()));
		}
		
		return permissions;
	}
}
