package com.accepted.betting.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultErrorResponse {

	BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
	NO_MATCH_FOUND(HttpStatus.NOT_FOUND, "No match Found");

	private HttpStatus status;
	private String message;
}
