package com.example.appjodoh.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1708941761004288591L;
	private static final long JWT_TOKEN_VALIDITY = 1*30*60;

	@Value("{jwt.secret}")
	private String secret;
	
	/* JIKA KITA SUDAH PUNYA TOKEN */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		// TODO Auto-generated method stub
		final Claims claims = getAllClaimFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimFromToken(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//JIKA LOGIN DAN TIDAK MEMPUNYAI TOKEN
	public String generateToken(UserDetails userDetails, String gender) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("gender", gender);
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		// TODO Auto-generated method stub
		Date dateExp = new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY *1000);
		String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(dateExp)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}
	
	//TOKEN VALIDATION
	public Boolean validateToken(String token,UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	

}
