package com.vision_hackathon.cheollian.member.dto;

import com.vision_hackathon.cheollian.member.entity.Gender;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String nickname;

    private String profileImage;

    private float height;

    private float weight;

    private int age;

    private Gender gender;

    private String schoolName;

    private int schoolCode;
}
