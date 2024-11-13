package com.vision_hackathon.cheollian.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {
	private String redirectUri;
}