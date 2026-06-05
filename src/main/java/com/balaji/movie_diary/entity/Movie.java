package com.balaji.movie_diary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Long tmdbId;

	@NotBlank
	private String title;
	private String posterUrl;
	private String backDropUrl;
	@Column(length = 5000)
	private String overview;
	@Column(name = "adult")
	private boolean isAdult;
	private String originalLanguage;
	private String releaseDate;
	private Double popularity;
	private Double voteAverage;
	private Double runtime;
	private Integer voteCount;
	private String tagline;

}
