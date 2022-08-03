package dazaram.eureka.recipe.domain;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExistingRecipe extends Recipe {

	private String url;

	@Builder
	public ExistingRecipe(
		final String name,
		final String image,
		final RecipeCategory recipeCategory,
		final RecipePlatform recipePlatform,
		final String url
	) {
		super(name, image, recipeCategory, recipePlatform);
		this.url = url;
	}

}
