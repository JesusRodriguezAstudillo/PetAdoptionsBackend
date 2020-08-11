package com.jra.petadoptions.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtConfig jwtConfig;
	private SecretKey secretKey;
	
	public JwtTokenFilter(JwtConfig jwtConfig, SecretKey secretKey) {
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeaders());
		
		if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		try {
			String jwtToken = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
			
			Jws<Claims> claims = Jwts
									.parserBuilder()
									.setSigningKey(secretKey)
									.build()
									.parseClaimsJws(jwtToken);
			
			Claims body = claims.getBody();
			String username = body.getSubject();
			List<Map<String, String>> authoritiesList = (List<Map<String, String>>)body.get("authorities");
			
			Set<SimpleGrantedAuthority> authoritiesSet = authoritiesList
															.stream()
															.map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
															.collect(Collectors.toSet());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesSet);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch(JwtException e) {
			throw new IllegalStateException("Untrusted Token");
		}
		
		filterChain.doFilter(request, response);
	}
}
