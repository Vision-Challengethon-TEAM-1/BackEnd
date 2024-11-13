package com.vision_hackathon.cheollian.auth.security.exception;

import com.vision_hackathon.cheollian.exception.support.security.AuthorizationFailedException;

public class AccessTokenInvalidException extends AuthorizationFailedException {
	private static final String errorMsg = "ACCESS_TOKEN_INVALID";

	public AccessTokenInvalidException() {
		super(errorMsg);
	}
}
