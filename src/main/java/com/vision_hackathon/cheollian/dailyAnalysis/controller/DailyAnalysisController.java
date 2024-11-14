package com.vision_hackathon.cheollian.dailyAnalysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.dailyAnalysis.service.DailyAnalysisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DailyAnalysisController {
	private final DailyAnalysisService dailyAnalysisService;
}
