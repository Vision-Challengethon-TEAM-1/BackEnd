package com.vision_hackathon.cheollian.group.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.group.dto.GroupCreateDto;
import com.vision_hackathon.cheollian.group.dto.GroupReadDto;
import com.vision_hackathon.cheollian.group.entity.Group;
import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.entity.GroupMemberRole;
import com.vision_hackathon.cheollian.group.exception.GroupMemberDuplicatedException;
import com.vision_hackathon.cheollian.group.exception.GroupNotFoundException;
import com.vision_hackathon.cheollian.group.persistence.GroupMemberRepository;
import com.vision_hackathon.cheollian.group.persistence.GroupRepository;
import com.vision_hackathon.cheollian.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;

	@Transactional
	public GroupReadDto createGroup(Member member, GroupCreateDto createDto) {
		Group group = groupRepository.save(createDto.toEntity());
		groupMemberRepository.save(GroupMember.builder()
			.group(group)
			.member(member)
			.role(GroupMemberRole.GROUP_ADMIN)
			.build());

		return GroupReadDto.of(group, groupMemberRepository);
	}

	public List<GroupReadDto> getMyGroups(Member member) {
		List<GroupMember> groupMemberList = groupMemberRepository.findAllByMember(member);
		return GroupReadDto.of(groupMemberList, groupMemberRepository);
	}

	@Transactional
	public void addMemberInGroup(UUID groupId, Member member) {
		Group group = groupRepository.findById(groupId)
			.orElseThrow(GroupNotFoundException::new);

		if (groupMemberRepository.existsByGroupAndMember(group, member)) {
			throw new GroupMemberDuplicatedException();
		}

		groupMemberRepository.save(GroupMember.builder()
			.group(group)
			.member(member)
			.role(GroupMemberRole.GROUP_MEMBER)
			.build());
	}

}
