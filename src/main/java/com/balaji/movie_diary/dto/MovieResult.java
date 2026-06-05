package com.balaji.movie_diary.dto;

import java.util.List;

import lombok.Data;

@Data
public class MovieResult {
	private Boolean adult;
	private String backdrop_path;
	private List<Integer> genre_ids;
	private Long id;
	private String title;
	private String original_language;
	private String original_title;
	private String overview;
	private Double popularity;
	private String poster_path;
	private String release_date;
	private boolean video;
	private Double vote_average;
	private Integer vote_count;
	private Double runtime;

}
