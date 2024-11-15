package com.vision_hackathon.cheollian.dailyAnalysis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vision_hackathon.cheollian.dailyAnalysis.dto.AnalyzeMonthlyResponseDto;
import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;
import com.vision_hackathon.cheollian.dailyAnalysis.entity.Praise;
import com.vision_hackathon.cheollian.dailyAnalysis.persistence.DailyAnalysisRepository;
import com.vision_hackathon.cheollian.member.entity.Member;

@Service
public class MonthlyAnalysisService {

    private final DailyAnalysisRepository dailyAnalysisRepository;

    public MonthlyAnalysisService(DailyAnalysisRepository dailyAnalysisRepository) {
        this.dailyAnalysisRepository = dailyAnalysisRepository;
    }

    public AnalyzeMonthlyResponseDto analyzeMonthly(String yearMonth, Member member) {
        List<DailyAnalysis> analyses = dailyAnalysisRepository.findByDateStartingWithAndMember(yearMonth, member);

        int excellentCnt = Long.valueOf(analyses.stream().filter(
                analyse -> analyse.getPraise() == Praise.EXCELLENT
        ).count()).intValue();

        int goodCnt = Long.valueOf(analyses.stream().filter(
                analyse -> analyse.getPraise() == Praise.GOOD
        ).count()).intValue();

        int badCnt = Long.valueOf(analyses.stream().filter(
                analyse -> analyse.getPraise() == Praise.BAD
        ).count()).intValue();

        return AnalyzeMonthlyResponseDto.builder()
                .excellent(excellentCnt)
                .good(goodCnt)
                .bad(badCnt)
                .build();






    }
}
