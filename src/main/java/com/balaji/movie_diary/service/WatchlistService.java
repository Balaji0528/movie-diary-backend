package com.balaji.movie_diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balaji.movie_diary.dto.DiaryRequest;
import com.balaji.movie_diary.dto.WatchlistResponse;
import com.balaji.movie_diary.entity.Diary;
import com.balaji.movie_diary.entity.Movie;
import com.balaji.movie_diary.entity.User;
import com.balaji.movie_diary.entity.Watchlist;
import com.balaji.movie_diary.exception.BadRequestException;
import com.balaji.movie_diary.exception.DuplicateResourceFoundException;
import com.balaji.movie_diary.exception.ResourceNotFoundException;
import com.balaji.movie_diary.repository.DiaryRepository;
import com.balaji.movie_diary.repository.MovieRepository;
import com.balaji.movie_diary.repository.UserRepository;
import com.balaji.movie_diary.repository.WatchlistRepository;

@Service
public class WatchlistService {
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WatchlistRepository watchlistRepository;

	@Autowired
	DiaryRepository diaryRepository;

	public Watchlist addToWatchlist(Movie movie, String email) {

		if (movie == null || movie.getTmdbId() == null) {
			throw new BadRequestException("Movie or TMDB ID cannot be null");
		}

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));

		boolean exists = watchlistRepository.existsByUserIdAndMovieId(user.getId(), movie.getTmdbId());

		if (exists) {
			throw new DuplicateResourceFoundException("Movie is already in watchlist");
		}

		Movie fetchMovie = movieRepository.findByTmdbId(movie.getTmdbId()).orElseGet(() -> movieRepository.save(movie));

		Watchlist watchList = new Watchlist();
		watchList.setMovie(fetchMovie);
		watchList.setUser(user);

		return watchlistRepository.save(watchList);
	}

	public List<Watchlist> getUserWatchlist(String email) {

		return watchlistRepository.findByUserEmail(email);
	}

	public void removeFromWatchlist(Long watchlistId, String email) {

		Watchlist watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Watchlist", "Id", watchlistId));

		if (!watchlist.getUser().getEmail().equals(email)) {
			throw new BadRequestException("Unauthorized action");
		}

		watchlistRepository.delete(watchlist);

	}

	public Diary markAsWatched(Long watchlistId, DiaryRequest request, String email) {

		Watchlist watchlist = watchlistRepository.findById(watchlistId)
				.orElseThrow(() -> new ResourceNotFoundException("Watchlist", "Id", watchlistId));

		if (!watchlist.getUser().getEmail().equals(email)) {
			throw new BadRequestException("Unauthorized action");
		}

		User user = watchlist.getUser();
		Movie movie = watchlist.getMovie();

		boolean alreadyExists = diaryRepository.existsByUserIdAndMovieId(user.getId(), movie.getTmdbId());

		if (alreadyExists) {
			throw new DuplicateResourceFoundException("Movie already marked as watched");
		}

		Diary diary = new Diary();
		diary.setUser(user);
		diary.setMovie(movie);
		diary.setRating(request.getRating());
		diary.setReview(request.getReview());
		diary.setWatchedDate(LocalDate.now());

		Diary savedDiary = diaryRepository.save(diary);

		watchlistRepository.delete(watchlist);

		return savedDiary;

	}

	public WatchlistResponse findWatchlistByMail(String email, Long movieId) {
		Optional<Watchlist> o = watchlistRepository.findByUserEmailAndMovieId(email, movieId);

		if (o.isPresent()) {
			return new WatchlistResponse(true, o.get().getId());
		}

		return new WatchlistResponse(false, null);
	}
}
