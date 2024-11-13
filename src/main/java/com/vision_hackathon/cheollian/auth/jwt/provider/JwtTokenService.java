package com.vision_hackathon.cheollian.auth.jwt.provider;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.vision_hackathon.cheollian.auth.config.JwtProperties;
import com.vision_hackathon.cheollian.auth.jwt.dto.Token;
import com.vision_hackathon.cheollian.auth.jwt.dto.TokenType;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.exception.MemberNotFoundException;
import com.vision_hackathon.cheollian.member.persistence.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenService {
	private final MemberRepository memberRepository;
	private final JwtProperties jwtProperties;
	private SecretKey signKey;

	@PostConstruct
	public void init() {
		this.signKey = new SecretKeySpec(jwtProperties.getSecret().getBytes(), "HmacSHA256");
	}

	public Token generateToken(Authentication authentication, TokenType type) {
		if (type == TokenType.ACCESS_TOKEN) {
			return doGenerateToken(authentication, TokenType.ACCESS_TOKEN);
		} else if (type == TokenType.REFRESH_TOKEN) {
			return doGenerateToken(authentication, TokenType.REFRESH_TOKEN);
		}

		throw new IllegalArgumentException("Unsupported token type: " + type);
	}

	private Token doGenerateToken(Authentication jwtAuthentication, TokenType type) {
		String token = Jwts.builder()
			.header().add(buildHeader(type)).and()
			.claims(buildPayload(jwtAuthentication))
			.expiration(buildExpiration(jwtProperties.getExpiration(type)))
			.signWith(signKey)
			.compact();

		return new Token(jwtProperties.getBearerType(), TokenType.ACCESS_TOKEN, token);
	}

	private Map<String, Object> buildHeader(TokenType type) {
		return Map.of(
			"typ", "JWT",
			"cat", type.name(),
			"alg", "HS256",
			"regDate", System.currentTimeMillis()
		);
	}

	private Map<String, Object> buildPayload(Authentication authentication) {
		return Map.of(
			"email", authentication.getName()
		);
	}

	private Date buildExpiration(Integer expirationSeconds) {
		return new Date(System.currentTimeMillis() + expirationSeconds * 1000);
	}

	public Jws<Claims> extractClaims(String tokenValue) {
		return Jwts.parser()
			.verifyWith(signKey)
			.build()
			.parseSignedClaims(tokenValue);
	}

	public String extractEmail(Jws<Claims> claimsJws) {
		return claimsJws.getPayload().get("email", String.class);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(jwtProperties.getAuthHeader());

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getBearerType())) {
			return bearerToken.substring(jwtProperties.getBearerType().length()).trim();
		}

		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(TokenType.REFRESH_TOKEN.name())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public Authentication getAuthentication(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(MemberNotFoundException::new);
		PrincipalDetails principalDetails = PrincipalDetails.buildPrincipalDetails(member);

		return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
	}
}
