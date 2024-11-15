package com.vision_hackathon.cheollian.member.service;

import com.vision_hackathon.cheollian.member.dto.SignUpRequestDto;
import com.vision_hackathon.cheollian.member.dto.SignUpResponseDto;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.MemberDetail;
import com.vision_hackathon.cheollian.member.persistence.MemberDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.member.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto request, Member member) {

        MemberDetail memberDetail = MemberDetail.builder()
                .memberId(member.getMemberId())
                .member(member)
                .age(request.getAge())
                .gender(request.getGender())
                .height(request.getHeight())
                .weight(request.getWeight())
                .nickname(request.getNickname())
                .profileImage(request.getProfileImage())
                .schoolCode(request.getSchoolCode())
                .schoolName(request.getSchoolName())
                .build();

        member.connectMemberDetail(memberDetail);
        memberRepository.save(member);

        return new SignUpResponseDto(member.getMemberId());
    }
}
