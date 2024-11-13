package com.vision_hackathon.cheollian.auth.security.exception;

import com.vision_hackathon.cheollian.exception.support.security.AuthorizationFailedException;

public class RefreshTokenInvalidException extends AuthorizationFailedException {
	private static final String errorMsg = "REFRESH_TOKEN_INVALID";

	public RefreshTokenInvalidException() {
		super(errorMsg);
	}
}
