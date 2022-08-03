package dazaram.eureka.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRecipe extends Recipe {
	@OneToOne
	@JoinColumn(name = "reference_id")
	private ExistingRecipe reference;

	@Builder
	public AiRecipe(
		final String name,
		final String image,
		final RecipeCategory recipeCategory,
		final RecipePlatform recipePlatform,
		final ExistingRecipe reference
	) {
		super(name, image, recipeCategory, recipePlatform);
		this.reference = reference;
	}
}
