package com.balaji.movie_diary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.movie_diary.entity.Movie;
import com.balaji.movie_diary.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	MovieService service;

	@GetMapping("/search")
	public ResponseEntity<List<Movie>> searchMovies(@RequestParam String name) {
		return ResponseEntity.ok(service.searchMovies(name));
	}

	@PostMapping
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
		return ResponseEntity.ok(service.saveMovieIfNotExists(movie));
	}

	@GetMapping
	public ResponseEntity<List<Movie>> getMovies() {
		return ResponseEntity.ok(service.getAllMovies());
	}

	@GetMapping("/{tmdbId}")
	public ResponseEntity<Movie> findMovieById(@PathVariable Long tmdbId) {
		return ResponseEntity.ok(service.getMovieById(tmdbId));
	}

	@GetMapping("/trending")
	public ResponseEntity<List<Movie>> trendingMovies() {
		return ResponseEntity.ok(service.getTrendingMovies());
	}

	@GetMapping("/popular")
	public ResponseEntity<List<Movie>> popularMovies() {
		return ResponseEntity.ok(service.getPopularMovies());
	}
}
