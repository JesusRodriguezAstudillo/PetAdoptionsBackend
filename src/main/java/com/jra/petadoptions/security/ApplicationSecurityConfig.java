package com.jra.petadoptions.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jra.petadoptions.auth.ApiUserServiceImpl;
import com.jra.petadoptions.jwt.JwtConfig;
import com.jra.petadoptions.jwt.JwtTokenFilter;
import com.jra.petadoptions.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ApiUserServiceImpl userService;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private SecretKey secretKey;
	
	private final String[] PERMIT_ALL_LIST = {
			"/user/login",
			"/user/createAccount",
			"/pet/viewAllPets",
			"/pet/viewPet/**",
			"/v2/api-docs/**",
			"/swagger-json",
			"/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**"	
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().cors()
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
			.addFilterAfter(new JwtTokenFilter(jwtConfig, secretKey), JwtUsernameAndPasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(PERMIT_ALL_LIST).permitAll()
			.antMatchers("/pet/addPet").hasAuthority("write")
			.antMatchers("/pet/deletePet").hasAuthority("delete")
			.antMatchers("/pet/editPet/**").hasAuthority("update")
			.antMatchers("/user/getUsers", "/user/deleteUser").hasAnyRole("ADMIN", "EDITOR")
			.antMatchers("/pet/reservePet").hasAnyRole("USER", "EDITOR", "ADMIN")
			.anyRequest()
			.authenticated();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder);
		provider.setUserDetailsService(userService);
		
		return provider;
	}
}
