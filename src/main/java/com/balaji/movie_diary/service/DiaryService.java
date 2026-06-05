package com.balaji.movie_diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balaji.movie_diary.dto.DiaryRequest;
import com.balaji.movie_diary.dto.DiaryResponse;
import com.balaji.movie_diary.entity.Diary;
import com.balaji.movie_diary.entity.Movie;
import com.balaji.movie_diary.entity.User;
import com.balaji.movie_diary.exception.BadRequestException;
import com.balaji.movie_diary.exception.ResourceNotFoundException;
import com.balaji.movie_diary.repository.DiaryRepository;
import com.balaji.movie_diary.repository.MovieRepository;
import com.balaji.movie_diary.repository.UserRepository;

@Service
public class DiaryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private DiaryRepository diaryRepository;

	public DiaryResponse addDiary(DiaryRequest request, String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));

		Movie movie = new Movie();
		movie.setTmdbId(request.getTmdbId());
		movie.setTitle(request.getTitle());

		Movie savedMovie = movieRepository.findByTmdbId(movie.getTmdbId()).orElseGet(() -> movieRepository.save(movie));

		Diary diary = new Diary();
		diary.setUser(user);
		diary.setMovie(savedMovie);
		diary.setRating(request.getRating());
		diary.setReview(request.getReview());
		diary.setWatchedDate(request.getWatchedDate());

		Diary d = diaryRepository.save(diary);

		return mapToResponse(d);
	}

	public List<DiaryResponse> getUserDiary(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));

		List<Diary> diaryList = diaryRepository.findByUserId(user.getId());

		return diaryList.stream().map(this::mapToResponse).toList();
	}

	public void removeDiary(Long id, String email) {

		Diary diary = diaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Diary", "id", id));

		if (!diary.getUser().getEmail().equals(email)) {
			throw new BadRequestException("Unauthorized action");
		}

		diaryRepository.delete(diary);
	}

	public List<DiaryResponse> getRecentEntries(User user) {
		List<Diary> recentDiary = diaryRepository.findTop5ByUserOrderByWatchedDateDesc(user);

		List<DiaryResponse> recentEntries = recentDiary.stream().map(this::mapToResponse).toList();

		return recentEntries;
	}

	public DiaryResponse mapToResponse(Diary d) {
		return new DiaryResponse(d.getId(), d.getMovie().getTmdbId(), d.getMovie().getTitle(),
				d.getMovie().getPosterUrl(), d.getReview(), d.getRating(), d.getWatchedDate(),
				d.getMovie().getReleaseDate());
	}
}