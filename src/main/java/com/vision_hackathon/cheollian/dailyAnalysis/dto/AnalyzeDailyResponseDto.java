package com.vision_hackathon.cheollian.dailyAnalysis.dto;

import java.util.UUID;

import com.vision_hackathon.cheollian.dailyAnalysis.entity.Praise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private String advice;

}
