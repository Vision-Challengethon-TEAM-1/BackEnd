package com.vision_hackathon.cheollian.dailyAnalysis.controller;

import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.dailyAnalysis.dto.AnalyzeMonthlyResponseDto;
import com.vision_hackathon.cheollian.dailyAnalysis.service.MonthlyAnalysisService;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class MonthlyAnalysisController {

    private final MonthlyAnalysisService monthlyAnalysisService;

    @GetMapping("monthly")
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<AnalyzeMonthlyResponseDto>> analyzeMonthly (
            @RequestParam String yearMonth,
            @LoggedInUser PrincipalDetails principalDetails
    ){
        AnalyzeMonthlyResponseDto response = monthlyAnalysisService.analyzeMonthly(yearMonth, principalDetails.member());

        return ResponseEntity
                .ok(ApiResponse.success(HttpStatus.OK,
                        response));
    }
}
