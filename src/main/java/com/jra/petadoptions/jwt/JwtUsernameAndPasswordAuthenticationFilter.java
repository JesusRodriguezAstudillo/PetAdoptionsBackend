package com.jra.petadoptions.jwt;

import java.io.IOException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;
	private SecretKey secretKey;
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig,
			SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}
	
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest = 
					new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			
			Authentication authentication = 
					new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(), 
							authenticationRequest.getPassword());
			
			Authentication authenticationResult = authenticationManager.authenticate(authentication);
			
			return authenticationResult;
		} catch(IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// Don't know if I need successful authentication method
}
