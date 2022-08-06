package dazaram.eureka.ingredient.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIngredient extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_ingredient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	private String name;

	private LocalDateTime insertDate;
	private LocalDateTime expireDate;

	private String memo;

	@Builder
	public UserIngredient(Long id, User user, Ingredient ingredient, String name, LocalDateTime insertDate,
		LocalDateTime expireDate, String memo) {
		this.id = id;
		this.user = user;
		this.ingredient = ingredient;
		this.name = name;
		this.insertDate = insertDate;
		this.expireDate = expireDate;
		this.memo = memo;

		if (user != null) {
			user.addUserIngredient(this);
		}
	}
}
