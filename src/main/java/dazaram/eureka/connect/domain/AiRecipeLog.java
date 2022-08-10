package dazaram.eureka.connect.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRecipeLog extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ai_recipe_log")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private AiRecipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public static AiRecipeLog create(AiRecipe recipe, User user) {
		return new AiRecipeLog(null, recipe, user);
	}

}
