package com.vision_hackathon.cheollian.member.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
	private static final String errorMsg = "MEMBER_NOT_FOUND";

	public MemberNotFoundException() {
		super(errorMsg);
	}
}
