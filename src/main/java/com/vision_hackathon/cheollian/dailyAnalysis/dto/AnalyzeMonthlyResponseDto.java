package com.vision_hackathon.cheollian.dailyAnalysis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnalyzeMonthlyResponseDto {

    private int excellent;

    private int good;

    private int bad;
}
