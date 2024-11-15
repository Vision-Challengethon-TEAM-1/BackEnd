package com.vision_hackathon.cheollian.dailyAnalysis.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;

public interface DailyAnalysisRepository extends JpaRepository<DailyAnalysis, UUID> {

    public Optional<DailyAnalysis> findByDate(String date);

}
