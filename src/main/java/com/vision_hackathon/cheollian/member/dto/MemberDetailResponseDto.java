package com.vision_hackathon.cheollian.member.dto;

import java.util.UUID;

import com.vision_hackathon.cheollian.member.entity.Gender;
import com.vision_hackathon.cheollian.member.entity.MemberDetail;

import lombok.Builder;

@Builder
public record MemberDetailResponseDto(
	UUID memberId,
	int age, Gender gender,
	float height, float weight,
	String nickname, String schoolName,
	Integer schoolCode
) {
	public static MemberDetailResponseDto from(MemberDetail memberDetail) {
		if (memberDetail == null) {
			return null;
		}

		return MemberDetailResponseDto.builder()
			.memberId(memberDetail.getMemberId())
			.age(memberDetail.getAge())
			.gender(memberDetail.getGender())
			.height(memberDetail.getHeight())
			.weight(memberDetail.getWeight())
			.nickname(memberDetail.getNickname())
			.schoolName(memberDetail.getSchoolName())
			.schoolCode(memberDetail.getSchoolCode())
			.build();
	}
}
