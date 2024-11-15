package com.vision_hackathon.cheollian.storage.exception;

import com.vision_hackathon.cheollian.exception.support.business.BadRequestException;

public class InvalidFilePathException extends BadRequestException {
	private static final String errorMsg = "INVALID_FILE_PATH";

	public InvalidFilePathException() {
		super(errorMsg);
	}
}
