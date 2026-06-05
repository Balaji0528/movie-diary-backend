package com.balaji.movie_diary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balaji.movie_diary.entity.User;
import com.balaji.movie_diary.entity.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

	boolean existsByUserIdAndMovieId(Long userId, Long movieId);

	Optional<Watchlist> findByUserEmailAndMovieId(String email, Long movieId);

	List<Watchlist> findByUserEmail(String email);

	int countByUser(User user);
}
