package com.vision_hackathon.cheollian.util.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiResponseTest {

	@Test
	void testSuccessWithoutData() {
		ApiSuccessResult<Void> result = ApiResponse.success(HttpStatus.OK, "Success");
		assertEquals(HttpStatus.OK.value(), result.status());
		assertEquals("Success", result.msg());
		assertNull(result.data());
	}

	@Test
	void testSuccessWithData() {
		String data = "Test Data";
		ApiSuccessResult<String> result = ApiResponse.success(HttpStatus.OK, "Success", data);
		assertEquals(HttpStatus.OK.value(), result.status());
		assertEquals("Success", result.msg());
		assertEquals(data, result.data());
	}

	@Test
	void testError() {
		ApiErrorResult error = ApiResponse.error(HttpStatus.BAD_REQUEST, "Bad Request");
		assertEquals(HttpStatus.BAD_REQUEST.value(), error.status());
		assertEquals("Bad Request", error.msg());
	}
}
