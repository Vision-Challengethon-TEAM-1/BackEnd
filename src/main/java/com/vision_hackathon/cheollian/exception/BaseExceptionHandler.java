package com.vision_hackathon.cheollian.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vision_hackathon.cheollian.util.api.ApiErrorResult;
import com.vision_hackathon.cheollian.util.api.ApiResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BaseExceptionHandler<T extends Throwable> {

	protected ResponseEntity<ApiErrorResult> handleException(T exception, HttpStatus status, String errorMsg) {
		if (status.is4xxClientError()) {
			log.info("Client error: {}", exception.getMessage(), exception);
		} else if (status.is5xxServerError()) {
			log.error("Server error: {}", exception.getMessage(), exception);
		} else {
			log.warn("Unexpected status {}: {}", status, exception.getMessage(), exception);
		}
		return ResponseEntity.status(status)
			.body(ApiResponse.error(status, errorMsg));
	}
}
