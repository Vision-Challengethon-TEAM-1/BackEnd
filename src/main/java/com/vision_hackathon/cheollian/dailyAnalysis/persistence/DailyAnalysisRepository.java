package com.vision_hackathon.cheollian.dailyAnalysis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vision_hackathon.cheollian.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;

public interface DailyAnalysisRepository extends JpaRepository<DailyAnalysis, UUID> {

    public Optional<DailyAnalysis> findByDateAndMember(String date, Member member);

    public List<DailyAnalysis> findByDateStartingWithAndMember(String yearMonth, Member member);
}
