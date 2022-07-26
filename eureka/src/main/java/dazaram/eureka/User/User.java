package dazaram.eureka.User;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.Oauth.Oauth;
import dazaram.eureka.UserTaste.UserTaste;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Oauth> oauths;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserTaste> userTastes;

	private User(UserBuilder userBuilder){
		this.name = userBuilder.phoneNumber;
		this.phoneNumber = userBuilder.phoneNumber;
		this.pushAlarmAllow = userBuilder.pushAlarmAllow;
		this.profileImage = userBuilder.profileImage;
		this.gender = userBuilder.gender;
		this.oauths = userBuilder.oauths;
		this.userTastes = userBuilder.userTastes;
	}

	public static class UserBuilder {
		private String name;
		private String phoneNumber;
		private boolean pushAlarmAllow;
		private String profileImage;
		private Gender gender;
		private List<Oauth> oauths;
		private List<UserTaste> userTastes;

		public UserBuilder() {
		}

		public UserBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public UserBuilder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserBuilder setPushAlarmAllow(boolean pushAlarmAllow){
			this.pushAlarmAllow = pushAlarmAllow;
			return this;
		}

		public UserBuilder setProfileImage(String profileImage){
			this.profileImage = profileImage;
			return this;
		}

		public UserBuilder setGender(Gender gender){
			this.gender = gender;
			return this;
		}

		public UserBuilder setOauths(List<Oauth> oauths){
			this.oauths = oauths;
			return this;
		}

		public UserBuilder setUserTastes(List<UserTaste> userTastes) {
			this.userTastes = userTastes;
			return this;
		}

		public User build(){
			return new User(this);
		}
	}

	public void addUserTaste(UserTaste userTaste) {
		userTastes.add(userTaste);
	}

	public void addOauth(Oauth oauth) {
		oauths.add(oauth);
	}
}
