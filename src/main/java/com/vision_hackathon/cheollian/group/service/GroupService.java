package com.vision_hackathon.cheollian.group.service;

import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;
import com.vision_hackathon.cheollian.dailyAnalysis.exception.DailyAnalysisNotFoundException;
import com.vision_hackathon.cheollian.dailyAnalysis.persistence.DailyAnalysisRepository;
import com.vision_hackathon.cheollian.diet.persistence.DietRepository;
import com.vision_hackathon.cheollian.group.dto.GetGroupDietResponseDto;
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
	private final DietRepository dietRepository;
	private final DailyAnalysisRepository dailyAnalysisRepository;

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

	public List<GroupReadDto> getPublicGroups(Member member) {
		List<Group> groupList = groupRepository.findAllByIsPublic(true);

		return groupList.stream()
			.filter(group -> !groupMemberRepository.existsByGroupAndMember(group, member))
			.map(group -> GroupReadDto.of(group, groupMemberRepository))
			.toList();
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

    public List<GetGroupDietResponseDto> getGroupDiet(UUID groupId, String date) {
		Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);

		List<GroupMember> members = groupMemberRepository.findAllByGroup(group);

		List<DailyAnalysis> dailyAnalyses = members.stream().map(
				groupMember -> dailyAnalysisRepository.findByDateAndMember(date, groupMember.getMember()).orElseThrow(DailyAnalysisNotFoundException::new)
		).toList();

		List<String> foodImages = dietRepository.findDietsByDate(date)
				.stream().map(
						diet -> diet.getImage()
				).toList();

        return dailyAnalyses.stream().map(
				daily -> GetGroupDietResponseDto.builder()
						.breakfastKcal(daily.getBreakfastKcal())
						.lunchKcal(daily.getLunchKcal())
						.dinnerKcal(daily.getDinnerKcal())
						.memberId(daily.getMember().getMemberId())
						.images(foodImages)
						.build()
		).toList();


	}
}
