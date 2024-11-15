package com.vision_hackathon.cheollian.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.member.dto.ConnectSchoolRequestDto;
import com.vision_hackathon.cheollian.member.dto.ConnectSchoolResponseDto;
import com.vision_hackathon.cheollian.member.dto.SignUpRequestDto;
import com.vision_hackathon.cheollian.member.dto.SignUpResponseDto;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.MemberDetail;
import com.vision_hackathon.cheollian.member.exception.MemberNotJoinedException;
import com.vision_hackathon.cheollian.member.persistence.MemberDetailRepository;
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
                .schoolCode(request.getSchoolCode())
                .schoolName(request.getSchoolName())
                .build();

        member.connectMemberDetail(memberDetail);
        memberRepository.save(member);

        return SignUpResponseDto.from(member);
    }

    @Transactional
    public ConnectSchoolResponseDto connectSchool(ConnectSchoolRequestDto request, Member member) {
        checkMemberJoined(member);
        member.connectSchool(request.getSchoolName(), request.getSchoolCode(), request.getRegionCode());
        memberRepository.save(member);

        return new ConnectSchoolResponseDto(member.getMemberId());
    }

    private void checkMemberJoined(Member member) {
        if (member.getMemberDetail() == null){
            throw new MemberNotJoinedException();
        }
    }
}
