package com.vision_hackathon.cheollian.group.dto;

import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.group.entity.Group;
import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.persistence.GroupMemberRepository;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GroupMemberCalReadDto(
	@NotNull String nickname,
	@NotNull String profileImage,
	@NotNull Integer kcal,
	@NotNull Integer point
) {
}
