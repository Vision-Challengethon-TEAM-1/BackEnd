package com.vision_hackathon.cheollian.diet.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class DietNotFoundException extends NotFoundException {
	private static final String errorMsg = "DIET_NOT_FOUND";

	public DietNotFoundException() {
		super(errorMsg);
	}
}
