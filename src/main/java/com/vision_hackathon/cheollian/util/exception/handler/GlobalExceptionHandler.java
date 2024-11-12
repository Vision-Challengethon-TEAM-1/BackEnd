package com.vision_hackathon.cheollian.util.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vision_hackathon.cheollian.util.api.ApiErrorResult;
import com.vision_hackathon.cheollian.util.exception.BaseExceptionHandler;
import com.vision_hackathon.cheollian.util.exception.ExceptionHandlerOrder;
import com.vision_hackathon.cheollian.util.exception.support.global.GlobalException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.CUSTOM_EXCEPTION_HANDLER)
public class GlobalExceptionHandler extends BaseExceptionHandler<GlobalException> {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ApiErrorResult> handleCustomException(GlobalException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}
}
