package com.vision_hackathon.cheollian.auth.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vision_hackathon.cheollian.auth.config.AuthProperties;
import com.vision_hackathon.cheollian.auth.domain.service.AuthenticationService;
import com.vision_hackathon.cheollian.auth.jwt.dto.Token;
import com.vision_hackathon.cheollian.auth.jwt.dto.TokenType;
import com.vision_hackathon.cheollian.auth.jwt.provider.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthProperties oauth2Properties;
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Generate tokens
        // authenticationService.issueToken(response, authentication);

        Token accessToken = jwtTokenService.generateToken(authentication, TokenType.ACCESS_TOKEN);
        Token refreshToken = jwtTokenService.generateToken(authentication, TokenType.REFRESH_TOKEN);

        authenticationService.updateRefreshToken(authentication.getName(), refreshToken);

        boolean hasMemberDetails = authenticationService.hasMemberDetails(authentication.getName());

		String redirectUrl = oauth2Properties.getRedirectUri()
			+ "?hasMemberDetails="
			+ hasMemberDetails
            + "&accessToken="
            + accessToken.value()
            + "&refreshToken="
            + refreshToken.value();

        // Redirect to the constructed URL
        response.sendRedirect(redirectUrl);

        log.info("OAuth2 login successful. Email: {}", authentication.getName());
    }
}
