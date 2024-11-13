package com.vision_hackathon.cheollian.auth.domain.entity;

import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token")
public class RefreshToken {
	@Id
	private String email;

	@Indexed
	private String refreshToken;

	@TimeToLive(unit = TimeUnit.MILLISECONDS)
	private Long expiration;

	@Builder
	public RefreshToken(String email, String refreshToken, Long expiration) {
		this.email = email;
		this.refreshToken = refreshToken;
		this.expiration = expiration;
	}
}
