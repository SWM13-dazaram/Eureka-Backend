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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

	private String name;
	private String phoneNumber;
	private boolean pushAlarmAllow;

	private String profileImage;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Oauth> oauths = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserTaste> userTastes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserIngredient> userIngredients;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CustomIngredient> customIngredients;

	@Builder
	public User(Long id, String name, String phoneNumber, boolean pushAlarmAllow, String profileImage, Gender gender) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.pushAlarmAllow = pushAlarmAllow;
		this.profileImage = profileImage;
		this.gender = gender;
		this.oauths = new ArrayList<>();
		this.userTastes = new ArrayList<>();
		this.userIngredients = new ArrayList<>();
		this.customIngredients = new ArrayList<>();
	}

	public void addUserTaste(UserTaste userTaste) {
		userTastes.add(userTaste);
	}

	public void addOauth(Oauth oauth) {
		oauths.add(oauth);
	}

	public void addUserIngredient(UserIngredient userIngredient) {
		userIngredients.add(userIngredient);
	}

	public void addCustomIngredient(CustomIngredient customIngredient) {
		customIngredients.add(customIngredient);
	}
}
