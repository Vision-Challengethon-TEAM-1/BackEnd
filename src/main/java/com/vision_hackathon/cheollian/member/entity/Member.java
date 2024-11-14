package com.vision_hackathon.cheollian.member.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.diet.entity.Diet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID memberId;

	@Email
	@NotBlank(message = "Email cannot be blank")
	@Size(max = 50, message = "Email must not exceed 100 characters")
	@Column(name = "email", unique = true)
	private String email;

	@NotBlank(message = "Name cannot be blank")
	@Size(max = 50, message = "Name must not exceed 50 characters")
	@Column(name = "name")
	private String name;

	@NotNull(message = "Role cannot be null")
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private MemberDetail memberDetail;

	@OneToMany(
		mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY
	)
	private List<Diet> diets = new ArrayList<>();

	@Builder
	public Member(UUID memberId, String email, String name, Role role, MemberDetail memberDetail, List<Diet> diets) {
		this.memberId = memberId;
		this.email = email;
		this.name = name;
		this.role = role;
		this.memberDetail = memberDetail;
		this.diets = diets;
	}
}
