package com.accepted.betting.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckedException extends Exception {

private static final long serialVersionUID = 1L;
	
	private ErrorResponse errorResponse;

	public CheckedException(final DefaultErrorResponse defaultErrorResponse) {
		super(defaultErrorResponse.getMessage());
		this.errorResponse = new ErrorResponse(defaultErrorResponse.getStatus(), defaultErrorResponse.getMessage());
	}
}
