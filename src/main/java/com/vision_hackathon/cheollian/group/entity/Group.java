package com.vision_hackathon.cheollian.group.entity;

import java.util.UUID;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`group`") // Group은 MySQL 예약어이므로 테이블명을 `group`로 설정
public class Group extends BaseAuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID groupId;

	@NotBlank
	@Column(name = "group_name", length = 100)
	private String groupName;

	@NotNull
	@Column(name = "is_public", nullable = false)
	private Boolean isPublic;

	public Group(UUID groupId, String groupName, Boolean isPublic) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.isPublic = isPublic;
	}
}
