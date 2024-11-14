package com.vision_hackathon.cheollian.diet.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID dietId;

	@Column(name = "carbs")
	private Float carbs; // 탄수화물

	@Column(name = "protein")
	private Float protein; // 단백질

	@Column(name = "fat")
	private Float fat; // 지방

	@Column(name = "natrium")
	private Float natrium; // 나트륨

	@Column(name = "kalium")
	private Float kalium; // 칼륨

	@Column(name = "cholesterol")
	private Float cholesterol; // 콜레스테롤

	@Column(name = "vitamin_a")
	private Float vitaminA; // 비타민 A

	@Column(name = "vitamin_b")
	private Float vitaminB; // 비타민 B

	@Column(name = "vitamin_c")
	private Float vitaminC; // 비타민 C

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private DietType type; // 식사 종류 (아침, 점심, 저녁)


	@Size(max = 256, message = "Diet image URL must not exceed 256 characters")
	@Pattern(
		regexp = "^(https?://).+",
		message = "Diet image must be a valid URL starting with http:// or https://"
	)
	@Column(name = "image", nullable = false, length = 256)
	private String image;

}
