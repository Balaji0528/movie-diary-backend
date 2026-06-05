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
import com.balaji.movie_diary.dto.DiaryResponse;
import com.balaji.movie_diary.service.DiaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/diary")
public class DiaryController {

	@Autowired
	DiaryService diaryService;;

	@PostMapping
	public ResponseEntity<DiaryResponse> addDiary(@AuthenticationPrincipal String email,
			@Valid @RequestBody DiaryRequest request) {
		return ResponseEntity.status(201).body(diaryService.addDiary(request, email));
	}

	@GetMapping
	public ResponseEntity<List<DiaryResponse>> getUserDiary(@AuthenticationPrincipal String email) {
		return ResponseEntity.ok(diaryService.getUserDiary(email));
	}

	@DeleteMapping("/{diaryId}")
	public ResponseEntity<?> removeDiary(@PathVariable Long diaryId, @AuthenticationPrincipal String email) {
		diaryService.removeDiary(diaryId, email);
		return ResponseEntity.ok(new ApiResponse("Diary removed", 200, LocalDateTime.now()));
	}
}
