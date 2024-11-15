package com.vision_hackathon.cheollian.group.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class GetGroupDietResponseDto {
    private UUID memberId;

    private Integer breakfastKcal;

    private Integer lunchKcal;

    private Integer dinnerKcal;

    private List<String> images;
}
