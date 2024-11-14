package com.vision_hackathon.cheollian.auth.domain.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.vision_hackathon.cheollian.auth.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
