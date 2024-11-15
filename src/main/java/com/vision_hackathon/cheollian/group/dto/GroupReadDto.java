package com.vision_hackathon.cheollian.group.dto;

import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.group.entity.Group;
import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.persistence.GroupMemberRepository;
import com.vision_hackathon.cheollian.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GroupReadDto(
	@NotNull UUID groupId,
	@NotNull String groupName,
	@NotNull Boolean isPublic,
	@NotNull List<GroupMemberReadDto> groupMembers
	) {
	public static GroupReadDto of(Group group, GroupMemberRepository groupMemberRepository) {
		return GroupReadDto.builder()
			.groupId(group.getGroupId())
			.groupName(group.getGroupName())
			.isPublic(group.getIsPublic())
			.groupMembers(GroupMemberReadDto.of(group, groupMemberRepository))
			.build();
	}


	public static List<GroupReadDto> of(List<GroupMember> groupMemberList, GroupMemberRepository groupMemberRepository) {
		return groupMemberList.stream()
			.map(GroupMember::getGroup)
			.map(group -> GroupReadDto.of(group, groupMemberRepository))
			.toList();
	}
}
