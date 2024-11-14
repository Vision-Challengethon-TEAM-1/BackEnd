package com.vision_hackathon.cheollian.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.vision_hackathon.cheollian.diet.entity.DietType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetNutritionFromImageRequest {

    private DietType type;

    private String date;
}
