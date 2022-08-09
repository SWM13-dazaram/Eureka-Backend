package dazaram.eureka.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Oauth extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oauth_id")
	private Long id;

	private String provider;

	private String accessToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oauth")
	private User user;

	@Builder
	public Oauth(Long id, String provider, String accessToken, User user) {
		this.id = id;
		this.provider = provider;
		this.accessToken = accessToken;
		this.user = user;

		if (user != null) {
			user.addOauth(this);
		}
	}
}
