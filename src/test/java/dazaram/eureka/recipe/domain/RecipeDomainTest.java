package dazaram.eureka.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import dazaram.eureka.recipe.RecipeTest;

public class RecipeDomainTest extends RecipeTest {
	@Test
	void ExistingRecipe를_생성한다() {
		ExistingRecipe existingRecipe = makeExistingRecipe();

		assertAll(
			() -> assertThat(existingRecipe.getUrl()).isEqualTo(URL),
			() -> assertThat(existingRecipe.getName()).isEqualTo(EXISTING_RECIPE_NAME),
			() -> assertThat(existingRecipe.getImage()).isEqualTo(EXISTING_RECIPE_IMAGE)
		);

	}

	@Test
	void AiRecipe를_생성한다() {
		ExistingRecipe existingRecipe = makeExistingRecipe();
		AiRecipe aiRecipe = makeAiRecipe(existingRecipe.getId());

		assertAll(
			() -> assertThat(aiRecipe.getReferenceRecipeId()).isEqualTo(existingRecipe.getId()),
			() -> assertThat(aiRecipe.getName()).isEqualTo(AI_RECIPE_NAME),
			() -> assertThat(aiRecipe.getImage()).isEqualTo(AI_IMAGE)
		);

	}

	@Test
	void RecipeSequence를_생성한다() {
		ExistingRecipe existingRecipe = makeExistingRecipe();

		RecipeSequence sequence1 = makeSequence(SEQ_1, CONTENT_1, existingRecipe);

		assertAll(
			() -> assertThat(sequence1.getSequence()).isEqualTo(SEQ_1),
			() -> assertThat(sequence1.getContent()).isEqualTo(CONTENT_1),
			() -> assertThat(sequence1.getRecipe()).isEqualTo(existingRecipe)
		);

	}

	@Test
	void RecipeSequence과_레시피를_연결한다() {
		RecipeSequence sequence1 = RecipeSequence.builder()
			.sequence(SEQ_1)
			.content(CONTENT_1)
			.build();

		RecipeSequence sequence2 = RecipeSequence.builder()
			.sequence(SEQ_2)
			.content(CONTENT_2)
			.build();

		List<RecipeSequence> recipeSequences = new ArrayList<>(Arrays.asList(sequence1, sequence2));

		ExistingRecipe existingRecipe = makeExistingRecipe();
		existingRecipe.setRecipeSequences(recipeSequences);

		assertAll(
			() -> assertThat(existingRecipe.getRecipeSequences()).hasSize(2),
			() -> assertThat(existingRecipe.getRecipeSequences().get(0).getContent()).isEqualTo(CONTENT_1),
			() -> assertThat(existingRecipe.getRecipeSequences().get(1).getContent()).isEqualTo(CONTENT_2)
		);

	}

}
