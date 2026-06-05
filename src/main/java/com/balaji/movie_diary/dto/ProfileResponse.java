package com.balaji.movie_diary.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProfileResponse {
	private String name;
	private String email;
	private int diaryCount;
	private int watchlistCount;

	private List<DiaryResponse> recentEntries;
}
