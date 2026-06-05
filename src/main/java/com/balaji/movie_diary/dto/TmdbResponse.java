package com.balaji.movie_diary.dto;

import java.util.List;

import lombok.Data;

@Data
public class TmdbResponse {
	private int page;
	private List<MovieResult> results;
	private int totalPages;
	private int totalResults;
}
