package dazaram.eureka.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecipeTest {

	private ExistingRecipe makeExistingRecipe(String name, String url, String image) {
		return ExistingRecipe.builder()
			.name(name)
			.recipePlatform(null)
			.recipeCategory(null)
			.url(url)
			.image(image)
			.build();
	}

	private AiRecipe makeAiRecipe(String name, String image, ExistingRecipe existingRecipe) {
		return AiRecipe.builder()
			.name(name)
			.image(image)
			.reference(existingRecipe)
			.build();
	}

	@Test
	void ExistingRecipe를_생성한다() {
		String name = "부대찌개";
		String url = "a.com";
		String image = "img";

		ExistingRecipe existingRecipe = makeExistingRecipe(name, url, image);

		assertAll(
			() -> assertThat(existingRecipe.getUrl()).isEqualTo(url),
			() -> assertThat(existingRecipe.getName()).isEqualTo(name),
			() -> assertThat(existingRecipe.getImage()).isEqualTo(image)
		);

	}

	@Test
	void AiRecipe를_생성한다() {
		String name = "된장찌개";
		String image = "img";

		String ExistingName = "부대찌개";
		String url = "a.com";
		String ExistingImage = "img";

		ExistingRecipe existingRecipe = makeExistingRecipe(ExistingName, url, ExistingImage);
		AiRecipe aiRecipe = makeAiRecipe(name, image, existingRecipe);

		assertAll(
			() -> assertThat(aiRecipe.getReference()).isEqualTo(existingRecipe),
			() -> assertThat(aiRecipe.getName()).isEqualTo(name),
			() -> assertThat(aiRecipe.getImage()).isEqualTo(image)
		);

	}

}
