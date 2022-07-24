package dazaram.eureka.domain;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class UserTaste {

	@Id
	@GeneratedValue
	@Column(name = "user_taste_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taste_id")
	private Taste taste;

	public static UserTaste create(User user, Taste taste){
		UserTaste userTaste = new UserTaste();
		userTaste.user = user;
		userTaste.taste = taste;
		user.addUserTaste(userTaste);
		taste.addUserTaste(userTaste);
		return userTaste;
	}
}
