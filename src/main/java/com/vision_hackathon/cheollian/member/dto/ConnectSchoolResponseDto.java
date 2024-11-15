package com.vision_hackathon.cheollian.member.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConnectSchoolResponseDto {
    private UUID memberId;

    @Builder
    public ConnectSchoolResponseDto(UUID memberId) {
        this.memberId = memberId;
    }
}
