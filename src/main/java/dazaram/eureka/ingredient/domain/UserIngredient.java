package dazaram.eureka.ingredient.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIngredient extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_ingredient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	private String name;

	private LocalDate insertDate;
	private LocalDate expireDate;

	private String memo;

	@Builder
	public UserIngredient(Long id, User user, Ingredient ingredient, String name, LocalDate insertDate,
		LocalDate expireDate, String memo) {
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

	public static UserIngredient createFromDto(UserIngredientDetailsDto dto, User user, Ingredient ingredient) {
		return UserIngredient.builder()
			.id(dto.getId())
			.name(dto.getName())
			.insertDate(dto.getInsertDate())
			.expireDate(dto.getExpireDate())
			.memo(dto.getMemo())
			.user(user)
			.ingredient(ingredient)
			.build();
	}

	public Long getIngredientId() {
		return ingredient.getId();
	}

	public void validateUser(User user) {
		if (!(this.user.equals(user))) {
			throw new RuntimeException("권한이 없는 유저입니다");
		}
	}
}
