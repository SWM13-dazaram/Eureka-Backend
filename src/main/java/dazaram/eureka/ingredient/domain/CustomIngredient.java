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
import dazaram.eureka.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomIngredient extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "custom_ingredient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String name;

	private LocalDateTime expireDate;

	private String memo;

	private LocalDateTime insertDate;

	private String icon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_category_id")
	private IngredientCategory ingredientCategory;

	@Builder
	public CustomIngredient(Long id, User user, String name, LocalDateTime expireDate, String memo,
		LocalDateTime insertDate, String icon, IngredientCategory ingredientCategory) {
		this.id = id;
		this.user = user;
		this.name = name;
		this.expireDate = expireDate;
		this.memo = memo;
		this.insertDate = insertDate;
		this.icon = icon;
		this.ingredientCategory = ingredientCategory;

		if (user != null) {
			user.addCustomIngredient(this);
		}
		if (ingredientCategory != null) {
			ingredientCategory.addCustomIngredient(this);
		}
	}
}
