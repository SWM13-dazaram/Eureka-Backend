package dazaram.eureka.connect.repository;

import static dazaram.eureka.ingredient.IngredientTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.connect.domain.RecipeIngredient;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.repository.ExistingRecipeRepository;

@SpringBootTest
@Transactional
class RecipeIngredientRepositoryTest extends RecipeTest {

	@Autowired
	RecipeIngredientRepository recipeIngredientRepository;
	@Autowired
	ExistingRecipeRepository existingRecipeRepository;
	@Autowired
	IngredientRepository ingredientRepository;

	RecipeIngredient recipeIngredient;
	Ingredient savedIngredient;
	ExistingRecipe savedExistingRecipe;
	ExistingRecipe existingRecipe = makeExistingRecipe();
	Ingredient ingredient = Ingredient.builder()
		.name(INGREDIENT_NAME)
		.build();

	@BeforeEach
	void setUp() {
		recipeIngredient = RecipeIngredient.create(existingRecipe, ingredient, null);

		savedIngredient = ingredientRepository.save(ingredient);
		savedExistingRecipe = existingRecipeRepository.save(existingRecipe);
	}

	@Test
	void RecipeIngredient를_DB에_저장한다() {
		//given

		//when
		RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);
		// then
		assertAll(
			() -> assertThat(savedRecipeIngredient.getIngredient()).isEqualTo(savedIngredient),
			() -> assertThat(savedRecipeIngredient.getRecipe()).isEqualTo(savedExistingRecipe)
		);
	}

	@Test
	void 레시피에_저장된_재료를_가져온다() {
		//given
		final String ingredientName = "재료2";

		RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);

		Ingredient ingredient2 = ingredientRepository.save(Ingredient.builder()
			.name(ingredientName)
			.build());

		recipeIngredientRepository.save(RecipeIngredient.create(existingRecipe, ingredient2, null));

		//when
		List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAllByRecipe(existingRecipe);
		// then
		assertAll(
			() -> assertThat(recipeIngredients).hasSize(2),
			() -> assertThat(recipeIngredients.get(0).getIngredient().getName()).isEqualTo(INGREDIENT_NAME),
			() -> assertThat(recipeIngredients.get(1).getIngredient().getName()).isEqualTo(ingredientName)
		);
	}
}
