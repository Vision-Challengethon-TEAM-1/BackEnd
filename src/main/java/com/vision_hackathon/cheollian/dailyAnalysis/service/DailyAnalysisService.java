package com.vision_hackathon.cheollian.dailyAnalysis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.dailyAnalysis.persistence.DailyAnalysisRepository;
import com.vision_hackathon.cheollian.diet.persistence.DietRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyAnalysisService {
	private final DailyAnalysisRepository dailyAnalysisRepository;
}
