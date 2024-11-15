package com.vision_hackathon.cheollian.storage.exception;

import com.vision_hackathon.cheollian.exception.support.business.BadRequestException;

public class FailedFileStoreException extends BadRequestException {
	private static final String errorMsg = "FAILED_FILE_STORE";

	public FailedFileStoreException() {
		super(errorMsg);
	}
}
