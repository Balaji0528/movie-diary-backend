package com.balaji.movie_diary.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiaryResponse {

	private Long id;

	private Long tmdbId;

	private String title;

	private String posterUrl;

	private String review;

	private Float rating;

	private LocalDate watchedDate;

	private String releaseDate;
}