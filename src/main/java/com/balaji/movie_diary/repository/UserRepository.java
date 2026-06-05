package com.balaji.movie_diary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balaji.movie_diary.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	User findByUserName(String userName);
}
