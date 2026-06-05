package com.balaji.movie_diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.movie_diary.dto.LoginRequest;
import com.balaji.movie_diary.dto.RegisterRequest;
import com.balaji.movie_diary.entity.User;
import com.balaji.movie_diary.service.UserService;
import com.balaji.movie_diary.utility.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		if (userService.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("Email Already registered with another account");
		}

		User user = new User();
		user.setUserName(request.getUserName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		return ResponseEntity.ok(userService.saveUser(user));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		User userDb = userService.findByEmail(request.getEmail());

		if (!passwordEncoder.matches(request.getPassword(), userDb.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
		}

		String token = jwtUtil.generateToken(userDb.getEmail());
		return ResponseEntity.ok(token);

	}
}
