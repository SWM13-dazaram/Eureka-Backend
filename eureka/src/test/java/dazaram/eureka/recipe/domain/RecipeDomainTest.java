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
			() -> assertThat(existingRecipe.getName()).isEqualTo(EXISTINGRECIPENAME),
			() -> assertThat(existingRecipe.getImage()).isEqualTo(EXISTINGRECIPEIMAGE)
		);

	}

	@Test
	void AiRecipe를_생성한다() {
		ExistingRecipe existingRecipe = makeExistingRecipe();
		AiRecipe aiRecipe = makeAiRecipe(existingRecipe.getId());

		assertAll(
			() -> assertThat(aiRecipe.getReferenceRecipeId()).isEqualTo(existingRecipe.getId()),
			() -> assertThat(aiRecipe.getName()).isEqualTo(AIRECIPENAME),
			() -> assertThat(aiRecipe.getImage()).isEqualTo(AIIMAGE)
		);

	}

	@Test
	void RecipeSequence를_생성한다() {
		ExistingRecipe existingRecipe = makeExistingRecipe();

		RecipeSequence sequence1 = makeSequence(SEQ1, CONTENT1, existingRecipe);

		assertAll(
			() -> assertThat(sequence1.getSequence()).isEqualTo(SEQ1),
			() -> assertThat(sequence1.getContent()).isEqualTo(CONTENT1),
			() -> assertThat(sequence1.getRecipe()).isEqualTo(existingRecipe)
		);

	}

	@Test
	void RecipeSequence과_레시피를_연결한다() {
		RecipeSequence sequence1 = RecipeSequence.builder()
			.sequence(SEQ1)
			.content(CONTENT1)
			.build();

		RecipeSequence sequence2 = RecipeSequence.builder()
			.sequence(SEQ2)
			.content(CONTENT2)
			.build();

		List<RecipeSequence> recipeSequences = new ArrayList<>(Arrays.asList(sequence1, sequence2));

		ExistingRecipe existingRecipe = makeExistingRecipe();
		existingRecipe.setRecipeSequences(recipeSequences);

		assertAll(
			() -> assertThat(existingRecipe.getRecipeSequences().size()).isEqualTo(2),
			() -> assertThat(existingRecipe.getRecipeSequences().get(0).getContent()).isEqualTo(CONTENT1),
			() -> assertThat(existingRecipe.getRecipeSequences().get(1).getContent()).isEqualTo(CONTENT2)
		);

	}

}
