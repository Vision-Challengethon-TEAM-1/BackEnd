package com.vision_hackathon.cheollian.diet.dto;

import com.vision_hackathon.cheollian.diet.entity.DietType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetNutritionFromImageRequest {
    private DietType type;
    private String date;
    private String imageUrl;
}
