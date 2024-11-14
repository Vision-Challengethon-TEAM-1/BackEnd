package com.vision_hackathon.cheollian.dailyAnalysis.entity;

import java.util.UUID;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;
import com.vision_hackathon.cheollian.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyAnalysis extends BaseAuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID dailyAnalysisId;

	@Column(name = "carbs")
	private Float carbs;

	@Column(name = "fat")
	private Float fat;

	@Column(name = "natrium")
	private Float natrium;

	@Column(name = "kalium")
	private Float kalium;

	@Column(name = "protein")
	private Float protein;

	@Lob
	@Column(name = "advice")
	private String advice;

	@Column(name = "cholesterol")
	private Float cholesterol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public DailyAnalysis(UUID dailyAnalysisId, Float carbs, Float fat, Float natrium, Float kalium, Float protein,
		String advice, Float cholesterol, Member member) {
		this.dailyAnalysisId = dailyAnalysisId;
		this.carbs = carbs;
		this.fat = fat;
		this.natrium = natrium;
		this.kalium = kalium;
		this.protein = protein;
		this.advice = advice;
		this.cholesterol = cholesterol;
		this.member = member;
	}
}
