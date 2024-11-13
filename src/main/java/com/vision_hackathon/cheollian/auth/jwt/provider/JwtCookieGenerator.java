package com.vision_hackathon.cheollian.auth.jwt.provider;

import org.springframework.stereotype.Component;

import com.vision_hackathon.cheollian.auth.config.JwtProperties;
import com.vision_hackathon.cheollian.auth.jwt.dto.Token;
import com.vision_hackathon.cheollian.auth.jwt.dto.TokenType;
import com.vision_hackathon.cheollian.util.cookie.CookieUtil;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtCookieGenerator {
	private final JwtProperties jwtProperties;

	public Cookie createCookie(Token token) {
		String cookieName = token.tokenType().name();
		String tokenValue = token.value();
		Integer expiration = jwtProperties.getExpiration(token.tokenType());
		String path = "/";
		boolean httpOnly = token.tokenType() == TokenType.REFRESH_TOKEN;

		return CookieUtil.createCookie(
			cookieName,
			tokenValue,
			expiration,
			path,
			httpOnly,
			true
		);
	}

	public Cookie deleteCookie(TokenType tokenType) {
		String cookieName = tokenType.name();
		String path = "/";
		boolean httpOnly = tokenType == TokenType.REFRESH_TOKEN;

		return CookieUtil.deleteCookie(
			cookieName,
			path,
			httpOnly,
			true
		);
	}
}
