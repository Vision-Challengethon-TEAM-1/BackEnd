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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
	public GroupMember(Group group, Member member, GroupMemberRole role) {
		this.group = group;
		this.member = member;
		this.role = role;
		this.id = new GroupMemberId(group.getGroupId(), member.getMemberId());
	}
}
