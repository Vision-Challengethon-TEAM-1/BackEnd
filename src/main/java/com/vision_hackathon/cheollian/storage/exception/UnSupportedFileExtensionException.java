package com.vision_hackathon.cheollian.storage.exception;

import com.vision_hackathon.cheollian.exception.support.business.BadRequestException;

public class UnSupportedFileExtensionException extends BadRequestException {
	private static final String errorMsg = "UNSUPPORTED_FILE_EXTENSION";

	public UnSupportedFileExtensionException() {
		super(errorMsg);
	}
}
