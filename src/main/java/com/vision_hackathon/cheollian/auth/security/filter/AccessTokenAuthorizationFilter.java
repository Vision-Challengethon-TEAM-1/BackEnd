package com.vision_hackathon.cheollian.auth.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.vision_hackathon.cheollian.auth.jwt.provider.JwtTokenService;
import com.vision_hackathon.cheollian.auth.security.exception.AccessTokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class AccessTokenAuthorizationFilter extends GenericFilterBean {
    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

		if ("OPTIONS".equalsIgnoreCase(servletRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = jwtTokenService.resolveAccessToken(servletRequest);
            log.info("accessToken: {}", accessToken);
            if (accessToken != null) {
                Jws<Claims> claims = jwtTokenService.extractClaims(accessToken);
                String email = jwtTokenService.extractEmail(claims);
                Authentication authentication = jwtTokenService.getAuthentication(email);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("Access token has expired: {}", e.getMessage());
            chain.doFilter(request, response);
        } catch (JwtException e) {
            log.warn("Invalid access token: {}", e.getMessage());
            throw new AccessTokenInvalidException();
        } catch (Exception e) {
            log.error("Error in AccessTokenValidationFilter: {}", e.getMessage(), e);
        }
    }
}
