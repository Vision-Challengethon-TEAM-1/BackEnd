package com.vision_hackathon.cheollian.group.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
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
