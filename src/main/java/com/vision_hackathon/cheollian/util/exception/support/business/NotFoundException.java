package com.vision_hackathon.cheollian.util.exception.support.business;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends ApplicationLogicException {
	private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	public NotFoundException(final String errorCode) {
		super(errorCode);
	}
}
