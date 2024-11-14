package com.vision_hackathon.cheollian.diet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision_hackathon.cheollian.diet.service.DietService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;
}
