package dazaram.eureka.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RecipeTest {

	private ExistingRecipe makeExistingRecipe(String name, String url, String image) {
		return ExistingRecipe.builder()
			.name(name)
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

	@Test
	void RecipeSequence를_생성한다() {
		int seq1 = 1;
		String content1 = "만든다";

		String ExistingName = "부대찌개";
		String url = "a.com";
		String ExistingImage = "img";

		ExistingRecipe existingRecipe = makeExistingRecipe(ExistingName, url, ExistingImage);

		RecipeSequence sequence1 = RecipeSequence.builder()
			.sequence(seq1)
			.content(content1)
			.recipe(existingRecipe)
			.build();

		assertAll(
			() -> assertThat(sequence1.getSequence()).isEqualTo(seq1),
			() -> assertThat(sequence1.getContent()).isEqualTo(content1),
			() -> assertThat(sequence1.getRecipe()).isEqualTo(existingRecipe)
		);

	}

	@Test
	void RecipeSequence과_레시피를_연결한다() {
		int seq1 = 1;
		int seq2 = 2;
		String content1 = "만든다";
		String content2 = "먹는다";

		String ExistingName = "부대찌개";
		String url = "a.com";
		String ExistingImage = "img";

		RecipeSequence sequence1 = RecipeSequence.builder()
			.sequence(seq1)
			.content(content1)
			.build();

		RecipeSequence sequence2 = RecipeSequence.builder()
			.sequence(seq2)
			.content(content2)
			.build();

		List<RecipeSequence> recipeSequences = new ArrayList<>(Arrays.asList(sequence1, sequence2));

		ExistingRecipe existingRecipe = ExistingRecipe.builder()
			.name(ExistingName)
			.url(url)
			.image(ExistingImage)
			.recipeSequences(recipeSequences)
			.build();

		assertAll(
			() -> assertThat(existingRecipe.getRecipeSequences().size()).isEqualTo(2),
			() -> assertThat(existingRecipe.getRecipeSequences().get(0).getContent()).isEqualTo(content1),
			() -> assertThat(existingRecipe.getRecipeSequences().get(1).getContent()).isEqualTo(content2)
		);

	}

}
