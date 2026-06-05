package com.balaji.movie_diary.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final SecretKey SECRET = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey".getBytes());

	public String generateToken(String email) {
		return Jwts.builder().subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)).signWith(SECRET).compact();
	}

	public String extractEmail(String token) {
		return Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token).getPayload().getSubject();
	}
}