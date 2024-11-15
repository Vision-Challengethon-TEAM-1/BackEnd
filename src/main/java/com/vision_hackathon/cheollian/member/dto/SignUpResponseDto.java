package com.vision_hackathon.cheollian.member.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SignUpResponseDto {
    private UUID memberId;

    @Builder
    public SignUpResponseDto(UUID memberId) {
        this.memberId = memberId;
    }
}
