package com.vision_hackathon.cheollian.auth.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.vision_hackathon.cheollian.auth.domain.service.AuthenticationService;
import com.vision_hackathon.cheollian.auth.jwt.provider.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
@Component
public class RefreshTokenFilter extends GenericFilterBean {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationService authenticationService;

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        String refreshTokenValue = "";
        try {
            refreshTokenValue = jwtTokenService.resolveRefreshToken(servletRequest);
            if (refreshTokenValue != null) {
                Jws<Claims> refreshClaims = jwtTokenService.extractClaims(refreshTokenValue);
                String email = jwtTokenService.extractEmail(refreshClaims);

                if (authenticationService.validateRefreshToken(email, refreshTokenValue)) {
                    Authentication authentication = jwtTokenService.getAuthentication(email);
                    authenticationService.issueToken(servletResponse, authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    authenticationService.logout(servletResponse, refreshTokenValue);
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("Refresh token has expired: {}", e.getMessage());
            authenticationService.logout(servletResponse, refreshTokenValue);
        } catch (JwtException e) {
            log.warn("Invalid refresh token: {}", e.getMessage());
            authenticationService.logout(servletResponse, refreshTokenValue);
        } catch (Exception e) {
            log.error("Error in RefreshTokenFilter: {}", e.getMessage(), e);
        }
    }
}
