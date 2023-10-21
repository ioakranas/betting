package com.accepted.betting.exception;

import java.util.stream.Stream;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultErrorResponse {

	BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
	NO_MATCH_FOUND(HttpStatus.NOT_FOUND, "No match Found"),
	INVALID_MATCH_ODDS(HttpStatus.BAD_REQUEST, "Invalid match odds"),
	INVALID_MATCH_DATE(HttpStatus.BAD_REQUEST, "Invalid match date"),
	TOO_MANY_REQUESTS(HttpStatus.BAD_REQUEST, "Too many requests for the same ip");
	
	private HttpStatus status;
	private String message;
	
	public static DefaultErrorResponse findByMessage(String message) {
		return Stream.of(DefaultErrorResponse.values())
                .filter(s -> s.getMessage().equalsIgnoreCase(message))
                .findFirst()
                .orElse(DefaultErrorResponse.BAD_REQUEST);
	}
	
	public static DefaultErrorResponse findByExceptionMessage(String message) {
		return Stream.of(DefaultErrorResponse.values())
                .filter(s -> message.contains(s.getMessage()))
                .findFirst()
                .orElse(DefaultErrorResponse.BAD_REQUEST);
	}
}
