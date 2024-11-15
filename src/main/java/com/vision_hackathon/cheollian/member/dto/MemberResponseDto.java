package com.vision_hackathon.cheollian.member.dto;

import java.util.UUID;

import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.Role;

import lombok.Builder;

@Builder
public record MemberResponseDto(
	UUID memberId, String email,
	String name, String profileImage,
	Role role, MemberDetailResponseDto memberDetail
) {
	public static MemberResponseDto from(Member member) {
		return MemberResponseDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.name(member.getName())
			.profileImage(member.getProfileImage())
			.role(member.getRole())
			.memberDetail(MemberDetailResponseDto.from(member.getMemberDetail()))
			.build();
	}
}
