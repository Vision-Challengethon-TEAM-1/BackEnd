package com.vision_hackathon.cheollian.group.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.entity.GroupMemberId;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}
