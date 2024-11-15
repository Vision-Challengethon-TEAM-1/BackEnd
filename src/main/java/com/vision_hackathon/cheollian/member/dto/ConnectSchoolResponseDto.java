package com.vision_hackathon.cheollian.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ConnectSchoolResponseDto {
    private UUID memberId;

    @Builder
    public ConnectSchoolResponseDto(UUID memberId) {
        this.memberId = memberId;
    }
}
