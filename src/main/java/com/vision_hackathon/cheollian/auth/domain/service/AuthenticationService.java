package com.vision_hackathon.cheollian.auth.domain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.auth.config.JwtProperties;
import com.vision_hackathon.cheollian.auth.domain.entity.RefreshToken;
import com.vision_hackathon.cheollian.auth.domain.persistence.RefreshTokenRepository;
import com.vision_hackathon.cheollian.auth.jwt.dto.Token;
import com.vision_hackathon.cheollian.auth.jwt.dto.TokenType;
import com.vision_hackathon.cheollian.auth.jwt.provider.JwtCookieGenerator;
import com.vision_hackathon.cheollian.auth.jwt.provider.JwtTokenService;
import com.vision_hackathon.cheollian.auth.security.dto.Oauth2ResponseDto;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.persistence.MemberDetailRepository;
import com.vision_hackathon.cheollian.member.persistence.MemberRepository;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	private final MemberRepository memberRepository;
	private final MemberDetailRepository memberDetailRepository;
	private final JwtProperties jwtProperties;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenService jwtTokenService;
	private final JwtCookieGenerator jwtCookieGenerator;

	/**
	 * OAuth2 사용자 정보를 저장하거나 기존 정보를 반환합니다.
	 *
	 * @param responseDto OAuth2 응답 DTO
	 * @return 저장되었거나 기존의 OAuth2 사용자 정보 엔티티
	 */
	@Transactional
	public Member authenticationMember(Oauth2ResponseDto responseDto) {
		return memberRepository.findByEmail(responseDto.getEmail())
			.orElseGet(() -> memberRepository.save(responseDto.toEntity()));
	}

	public boolean validateRefreshToken(String email, String refreshTokenValue) {
		Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshTokenValue);
		return tokenOpt.map(refreshToken -> refreshToken.getEmail().equals(email)).orElse(false);
	}

	@Transactional
	public void updateRefreshToken(String email, Token newRefreshToken) {
		RefreshToken token = RefreshToken.builder()
			.email(email)
			.refreshToken(newRefreshToken.value())
			.expiration(jwtProperties.getExpiration(TokenType.REFRESH_TOKEN) * 1000L)
			.build();

		refreshTokenRepository.save(token);
	}

	@Transactional
	public void issueToken(HttpServletResponse response, Authentication authentication) {
		String email = authentication.getName();

		Token accessToken = jwtTokenService.generateToken(authentication, TokenType.ACCESS_TOKEN);
		Token refreshToken = jwtTokenService.generateToken(authentication, TokenType.REFRESH_TOKEN);

		Cookie accessCookie = jwtCookieGenerator.createCookie(accessToken);
		Cookie refreshCookie = jwtCookieGenerator.createCookie(refreshToken);

		updateRefreshToken(email, refreshToken);

		response.addCookie(accessCookie);
		response.addCookie(refreshCookie);
	}

	@Transactional
	public void logout(HttpServletResponse response, String refreshTokenValue) {
		refreshTokenRepository.findByRefreshToken(refreshTokenValue)
			.ifPresent(token -> refreshTokenRepository.deleteById(token.getEmail()));

		Cookie accessCookie = jwtCookieGenerator.deleteCookie(TokenType.ACCESS_TOKEN);
		Cookie refreshCookie = jwtCookieGenerator.deleteCookie(TokenType.REFRESH_TOKEN);

		response.addCookie(accessCookie);
		response.addCookie(refreshCookie);
	}

	public boolean hasMemberDetails(String email) {
		return memberDetailRepository.existsByMemberEmail(email);
	}
}
