package com.vision_hackathon.cheollian.diet.dto;

import com.vision_hackathon.cheollian.diet.entity.DietType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetNutritionFromImageResponse {
    private int totalKcal;

    private float carbs;

    private float protein;

    private float fat;

    private float kalium;

    private float natrium;

    private float cholesterol;

    private float vitaminA;

    private float vitaminB;

    private float vitaminC;

    private String date;

    private DietType type;
}
