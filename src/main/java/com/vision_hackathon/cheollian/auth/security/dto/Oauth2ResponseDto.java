package com.vision_hackathon.cheollian.auth.security.dto;

import com.vision_hackathon.cheollian.member.entity.Member;

public interface Oauth2ResponseDto {
    String getEmail();
    String getName();
    Member toEntity();
}
