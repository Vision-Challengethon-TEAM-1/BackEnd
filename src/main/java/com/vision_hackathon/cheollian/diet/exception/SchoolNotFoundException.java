package com.vision_hackathon.cheollian.diet.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class SchoolNotFoundException extends NotFoundException {
    private static final String errorMsg = "SCHOOL_NOT_FOUND";

    public SchoolNotFoundException() {
        super(errorMsg);
    }
}
