package com.balaji.movie_diary.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	private String userName;
	private String email;
	private String password;
}
