package com.vision_hackathon.cheollian.dailyAnalysis.service;

import com.vision_hackathon.cheollian.dailyAnalysis.dto.AnalyzeDailyResponseDto;
import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;
import com.vision_hackathon.cheollian.diet.entity.Diet;
import com.vision_hackathon.cheollian.diet.entity.DietType;
import com.vision_hackathon.cheollian.diet.persistence.DietRepository;
import com.vision_hackathon.cheollian.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.dailyAnalysis.persistence.DailyAnalysisRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyAnalysisService {
	private final DailyAnalysisRepository dailyAnalysisRepository;
    private final DietRepository dietRepository;

    @Transactional
    public AnalyzeDailyResponseDto analyzeDaily(String date, Member member) {
        Optional<DailyAnalysis> existedAnalysis = dailyAnalysisRepository.findByDateAndMember(date, member);

        if (existedAnalysis.isPresent()) {
            DailyAnalysis dailyAnalysis = existedAnalysis.get();
            return AnalyzeDailyResponseDto.builder()
                    .breakfastKcal(dailyAnalysis.getBreakfastKcal())
                    .lunchKcal(dailyAnalysis.getLunchKcal())
                    .dinnerKcal(dailyAnalysis.getDinnerKcal())
                    .dailyAnalysisId(dailyAnalysis.getDailyAnalysisId())
                    .praise(dailyAnalysis.getPraise())
                    .memberId(member.getMemberId())
                    .date(date)
                    .build();
        }

        List<Diet> diets = dietRepository.findDietsByDate(date);

        Integer breakfastKcal = null;
        Integer lunchKcal = null;
        Integer dinnerKcal = null;

        Map<DietType, Diet> dietMap = diets.stream()
                .collect(Collectors.toMap(
                        Diet::getType,
                        diet -> diet
                ));


        if (dietMap.get(DietType.BREAKFAST) != null){
            breakfastKcal = dietMap.get(DietType.BREAKFAST).getTotalKcal();
        }
        if (dietMap.get(DietType.LUNCH) != null) {
            lunchKcal = dietMap.get(DietType.LUNCH).getTotalKcal();
        }
        if (dietMap.get(DietType.DINNER) != null) {
            dinnerKcal = dietMap.get(DietType.DINNER).getTotalKcal();
        }

        DailyAnalysis dailyAnalysis = DailyAnalysis.builder()
                .breakfastKcal(breakfastKcal)
                .lunchKcal(lunchKcal)
                .dinnerKcal(dinnerKcal)
                .member(member)
                .date(date)
                .build();

        dailyAnalysisRepository.save(dailyAnalysis);

        return AnalyzeDailyResponseDto.builder()
                .breakfastKcal(dailyAnalysis.getBreakfastKcal())
                .lunchKcal(dailyAnalysis.getLunchKcal())
                .dinnerKcal(dailyAnalysis.getDinnerKcal())
                .dailyAnalysisId(dailyAnalysis.getDailyAnalysisId())
                .memberId(member.getMemberId())
                .praise(dailyAnalysis.getPraise())
                .date(date)
                .build();
    }
}
