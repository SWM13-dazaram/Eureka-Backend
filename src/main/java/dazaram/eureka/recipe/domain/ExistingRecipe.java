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
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform,
		String url
	) {
		super(name, image, recipeCategory, recipePlatform);
		this.url = url;
	}

}
