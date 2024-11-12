package com.vision_hackathon.cheollian.util.api;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ApiResponse {
    public static <T> ApiSuccessResult<T> success(HttpStatus httpStatus, String msg) {
        return new ApiSuccessResult<>(httpStatus.value(), msg, null);
    }

    public static <T> ApiSuccessResult<T> success(HttpStatus httpStatus, String msg, T response) {
        return new ApiSuccessResult<>(httpStatus.value(), msg, response);
    }

    public static ApiErrorResult error(HttpStatus status, String msg) {
        return new ApiErrorResult(status.value(), msg);
    }
}
