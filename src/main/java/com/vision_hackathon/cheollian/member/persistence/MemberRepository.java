package com.vision_hackathon.cheollian.member.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
