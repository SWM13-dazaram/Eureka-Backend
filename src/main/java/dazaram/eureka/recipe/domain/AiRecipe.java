package dazaram.eureka.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import dazaram.eureka.connect.domain.ReplaceIngredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRecipe extends Recipe {

	private Long referenceRecipeId;

	@OneToOne
	@JoinColumn(name = "replace_ingredient_id")
	private ReplaceIngredient replaceIngredient;

	@Builder
	public AiRecipe(
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform,
		Long referenceRecipeId,
		ReplaceIngredient replaceIngredient
	) {
		super(name, image, recipeCategory, recipePlatform);
		this.referenceRecipeId = referenceRecipeId;
		this.replaceIngredient = replaceIngredient;
	}

	public void setReplaceIngredient(ReplaceIngredient replaceIngredient) {
		this.replaceIngredient = replaceIngredient;
	}
}
