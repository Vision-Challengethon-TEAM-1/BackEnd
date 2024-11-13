package com.vision_hackathon.cheollian.exception.support.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AuthorizationFailedException extends SecurityException {
	private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
	public AuthorizationFailedException(final String errorMsg) {
		super(errorMsg);
	}
}