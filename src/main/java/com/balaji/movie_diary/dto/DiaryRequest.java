package com.balaji.movie_diary.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiaryRequest {

	@NotNull(message = "TMDB id is required")
	private Long tmdbId;

	@NotBlank(message = "Title cannot be empty")
	private String title;

	private String review;

	@NotNull(message = "Rating is required")
	@DecimalMin(value = "0.5")
	@DecimalMax(value = "5.0")
	private Float rating;

	private LocalDate watchedDate;

}