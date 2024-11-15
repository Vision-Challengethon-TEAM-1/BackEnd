package com.vision_hackathon.cheollian.diet.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageRequest;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageResponse;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromSchoolResponseDto;
import com.vision_hackathon.cheollian.diet.service.DietService;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;

	@PostMapping("")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<GetNutritionFromImageResponse>> getNutritionFromImage(
			@RequestBody GetNutritionFromImageRequest requestBody,
			@LoggedInUser PrincipalDetails principalDetails
	){
		try{
			GetNutritionFromImageResponse response = dietService.getNutritionFromImage(requestBody, principalDetails.member());
			return ResponseEntity.ok()
					.body(ApiResponse.success(HttpStatus.OK, response));

		} catch (IOException e){
			return ResponseEntity.badRequest()
					.body(null);
		}
	}

	@GetMapping("/school")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<GetNutritionFromSchoolResponseDto>> getNutritionFromSchool(
			@RequestParam String date,
			@LoggedInUser PrincipalDetails principalDetails
	){
		return ResponseEntity
				.ok()
				.body(ApiResponse.success(HttpStatus.OK,
						dietService.getNutritionFromSchool(date, principalDetails.member())));
	}
}
