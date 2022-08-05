package dazaram.eureka.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.oauth.Oauth;
import dazaram.eureka.usertaste.UserTaste;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
// @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	private String name;
	private String phoneNumber;
	private boolean pushAlarmAllow;

	private String profileImage;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Oauth> oauths = new ArrayList<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserTaste> userTastes = new ArrayList<>();

	@Builder
	public User(Long id, String name, String phoneNumber, boolean pushAlarmAllow, String profileImage, Gender gender) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.pushAlarmAllow = pushAlarmAllow;
		this.profileImage = profileImage;
		this.gender = gender;
	}

	public void addUserTaste(UserTaste userTaste) {
		userTastes.add(userTaste);
	}

	public void addOauth(Oauth oauth) {
		oauths.add(oauth);
	}
}
