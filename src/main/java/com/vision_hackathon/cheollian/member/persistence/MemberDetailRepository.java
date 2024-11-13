package com.vision_hackathon.cheollian.member.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.member.entity.MemberDetail;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, UUID> {
	boolean existsByMemberEmail(String email);
}
