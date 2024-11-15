package com.vision_hackathon.cheollian.member.dto;

import java.util.UUID;

import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.MemberDetail;

import lombok.Builder;

@Builder
public record SignUpResponseDto(
    UUID memberId,
    String email,
    String name,
    String profileImage,
    String role,
    int age,
    String gender,
    float height,
    float weight,
    String nickname,
    String schoolName,
    Integer schoolCode
) {
    public static SignUpResponseDto from(Member member) {
        MemberDetail memberDetail = member.getMemberDetail();
        return SignUpResponseDto.builder()
            .memberId(member.getMemberId())
            .email(member.getEmail())
            .name(member.getName())
            .profileImage(member.getProfileImage())
            .role(member.getRole().name())
            .age(memberDetail.getAge())
            .gender(memberDetail.getGender().name())
            .height(memberDetail.getHeight())
            .weight(memberDetail.getWeight())
            .nickname(memberDetail.getNickname())
            .schoolName(memberDetail.getSchoolName())
            .schoolCode(memberDetail.getSchoolCode())
            .build();
    }
}
