package com.vision_hackathon.cheollian.storage.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class FileNotFoundException extends NotFoundException {
	private static final String errorMsg = "FILE_NOT_FOUND";

	public FileNotFoundException() {
		super(errorMsg);
	}
}
