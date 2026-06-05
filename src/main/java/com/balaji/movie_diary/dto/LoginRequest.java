package com.balaji.movie_diary.dto;

import lombok.Data;

@Data
public class LoginRequest {
	private String email;
	private String password;
}
