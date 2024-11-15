package com.vision_hackathon.cheollian.member.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vision_hackathon.cheollian.base.BaseAuditEntity;
import com.vision_hackathon.cheollian.dailyAnalysis.entity.DailyAnalysis;
import com.vision_hackathon.cheollian.diet.entity.Diet;
import com.vision_hackathon.cheollian.diet.exception.SchoolNotFoundException;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseAuditEntity {
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

	@Column(name = "profile_image", length = 256)
	@Size(max = 256, message = "Profile image URL must not exceed 256 characters")
	@Pattern(
		regexp = "^(https?://).+",
		message = "Profile image must be a valid URL starting with http:// or https://"
	)
	private String profileImage;

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MemberDetail memberDetail;

	@OneToMany(
		mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY
	)
	private List<Diet> diets = new ArrayList<>();

	@OneToMany(
		mappedBy = "member",
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.LAZY
	)
	private List<DailyAnalysis> dailyAnalyses = new ArrayList<>();

	@Builder
	public Member(UUID memberId, String email, String name, String profileImage, Role role, MemberDetail memberDetail) {
		this.memberId = memberId;
		this.email = email;
		this.name = name;
		this.profileImage = profileImage;
		this.role = role;
		this.memberDetail = memberDetail;
	}

	public void connectMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}

	public void connectSchool(String schoolName, int schoolCode, String regionCode) {
		this.memberDetail.setSchoolName(schoolName);
		this.memberDetail.setSchoolCode(schoolCode);
		this.memberDetail.setRegionCode(regionCode);
	}

	public void checkSchoolInfo(){
		if (this.memberDetail.getSchoolName() == null){
			throw new SchoolNotFoundException();
		}
	}

}
