package com.vision_hackathon.cheollian.util.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiResponseTest {

	@Test
	void testSuccessWithoutData() {
		ApiSuccessResult<Void> result = ApiResponse.success(HttpStatus.OK);
		assertEquals(HttpStatus.OK.value(), result.status());
		assertNull(result.data());
	}

	@Test
	void testSuccessWithData() {
		String data = "Test Data";
		ApiSuccessResult<String> result = ApiResponse.success(HttpStatus.OK, data);
		assertEquals(HttpStatus.OK.value(), result.status());
		assertEquals(data, result.data());
	}

	@Test
	void testError() {
		ApiErrorResult error = ApiResponse.error(HttpStatus.BAD_REQUEST, "Bad Request");
		assertEquals(HttpStatus.BAD_REQUEST.value(), error.status());
		assertEquals("Bad Request", error.msg());
	}
}
