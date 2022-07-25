package dazaram.eureka.Oauth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.User.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth {

	@Id
	@GeneratedValue
	@Column(name = "oauth_id")
	private Long id;

	private String provider;

	private String accessToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oauth")
	private User user;

	public static Oauth create(String provider, String accessToken, User user) {
		Oauth oauth = new Oauth();
		oauth.provider = provider;
		oauth.accessToken = accessToken;
		oauth.user = user;
		user.addOauth(oauth);
		return oauth;
	}
}