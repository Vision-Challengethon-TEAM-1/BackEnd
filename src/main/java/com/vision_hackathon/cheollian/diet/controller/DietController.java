package com.vision_hackathon.cheollian.diet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageRequest;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageResponse;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.diet.service.DietService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;

	@PostMapping("")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<GetNutritionFromImageResponse>> getNutritionFromImage(
			@RequestPart String request,
			@RequestPart MultipartFile image,
			@LoggedInUser PrincipalDetails principalDetails
			){
		try{
			ObjectMapper mapper = new ObjectMapper();
			GetNutritionFromImageResponse response = dietService.getNutritionFromImage(
					mapper.readValue(request,
					GetNutritionFromImageRequest.class), image, principalDetails.member());
			return ResponseEntity.ok()
					.body(ApiResponse.success(HttpStatus.OK, response));

		} catch (IOException e){
			return ResponseEntity.badRequest()
					.body(null);
		}
	}
}
