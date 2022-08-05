package dazaram.eureka.recipe;

import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.domain.RecipeSequence;

public abstract class RecipeTest {
	public final String EXISTING_RECIPE_NAME = "부대찌개";
	public final String URL = "existing.com";
	public final String EXISTING_RECIPE_IMAGE = "existingImg";

	public final String AI_RECIPE_NAME = "된장찌개";
	public final String AI_IMAGE = "aiImg";

	public final int SEQ_1 = 1;
	public final int SEQ_2 = 2;

	public final String CONTENT_1 = "만든다";
	public final String CONTENT_2 = "먹는다";

	public ExistingRecipe makeExistingRecipe() {
		return ExistingRecipe.builder()
			.name(EXISTING_RECIPE_NAME)
			.url(URL)
			.image(EXISTING_RECIPE_IMAGE)
			.build();
	}

	public AiRecipe makeAiRecipe(Long referenceRecipeId) {
		return AiRecipe.builder()
			.name(AI_RECIPE_NAME)
			.image(AI_IMAGE)
			.referenceRecipeId(referenceRecipeId)
			.build();
	}

	public RecipeSequence makeSequence(int sequence, String content, dazaram.eureka.recipe.domain.Recipe recipe) {
		return RecipeSequence.builder()
			.sequence(sequence)
			.content(content)
			.recipe(recipe)
			.build();
	}
}
