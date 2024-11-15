package com.vision_hackathon.cheollian.group.exception;

import com.vision_hackathon.cheollian.exception.support.business.DuplicatedException;

public class GroupMemberDuplicatedException extends DuplicatedException {
	private static final String errorMsg = "GROUP_MEMBER_DUPLICATED";

	public GroupMemberDuplicatedException() {
		super(errorMsg);
	}
}
