package com.vision_hackathon.cheollian.group.dto;

import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.group.entity.Group;
import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.persistence.GroupMemberRepository;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GroupMemberReadDto(
	@NotNull UUID memberId,
	@NotNull String profileImage
) {
	public static List<GroupMemberReadDto> of(Group group, GroupMemberRepository groupMemberRepository) {
		List<GroupMember> groupMembers = groupMemberRepository.findAllByGroup(group);

		if (groupMembers.isEmpty()) {
			return List.of();
		} else {
			return groupMembers.stream()
				.map(groupMember -> GroupMemberReadDto.builder()
					.memberId(groupMember.getMember().getMemberId())
					.profileImage(groupMember.getMember().getProfileImage())
					.build())
				.toList();
		}
	}

}
