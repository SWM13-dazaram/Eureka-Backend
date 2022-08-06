package dazaram.eureka.ingredient.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.IngredientTest;

class IngredientDomainTest extends IngredientTest {

	@Test
	public void 식재료를_생성한다() {
		Ingredient ingredient = createIngredient(null);

		assertAll(
			() -> assertThat(ingredient.getName()).isEqualTo(INGREDIENT_NAME),
			() -> assertThat(ingredient.getExpirePeriod()).isEqualTo(INGREDIENT_EXPIRE_PERIOD),
			() -> assertThat(ingredient.getIcon()).isEqualTo(INGREDIENT_ICON)
		);
	}

	@Test
	public void 식재료와_카테고리를_매핑한다() {
		IngredientCategory ingredientCategory = createIngredientCategory();
		Ingredient ingredient = createIngredient(ingredientCategory);

		assertAll(
			() -> assertThat(ingredient.getIngredientCategory()).isEqualTo(ingredientCategory),
			() -> assertThat(ingredientCategory.getIngredients().get(0)).isEqualTo(ingredient)
		);
	}

	@Test
	public void 식재료와_선택_식재료를_매핑한다() {
		Ingredient ingredient = createIngredient(null);
		UserIngredient userIngredient = createUserIngredient(ingredient);

		assertAll(
			() -> assertThat(userIngredient.getIngredient()).isEqualTo(ingredient),
			() -> assertThat(ingredient.getUserIngredients().get(0)).isEqualTo(userIngredient)
		);
	}
}
