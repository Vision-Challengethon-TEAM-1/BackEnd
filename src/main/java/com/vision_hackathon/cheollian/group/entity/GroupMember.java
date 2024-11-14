package com.vision_hackathon.cheollian.group.entity;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;
import com.vision_hackathon.cheollian.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseAuditEntity {
	@EmbeddedId
	private GroupMemberId id;

	@ManyToOne
	@MapsId("groupId")
	@JoinColumn(name = "group_id")
	private Group group;

	@ManyToOne
	@MapsId("memberId")
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private GroupMemberRole role;

	@Builder
	public GroupMember(GroupMemberId id, GroupMemberRole role) {
		this.id = id;
		this.role = role;
	}
}
