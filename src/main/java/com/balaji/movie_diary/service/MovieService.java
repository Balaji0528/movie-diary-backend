package com.balaji.movie_diary.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.balaji.movie_diary.dto.MovieResult;
import com.balaji.movie_diary.dto.TmdbResponse;
import com.balaji.movie_diary.entity.Movie;
import com.balaji.movie_diary.exception.BadRequestException;
import com.balaji.movie_diary.exception.ResourceNotFoundException;
import com.balaji.movie_diary.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	MovieRepository repository;

	@Value("${tmdb.api.key}")
	String apiKey;

	@Value("${tmdb.base.url}")
	String baseUrl;

	public Movie saveMovieIfNotExists(Movie movie) {

		Optional<Movie> o = repository.findByTmdbId(movie.getTmdbId());

		if (o.isPresent()) {
			return o.get();
		}

		return repository.save(movie);
	}

	public Movie getMovieById(Long tmdbId) {
		Optional<Movie> existing = repository.findByTmdbId(tmdbId);

		if (existing.isPresent()) {
			return existing.get();
		}

		String url = String.format("%s/movie/%d?api_key=%s", baseUrl, tmdbId, apiKey);

		MovieResult result = restTemplate.getForObject(url, MovieResult.class);

		if (result == null) {
			throw new ResourceNotFoundException("Movie", "tmdbId", tmdbId);
		}

		Movie movie = convertToEntity(result);

		return repository.save(movie);
	}

	public List<Movie> searchMovies(String name) {

		if (name == null || name.isBlank()) {
			throw new BadRequestException("Movie name cannot be empty");
		}

		String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);

		String url = String.format("%s/search/movie?api_key=%s&query=%s", baseUrl, apiKey, encodedName);

		TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

		if (response == null || response.getResults() == null) {
			return new ArrayList<>();
		}

		List<Movie> movies = new ArrayList<>();

		for (MovieResult result : response.getResults()) {
			movies.add(convertToEntity(result));
		}

		return movies.stream().limit(6).toList();
	}

	public Movie convertToEntity(MovieResult result) {
		Movie movie = new Movie();

		movie.setAdult(result.getAdult());
		movie.setBackDropUrl("https://image.tmdb.org/t/p/w780" + result.getBackdrop_path());
		movie.setTmdbId(result.getId());
		movie.setTitle(result.getTitle());
		movie.setOriginalLanguage(result.getOriginal_language());
		movie.setPopularity(result.getPopularity());
		movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + result.getPoster_path());
		movie.setReleaseDate(result.getRelease_date());
		movie.setOverview(result.getOverview());
		movie.setVoteAverage(result.getVote_average());
		movie.setVoteCount(result.getVote_count());
		movie.setRuntime(result.getRuntime());

		return movie;

	}

	public List<Movie> getAllMovies() {
		return repository.findAll();
	}

	public List<Movie> getTrendingMovies() {
		String url = String.format("%s/trending/movie/day?api_key=%s", baseUrl, apiKey);

		TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

		if (response == null || response.getResults() == null) {
			return new ArrayList<Movie>();
		}

		List<Movie> movies = new ArrayList<Movie>();

		for (MovieResult result : response.getResults()) {
			movies.add(convertToEntity(result));
		}

		return movies.stream().limit(20).toList();
	}

	public List<Movie> getPopularMovies() {
		String url = String.format("%s/movie/popular?api_key=%s", baseUrl, apiKey);

		TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);

		if (response == null || response.getResults() == null) {
			return new ArrayList<>();
		}

		List<Movie> movies = new ArrayList<Movie>();

		for (MovieResult result : response.getResults()) {
			movies.add(convertToEntity(result));
		}

		return movies.stream().limit(12).toList();
	}
}
