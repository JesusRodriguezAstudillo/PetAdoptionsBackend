package com.jra.petadoptions.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jra.petadoptions.auth.ApiUserServiceImpl;
import com.jra.petadoptions.jwt.JwtUtil;
import com.jra.petadoptions.jwt.UsernameAndPasswordAuthenticationRequest;
import com.jra.petadoptions.models.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ApiUserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UsernameAndPasswordAuthenticationRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch(Exception e) {
			throw new Exception("Failed to authenticate user.");
		}
 		
		String jwtToken = jwtUtil.generateToken(userService.loadUserByUsername(authRequest.getUsername()));
		
		return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
	}
	
	@PostMapping("/createAccount")
	public ResponseEntity<String> createAccount(@RequestBody @Valid User user) {
		String response = userService.createUser(user);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getUsers(){
		List<User> userList = userService.getUsers();
		
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam String username) {
		String response = userService.deleteUser(username);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
