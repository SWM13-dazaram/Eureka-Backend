package dazaram.eureka.recipe;

import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.domain.RecipeSequence;

public abstract class RecipeTest {
	public final String EXISTINGRECIPENAME = "부대찌개";
	public final String URL = "existing.com";
	public final String EXISTINGRECIPEIMAGE = "existingImg";

	public final String AIRECIPENAME = "된장찌개";
	public final String AIIMAGE = "aiImg";

	public final int SEQ1 = 1;
	public final int SEQ2 = 2;
	public final String CONTENT1 = "만든다";
	public final String CONTENT2 = "먹는다";

	public ExistingRecipe makeExistingRecipe() {
		return ExistingRecipe.builder()
			.name(EXISTINGRECIPENAME)
			.url(URL)
			.image(EXISTINGRECIPEIMAGE)
			.build();
	}

	public AiRecipe makeAiRecipe(Long referenceRecipeId) {
		return AiRecipe.builder()
			.name(AIRECIPENAME)
			.image(AIIMAGE)
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
