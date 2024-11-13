package com.vision_hackathon.cheollian.auth.security.exception;

import com.vision_hackathon.cheollian.exception.support.security.AuthorizationFailedException;

public class RefreshTokenExpiredException extends AuthorizationFailedException {
	private static final String errorMsg = "REFRESH_TOKEN_EXPIRED";

	public RefreshTokenExpiredException() {
		super(errorMsg);
	}
}
