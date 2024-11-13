package com.vision_hackathon.cheollian.auth.domain.persistence;

import java.util.Optional;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import com.vision_hackathon.cheollian.auth.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
