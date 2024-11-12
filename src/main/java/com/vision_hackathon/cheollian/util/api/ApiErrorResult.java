package com.vision_hackathon.cheollian.util.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResult(int status, String msg) {
}
