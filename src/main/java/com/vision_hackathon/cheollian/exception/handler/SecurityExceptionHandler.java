package com.vision_hackathon.cheollian.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vision_hackathon.cheollian.exception.ExceptionHandlerOrder;
import com.vision_hackathon.cheollian.util.api.ApiErrorResult;
import com.vision_hackathon.cheollian.exception.BaseExceptionHandler;
import com.vision_hackathon.cheollian.exception.support.business.ApplicationLogicException;
import com.vision_hackathon.cheollian.exception.support.business.BadRequestException;
import com.vision_hackathon.cheollian.exception.support.business.DuplicatedException;
import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.SECURITY_EXCEPTION_HANDLER)
public class SecurityExceptionHandler extends BaseExceptionHandler<Exception> {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResult> handleBadRequestException(BadRequestException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(DuplicatedException.class)
	public ResponseEntity<ApiErrorResult> handleDuplicatedException(DuplicatedException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorResult> handleNotFoundException(NotFoundException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(ApplicationLogicException.class)
	public ResponseEntity<ApiErrorResult> handleApplicationLogicException(ApplicationLogicException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}
}
