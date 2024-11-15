package com.vision_hackathon.cheollian.dailyAnalysis.entity;

import java.util.UUID;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;
import com.vision_hackathon.cheollian.member.entity.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyAnalysis extends BaseAuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID dailyAnalysisId;

	private String date;

	@Nullable
	private Integer breakfastKcal;

	@Nullable
	private Integer lunchKcal;

	@Nullable
	private Integer dinnerKcal;

	private Praise praise;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Lob
	private String advice;

	@Builder
	public DailyAnalysis(Integer breakfastKcal, Integer lunchKcal, Integer dinnerKcal, Member member, String date, String advice) {
		this.dailyAnalysisId = UUID.randomUUID();
		this.breakfastKcal = breakfastKcal;
		this.lunchKcal = lunchKcal;
		this.dinnerKcal = dinnerKcal;
		this.praise = generatePraise(breakfastKcal, lunchKcal, dinnerKcal);
		this.member = member;
		this.date = date;
		this.advice = advice;
	}

	private Praise generatePraise(Integer breakfastKcal, Integer lunchKcal, Integer dinnerKcal) {

		Integer totalKcal = 0;

		if (breakfastKcal != null) {
			totalKcal += breakfastKcal;
		}
		if (lunchKcal != null) {
			totalKcal += lunchKcal;
		}
		if (dinnerKcal != null) {
			totalKcal += dinnerKcal;
		}


		int diff = Math.abs(1800 - totalKcal);

		if (diff >= 0 && diff <= 200){
			return Praise.EXCELLENT;
		} else if (diff > 200 && diff <= 800) {
			return Praise.GOOD;
		}else {
			return Praise.BAD;
		}
	}
}
