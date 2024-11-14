package com.vision_hackathon.cheollian.group.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class GroupNotFoundException extends NotFoundException {
	private static final String errorMsg = "GROUP_NOT_FOUND";

	public GroupNotFoundException() {
		super(errorMsg);
	}
}
