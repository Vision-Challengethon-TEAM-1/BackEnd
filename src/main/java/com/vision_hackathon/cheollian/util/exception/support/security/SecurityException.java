package com.vision_hackathon.cheollian.util.exception.support.security;

import org.springframework.http.HttpStatus;

import com.vision_hackathon.cheollian.util.exception.support.global.GlobalException;

import lombok.Getter;

@Getter
public abstract class SecurityException extends GlobalException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private final String errorMsg;

	public SecurityException(final String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
}
