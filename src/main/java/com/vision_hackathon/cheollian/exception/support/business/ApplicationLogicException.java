package com.vision_hackathon.cheollian.exception.support.business;

import org.springframework.http.HttpStatus;

import com.vision_hackathon.cheollian.exception.support.global.GlobalException;

import lombok.Getter;

@Getter
public abstract class ApplicationLogicException extends GlobalException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private final String errorMsg;

	public ApplicationLogicException(final String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
}
