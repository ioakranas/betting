package com.accepted.betting.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = CheckedException.class)
	public ResponseEntity<String> handleCheckedException(CheckedException e) {
		ErrorResponse error = e.getErrorResponse();
		return ResponseEntity.status(error.getStatus()).body(error.getMessage());
	}

	@ExceptionHandler()
	public ResponseEntity<String> handleException(Exception e) {
		log.error("GlobalExceptionHandler will handle exception under:{}", e);
		return ResponseEntity.status(DefaultErrorResponse.BAD_REQUEST.getStatus())
				.body(DefaultErrorResponse.BAD_REQUEST.getMessage());
	}
}
