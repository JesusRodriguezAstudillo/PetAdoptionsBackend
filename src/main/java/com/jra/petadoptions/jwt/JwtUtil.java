package com.jra.petadoptions.jwt;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jra.petadoptions.auth.ApiUserDAO;
import com.jra.petadoptions.models.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtil {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private SecretKey secretKey;
	
	@Autowired
	private ApiUserDAO userDAO;
	
	public String getUsernameFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date getExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Boolean isTokenExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) throws Exception {
		return createToken(userDetails.getAuthorities(), userDetails.getUsername());
	}
	
	public String createToken(Collection<? extends GrantedAuthority> claims, String username) throws Exception {
		Integer id = userDAO
						.findByUsername(username)
						.map(user -> user.getId())
						.orElseThrow(() -> new Exception("something wrong"));
		
		String token = Jwts.builder()
						.setSubject(username)
						.claim("authorities", claims)
						.claim("id", id)
						.setIssuedAt(new Date())
						.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(3)))
						.signWith(secretKey)
						.compact();
		
		return token;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}
