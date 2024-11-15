package com.vision_hackathon.cheollian.group.dto;

import com.vision_hackathon.cheollian.group.entity.Group;

import jakarta.validation.constraints.NotNull;

public record GroupCreateDto(
	@NotNull String groupName,
	@NotNull Boolean isPublic
) {
	public Group toEntity() {
		return Group.builder()
			.groupName(this.groupName)
			.isPublic(this.isPublic)
			.build();
	}
}
