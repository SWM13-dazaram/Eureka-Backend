package dazaram.eureka.connect.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.ExistingRecipe;

public class RecipeIngredientDomainTest extends RecipeTest {
	Ingredient ingredient = Ingredient.builder().build();
	ExistingRecipe existingRecipe = makeExistingRecipe();

	RecipeIngredient recipeIngredient;

	@Test
	void RecipeIngredient를_생성합니다() {
		// given

		// when
		recipeIngredient = RecipeIngredient.builder()
			.ingredient(ingredient)
			.recipe(existingRecipe)
			.build();
		// then
		assertAll(
			() -> assertThat(recipeIngredient.getRecipe()).isEqualTo(existingRecipe),
			() -> assertThat(recipeIngredient.getIngredient()).isEqualTo(ingredient)
		);
	}

}
