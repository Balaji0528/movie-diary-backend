package com.balaji.movie_diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balaji.movie_diary.entity.Diary;
import com.balaji.movie_diary.entity.User;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	List<Diary> findByUserId(Long userId);

	boolean existsByUserIdAndMovieId(Long userID, Long tmdbId);

	int countByUser(User user);

	List<Diary> findTop5ByUserOrderByWatchedDateDesc(User user);
}
