package dazaram.eureka.recipe.domain;

import java.util.List;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRecipe extends Recipe {

	private Long referenceRecipeId;

	@Builder
	public AiRecipe(
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform,
		List<RecipeSequence> recipeSequences,
		Long referenceRecipeId
	) {
		super(name, image, recipeCategory, recipePlatform, recipeSequences);
		this.referenceRecipeId = referenceRecipeId;
	}
}
