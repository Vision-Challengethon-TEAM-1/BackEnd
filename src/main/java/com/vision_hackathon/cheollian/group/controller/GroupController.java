package com.vision_hackathon.cheollian.group.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.group.service.GroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;
}
