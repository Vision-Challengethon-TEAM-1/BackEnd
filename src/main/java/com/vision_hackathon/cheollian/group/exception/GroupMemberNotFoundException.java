package com.vision_hackathon.cheollian.group.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class GroupMemberNotFoundException extends NotFoundException {
	private static final String errorMsg = "GROUP_MEMBER_NOT_FOUND";

	public GroupMemberNotFoundException() {
		super(errorMsg);
	}
}
