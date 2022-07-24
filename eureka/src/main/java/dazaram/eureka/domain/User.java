package dazaram.eureka.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String name;
	private String phoneNumber;
	private boolean pushAlarmAllow;

	private String profileImage;

	private Gender gender;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Oauth> oauths;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserTaste> userTastes;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<CustomIngredient> customIngredients;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserIngredient> userIngredients;

	public static User create(
		String name,
		String phoneNumber,
		boolean pushAlarmAllow,
		String profileImage,
		Gender gender,
		List<Oauth> oauths,
		List<UserTaste> userTastes,
		List<CustomIngredient> customIngredients,
		List<UserIngredient> userIngredients
	) {
		return new User(
			null,
			name,
			phoneNumber,
			pushAlarmAllow,
			profileImage,
			gender,
			oauths,
			userTastes,
			customIngredients,
			userIngredients
		);
	}

}
