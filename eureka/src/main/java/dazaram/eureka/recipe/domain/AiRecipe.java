package dazaram.eureka.recipe.domain;

import java.util.List;

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
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform,
		List<RecipeSequence> recipeSequences,
		ExistingRecipe reference
	) {
		super(name, image, recipeCategory, recipePlatform, recipeSequences);
		this.reference = reference;
	}
}
