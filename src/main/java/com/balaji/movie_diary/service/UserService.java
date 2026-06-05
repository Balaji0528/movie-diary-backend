package com.balaji.movie_diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.balaji.movie_diary.dto.DiaryResponse;
import com.balaji.movie_diary.dto.ProfileResponse;
import com.balaji.movie_diary.entity.User;
import com.balaji.movie_diary.exception.ResourceNotFoundException;
import com.balaji.movie_diary.repository.DiaryRepository;
import com.balaji.movie_diary.repository.UserRepository;
import com.balaji.movie_diary.repository.WatchlistRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DiaryRepository diaryRepository;

	@Autowired
	private WatchlistRepository watchlistRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private DiaryService diaryService;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public ProfileResponse getProfile(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));

		int diaryCount = diaryRepository.countByUser(user);
		int watchlistCount = watchlistRepository.countByUser(user);
		List<DiaryResponse> recentEntries = diaryService.getRecentEntries(user);

		ProfileResponse profile = new ProfileResponse();

		profile.setName(user.getUserName());
		profile.setEmail(user.getEmail());
		profile.setDiaryCount(diaryCount);
		profile.setWatchlistCount(watchlistCount);
		profile.setRecentEntries(recentEntries);

		return profile;
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public User updateUserById(Long id, User newUser) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		existingUser.setEmail(newUser.getEmail());
		existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		existingUser.setUserName(newUser.getUserName());

		return userRepository.save(existingUser);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));
	}

	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

}