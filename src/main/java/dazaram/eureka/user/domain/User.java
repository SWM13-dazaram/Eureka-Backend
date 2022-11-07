package dazaram.eureka.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.ingredient.domain.CustomIngredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.security.dto.LoginUserInfoDto;
import dazaram.eureka.user.enums.Gender;
import dazaram.eureka.user.enums.ProviderType;
import dazaram.eureka.user.enums.RoleType;
import dazaram.eureka.user.enums.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String loginId;
	private String nickName;
	private String email;
	private String name;
	private String phoneNumber;
	private Boolean pushAlarmAllow = Boolean.TRUE;
	private String profileImage;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@Enumerated(EnumType.STRING)
	private ProviderType providerType;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserTaste> userTastes = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserIngredient> userIngredients = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CustomIngredient> customIngredients = new ArrayList<>();

	@Builder
	public User(String loginId, String nickName, String email, String name, String phoneNumber, Boolean pushAlarmAllow,
		String profileImage, UserStatus userStatus, ProviderType providerType, Gender gender, RoleType roleType) {
		this.loginId = loginId;
		this.nickName = nickName;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.pushAlarmAllow = pushAlarmAllow;
		this.profileImage = profileImage;
		this.userStatus = userStatus;
		this.providerType = providerType;
		this.gender = gender;
		this.roleType = roleType;

		if (pushAlarmAllow == null) {
			this.pushAlarmAllow = true;
		}
	}

	public void addUserTaste(UserTaste userTaste) {
		userTastes.add(userTaste);
	}

	public void addUserIngredient(UserIngredient userIngredient) {
		userIngredients.add(userIngredient);
	}

	public void addCustomIngredient(CustomIngredient customIngredient) {
		customIngredients.add(customIngredient);
	}

	public boolean isActivate() {
		return this.userStatus.equals(UserStatus.ACTIVE);
	}

	public static User fromLoginUserInfoDto(LoginUserInfoDto loginUserInfoDto) {
		return User.builder()
			.loginId(loginUserInfoDto.getLoginId())
			.providerType(loginUserInfoDto.getProviderType())
			.nickName(loginUserInfoDto.getNickName())
			.profileImage(loginUserInfoDto.getProfileImage())
			.email(loginUserInfoDto.getEmail())
			.pushAlarmAllow(true)
			.roleType(RoleType.USER)
			.userStatus(UserStatus.ACTIVE)
			.build();
	}

}
