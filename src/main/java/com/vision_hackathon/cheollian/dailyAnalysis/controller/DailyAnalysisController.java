package com.vision_hackathon.cheollian.dailyAnalysis.controller;

import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.dailyAnalysis.dto.AnalyzeDailyResponseDto;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.dailyAnalysis.service.DailyAnalysisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DailyAnalysisController {
	private final DailyAnalysisService dailyAnalysisService;

	@GetMapping("daily")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<AnalyzeDailyResponseDto>> analyzeDaily(
			@RequestParam String date,
			@LoggedInUser PrincipalDetails principalDetails

	) {
		AnalyzeDailyResponseDto response = dailyAnalysisService.analyzeDaily(date, principalDetails.member());

		return ResponseEntity
				.ok(ApiResponse.success(HttpStatus.OK,
						response));
	}


}
