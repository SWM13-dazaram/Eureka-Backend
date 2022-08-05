package dazaram.eureka.ingredient.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.IngredientTest;

class CustomIngredientTest extends IngredientTest {

	@Test
	public void 직접입력_식재료를_생성한다() {
		CustomIngredient customIngredient = createCustomIngredient(null);

		assertAll(
			() -> assertThat(customIngredient.getName()).isEqualTo(CUSTOM_INGREDIENT_NAME),
			() -> assertThat(customIngredient.getIcon()).isEqualTo(CUSTOM_INGREDIENT_ICON),
			() -> assertThat(customIngredient.getMemo()).isEqualTo(CUSTOM_INGREDIENT_MEMO)
		);
	}

	@Test
	public void 직접입력_식재료와_카테고리를_매핑한다() {
		Category category = createCategory();
		CustomIngredient customIngredient = createCustomIngredient(category);

		assertAll(
			() -> assertThat(customIngredient.getCategory()).isEqualTo(category),
			() -> assertThat(category.getCustomIngredients().get(0)).isEqualTo(customIngredient)
		);
	}
}
