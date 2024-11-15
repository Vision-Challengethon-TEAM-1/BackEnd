package com.vision_hackathon.cheollian.group.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.auth.security.LoggedInUser;
import com.vision_hackathon.cheollian.auth.security.details.PrincipalDetails;
import com.vision_hackathon.cheollian.group.dto.GroupCreateDto;
import com.vision_hackathon.cheollian.group.dto.GroupMemberCalReadDto;
import com.vision_hackathon.cheollian.group.dto.GroupReadDto;
import com.vision_hackathon.cheollian.group.service.GroupService;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.util.api.ApiResponse;
import com.vision_hackathon.cheollian.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<GroupReadDto>>> getMyGroups(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		Member member = principalDetails.member();
		List<GroupReadDto> responseBody = groupService.getMyGroups(member);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}


	@GetMapping("/{groupId}/{date}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<GroupMemberCalReadDto>>> getMembersOfGroup(
		@PathVariable("groupId") UUID groupId,
		@PathVariable("date") String date
	) {
		List<GroupMemberCalReadDto> responseBody = groupService.getMembersOfGroup(groupId, date);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}


	@GetMapping("/")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<GroupReadDto>>> getPublicGroups(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		Member member = principalDetails.member();
		List<GroupReadDto> responseBody = groupService.getPublicGroups(member);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}


	@PostMapping()
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<GroupReadDto>> getMyGroups(
		@LoggedInUser PrincipalDetails principalDetails,
		@RequestBody GroupCreateDto groupCreateDto
	) {
		Member member = principalDetails.member();
		GroupReadDto responseBody = groupService.createGroup(member, groupCreateDto);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}


	@PostMapping("/{groupId}/join")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<?>> joinGroup(
		@LoggedInUser PrincipalDetails principalDetails,
		@PathVariable("groupId") UUID groupId
	) {
		Member member = principalDetails.member();
		groupService.addMemberInGroup(groupId, member);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.OK));
	}


}
