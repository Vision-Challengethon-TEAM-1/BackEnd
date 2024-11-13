package com.vision_hackathon.cheollian.auth.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;

import jakarta.annotation.Nonnull;

@Component
public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(@Nonnull MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoggedInUser.class)
			&& parameter.getParameterType().equals(String.class);
	}

	@Override
	public PrincipalDetails resolveArgument(
		@Nonnull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@Nonnull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		Authentication authentication = SecurityContextHolder
			.getContext()
			.getAuthentication();

		return (PrincipalDetails) authentication.getPrincipal();
	}
}