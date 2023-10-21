package com.accepted.betting.exception;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(Exception e) {
		DefaultErrorResponse de = DefaultErrorResponse.findByExceptionMessage(e.getMessage());
		return ResponseEntity.status(de.getStatus())
				.body(de.getMessage());
	}

	@ExceptionHandler()
	public ResponseEntity<String> handleException(Exception e) {
		log.error("GlobalExceptionHandler will handle exception under:{}", e);
		if (e instanceof UndeclaredThrowableException
				&& ((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof CheckedException) {
			DefaultErrorResponse de = DefaultErrorResponse
					.findByMessage(((UndeclaredThrowableException) e).getUndeclaredThrowable().getMessage());
			return ResponseEntity.status(de.getStatus()).body(de.getMessage());
		}
		return ResponseEntity.status(DefaultErrorResponse.BAD_REQUEST.getStatus())
				.body(DefaultErrorResponse.BAD_REQUEST.getMessage());
	}
}
