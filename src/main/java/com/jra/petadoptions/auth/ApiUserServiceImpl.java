package com.jra.petadoptions.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jra.petadoptions.exception.UserNotFoundException;
import com.jra.petadoptions.models.PermissionEntity;
import com.jra.petadoptions.models.User;
import com.jra.petadoptions.models.UserEntity;
import com.jra.petadoptions.models.UserRoleEntity;
import com.jra.petadoptions.repository.UserRoleDAO;

@Service
public class ApiUserServiceImpl implements ApiUserService, UserDetailsService {
	
	@Autowired
	private ApiUserDAO dao;
	
	@Autowired
	private UserRoleDAO roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	public String createUser(User user) {
		UserEntity entity = modelToEntity(user);
		
		return dao.save(entity).getUsername();
	}
	
	public User entityToModel(UserEntity entity) {
		User user = new User();
		user.setEmail(entity.getEmail());
		user.setPassword(entity.getPassword());
		user.setUsername(entity.getUsername());
		
		return user;
	}
	
	public UserEntity modelToEntity(User user) {
		UserEntity entity = new UserEntity();
		entity.setEmail(user.getEmail());
		entity.setUsername(user.getUsername());
		entity.setPassword(passwordEncoder.encode(user.getPassword()));
		entity.setEnabled(true);
		
		Set<UserRoleEntity> startingRoles = roleDao
												.findByRoleName("ROLE_USER")
												.stream()
												.collect(Collectors.toSet());

		entity.setRoles(startingRoles);
		
		return entity;
	}
	
	public List<User> getUsers() {
		List<UserEntity> userList = dao.findAll();
		
		return userList
				.stream()
				.map(entity -> entityToModel(entity) )
				.collect(Collectors.toList());
	}
	
	@Transactional
	public String deleteUser(String username) {
		Optional<UserEntity> entity = dao.findByUsername(username);

		entity.ifPresent(user -> {
			Set<UserRoleEntity> roles = user.getRoles();
			roles.forEach(role -> {
				dao.deleteByUserIdAndRoleId(user.getId(), role.getRoleId());
			});
			
			dao.delete(user);
		});
		
		return entity
				.map(e -> e.getUsername())
				.orElseThrow(() -> new UserNotFoundException());
	}
}
