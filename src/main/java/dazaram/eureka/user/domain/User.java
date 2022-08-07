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
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

	private Gender gender;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Oauth> oauths;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserTaste> userTastes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserIngredient> userIngredients;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CustomIngredient> customIngredients;

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
