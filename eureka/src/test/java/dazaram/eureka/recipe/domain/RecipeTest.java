package dazaram.eureka.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RecipeTest {
	public static final String EXISTINGRECIPENAME = "부대찌개";
	public static final String URL = "existing.com";
	public static final String EXISTINGRECIPEIMAGE = "existingImg";

	public static final String AIRECIPENAME = "된장찌개";
	public static final String AIIMAGE = "aiImg";

	public static final int SEQ1 = 1;
	public static final int SEQ2 = 2;
	public static final String CONTENT1 = "만든다";
	public static final String CONTENT2 = "먹는다";

	public static ExistingRecipe makeExistingRecipe() {
		return ExistingRecipe.builder()
			.name(EXISTINGRECIPENAME)
			.url(URL)
			.image(EXISTINGRECIPEIMAGE)
			.build();
	}

	public static AiRecipe makeAiRecipe(Long referenceRecipeId) {
		return AiRecipe.builder()
			.name(AIRECIPENAME)
			.image(AIIMAGE)
			.referenceRecipeId(referenceRecipeId)
			.build();
	}

	public static RecipeSequence makeSequence(int sequence, String content, Recipe recipe) {
		return RecipeSequence.builder()
			.sequence(sequence)
			.content(content)
			.recipe(recipe)
			.build();
	}

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
