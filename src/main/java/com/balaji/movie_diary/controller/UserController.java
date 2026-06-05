package com.balaji.movie_diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.movie_diary.dto.ProfileResponse;
import com.balaji.movie_diary.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
		return ResponseEntity.ok(userService.getProfile(authentication.getName()));
	}
}
