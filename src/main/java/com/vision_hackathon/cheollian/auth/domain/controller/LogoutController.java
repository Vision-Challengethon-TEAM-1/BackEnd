package com.vision_hackathon.cheollian.auth.domain.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.auth.domain.service.AuthenticationService;
import com.vision_hackathon.cheollian.auth.jwt.provider.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {
	private final AuthenticationService authenticationService;
	private final JwtTokenService jwtTokenService;

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		String refreshTokenValue = jwtTokenService.resolveRefreshToken(request);
		authenticationService.logout(response, refreshTokenValue);
		return ResponseEntity.ok("Logged out successfully");
	}
}
