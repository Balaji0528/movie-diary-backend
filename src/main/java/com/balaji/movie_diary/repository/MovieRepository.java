package com.balaji.movie_diary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balaji.movie_diary.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	Optional<Movie> findByTmdbId(Long tmdbId);
}
