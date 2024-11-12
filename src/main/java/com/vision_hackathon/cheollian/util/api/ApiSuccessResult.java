package com.vision_hackathon.cheollian.util.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiSuccessResult<T>(int status, String msg, T data) {
}
