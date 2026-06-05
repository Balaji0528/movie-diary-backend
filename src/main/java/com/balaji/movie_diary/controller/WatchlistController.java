package com.balaji.movie_diary.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.movie_diary.dto.ApiResponse;
import com.balaji.movie_diary.dto.DiaryRequest;
import com.balaji.movie_diary.entity.Diary;
import com.balaji.movie_diary.entity.Movie;
import com.balaji.movie_diary.entity.Watchlist;
import com.balaji.movie_diary.service.WatchlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

	@Autowired
	WatchlistService watchlistService;

	@PostMapping
	public ResponseEntity<Watchlist> addToWatchList(@RequestBody Movie movie, @AuthenticationPrincipal String email) {
		return ResponseEntity.status(201).body(watchlistService.addToWatchlist(movie, email));
	}

	@GetMapping
	public ResponseEntity<List<Watchlist>> getUserWatchlist(@AuthenticationPrincipal String email) {
		return ResponseEntity.ok(watchlistService.getUserWatchlist(email));
	}

	@DeleteMapping("/{watchlistId}")
	public ResponseEntity<?> removeFromWatchlist(@PathVariable Long watchlistId,
			@AuthenticationPrincipal String email) {
		watchlistService.removeFromWatchlist(watchlistId, email);

		return ResponseEntity.ok(new ApiResponse("Watchlist removed", 200, LocalDateTime.now()));
	}

	@PostMapping("/mark-watched/{watchlistId}")
	public ResponseEntity<Diary> markAsWatched(@PathVariable Long watchlistId, @Valid @RequestBody DiaryRequest request,
			@AuthenticationPrincipal String email) {
		return ResponseEntity.status(201).body(watchlistService.markAsWatched(watchlistId, request, email));
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<?> getWatchlistByMovieId(@AuthenticationPrincipal String email, @PathVariable Long movieId) {
		return ResponseEntity.ok(watchlistService.findWatchlistByMail(email, movieId));
	}
}
