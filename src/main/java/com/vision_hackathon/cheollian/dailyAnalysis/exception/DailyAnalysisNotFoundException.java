package com.vision_hackathon.cheollian.dailyAnalysis.exception;

import com.vision_hackathon.cheollian.exception.support.business.NotFoundException;

public class DailyAnalysisNotFoundException extends NotFoundException {
	private static final String errorMsg = "DAILY_ANALYSIS_NOT_FOUND";

	public DailyAnalysisNotFoundException() {
		super(errorMsg);
	}
}
