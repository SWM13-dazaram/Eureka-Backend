package dazaram.eureka.Oauth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.User.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "oauth_id")
	private Long id;

	private String provider;

	private String accessToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oauth")
	private User user;

	private Oauth(OauthBuilder oauthBuilder){
		this.provider = oauthBuilder.provider;
		this.accessToken = oauthBuilder.accessToken;
		this.user = oauthBuilder.user;
	}

	public static class OauthBuilder {
		private String provider;
		private String accessToken;
		private User user;

		public OauthBuilder(){}

		public OauthBuilder setProvider(String provider){
			this.provider = provider;
			return this;
		}

		public OauthBuilder setAccessToken(String accessToken){
			this.accessToken = accessToken;
			return this;
		}

		public OauthBuilder setUser(User user){
			this.user = user;
			return this;
		}

		public Oauth build() {
			return new Oauth(this);
		}
	}

}