package com.vision_hackathon.cheollian.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.member.dto.ConnectSchoolRequestDto;
import com.vision_hackathon.cheollian.member.dto.ConnectSchoolResponseDto;
import com.vision_hackathon.cheollian.member.dto.MemberResponseDto;
import com.vision_hackathon.cheollian.member.dto.SignUpRequestDto;
import com.vision_hackathon.cheollian.member.dto.SignUpResponseDto;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.service.MemberService;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<MemberResponseDto>> getMe(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		Member member = principalDetails.member();
		MemberResponseDto responseBody = MemberResponseDto.from(member);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}

	@PostMapping("sign-up")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<SignUpResponseDto>> signUp(
			@RequestBody SignUpRequestDto request,
			@LoggedInUser PrincipalDetails principalDetails
	){
		return ResponseEntity
				.ok(ApiResponse.success(HttpStatus.OK, memberService.signUp(request, principalDetails.member())));
	}

	@PostMapping("school")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<ConnectSchoolResponseDto>> connectSchool(
			@RequestBody ConnectSchoolRequestDto request,
			@LoggedInUser PrincipalDetails principalDetails
	){
		return ResponseEntity
				.ok(ApiResponse.success(HttpStatus.OK,
						memberService.connectSchool(request, principalDetails.member())
				));
	}
}
