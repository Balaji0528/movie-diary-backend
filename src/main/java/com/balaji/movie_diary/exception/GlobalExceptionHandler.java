package com.balaji.movie_diary.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.balaji.movie_diary.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiResponse response = new ApiResponse(ex.getMessage(), 404, LocalDateTime.now());
		return ResponseEntity.status(404).body(response);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(400).body(errors);

	}

	@ExceptionHandler
	public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
		ApiResponse response = new ApiResponse(ex.getMessage(), 404, LocalDateTime.now());
		return ResponseEntity.status(400).body(response);
	}
}
