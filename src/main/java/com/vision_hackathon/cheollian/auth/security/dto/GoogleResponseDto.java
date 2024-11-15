package com.vision_hackathon.cheollian.auth.security.dto;

import java.util.Map;

import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.Role;

public class GoogleResponseDto implements Oauth2ResponseDto {

    private final Map<String, Object> attributes;

    public GoogleResponseDto(Map<String, Object> attribute) {
        this.attributes = attribute;
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getProfileImage() {
        return attributes.get("picture").toString();
    }

    @Override
    public Member toEntity() {
        return Member.builder()
            .email(getEmail())
            .name(getName())
            .profileImage(getProfileImage())
            .role(Role.ROLE_USER)
            .build();
    }
}
