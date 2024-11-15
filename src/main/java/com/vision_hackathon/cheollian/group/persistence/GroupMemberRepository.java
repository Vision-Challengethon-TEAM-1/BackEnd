package com.vision_hackathon.cheollian.group.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.group.entity.Group;
import com.vision_hackathon.cheollian.group.entity.GroupMember;
import com.vision_hackathon.cheollian.group.entity.GroupMemberId;
import com.vision_hackathon.cheollian.member.entity.Member;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
	List<GroupMember> findAllByGroup(Group group);
	List<GroupMember> findAllByMember(Member member);
	boolean existsByGroupAndMember(Group group, Member member);
}
