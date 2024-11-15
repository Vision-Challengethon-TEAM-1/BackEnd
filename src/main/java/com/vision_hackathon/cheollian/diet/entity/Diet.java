package com.vision_hackathon.cheollian.diet.entity;

import java.util.UUID;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;
import com.vision_hackathon.cheollian.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet extends BaseAuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID dietId;

	private String date;

	private int totalKcal;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Diet( int totalKcal, Float carbs, Float protein, Float fat, Float natrium, Float kalium, Float cholesterol,
		Float vitaminA, Float vitaminB, Float vitaminC, DietType type, String image, Member member, String date) {
		this.dietId = UUID.randomUUID();
		this.totalKcal = totalKcal;
		this.carbs = carbs;
		this.protein = protein;
		this.fat = fat;
		this.natrium = natrium;
		this.kalium = kalium;
		this.cholesterol = cholesterol;
		this.vitaminA = vitaminA;
		this.vitaminB = vitaminB;
		this.vitaminC = vitaminC;
		this.type = type;
		this.image = image;
		this.member = member;
		this.date = date;
	}
}
