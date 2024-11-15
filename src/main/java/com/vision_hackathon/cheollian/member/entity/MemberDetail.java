package com.vision_hackathon.cheollian.member.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_detail")
public class MemberDetail {
	@Id
	@Column(name = "member_id", nullable = false, updatable = false)
	@NotNull(message = "Member ID cannot be null")
	private UUID memberId;

	@MapsId
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "member_id")
	@NotNull(message = "Member association cannot be null")
	private Member member;

	@Column(name = "age", nullable = false)
	@Min(value = 0, message = "Age must be non-negative")
	@Max(value = 150, message = "Age must be realistic")
	private int age;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", length = 10)
	@NotNull(message = "Gender must be specified")
	private Gender gender;

	@Column(name = "height")
	@NotNull(message = "Height cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Height must be positive")
	private float height;

	@Column(name = "weight")
	@NotNull(message = "Weight cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Weight must be positive")
	private float weight;

	@Column(name = "nickname", length = 50)
	@NotBlank(message = "Nickname cannot be blank")
	@Size(max = 50, message = "Nickname must not exceed 50 characters")
	private String nickname;

	@Column(name = "profile_image", length = 256)
	@Size(max = 256, message = "Profile image URL must not exceed 256 characters")
	@Pattern(
		regexp = "^(https?://).+",
		message = "Profile image must be a valid URL starting with http:// or https://"
	)
	private String profileImage;

	@Column(name = "school_name", length = 20)
	@Size(max = 20, message = "School name must not exceed 20 characters")
	private String schoolName;

	@Column(name = "school_code")
	private Integer schoolCode;

	@Builder
	public MemberDetail(UUID memberId, Member member, int age, Gender gender, float height, float weight,
		String nickname, String profileImage, String schoolName, Integer schoolCode) {
		this.memberId = memberId;
		this.member = member;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.schoolName = schoolName;
		this.schoolCode = schoolCode;
	}
}
