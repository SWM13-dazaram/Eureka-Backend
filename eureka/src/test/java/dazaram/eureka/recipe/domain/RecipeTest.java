package dazaram.eureka.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecipeTest {

	@Test
	void ExistingRecipe를_생성한다() {
		String name = "부대찌개";
		String url = "a.com";
		String image = "img";

		ExistingRecipe existingRecipe = ExistingRecipe.builder()
			.name(name)
			.recipePlatform(null)
			.recipeCategory(null)
			.url(url)
			.image(image)
			.build();

		assertAll(
			() -> assertThat(existingRecipe.getUrl()).isEqualTo(url),
			() -> assertThat(existingRecipe.getName()).isEqualTo(name),
			() -> assertThat(existingRecipe.getImage()).isEqualTo(image)
		);

	}
}
