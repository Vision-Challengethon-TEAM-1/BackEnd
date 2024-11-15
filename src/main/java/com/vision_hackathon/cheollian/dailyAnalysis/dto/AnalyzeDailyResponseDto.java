package com.vision_hackathon.cheollian.dailyAnalysis.dto;

import com.vision_hackathon.cheollian.dailyAnalysis.entity.Praise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnalyzeDailyResponseDto {

    private UUID dailyAnalysisId;

    private UUID memberId;

    private Integer breakfastKcal;

    private Integer lunchKcal;

    private Integer dinnerKcal;

    private Praise praise;

    private String date;

}
