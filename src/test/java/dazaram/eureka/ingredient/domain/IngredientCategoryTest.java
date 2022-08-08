package dazaram.eureka.ingredient.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.IngredientTest;

public class IngredientCategoryTest extends IngredientTest {

	@Test
	public void 카테고리를_생성한다(){
		IngredientCategory ingredientCategory = createIngredientCategory();

		assertAll(
			() -> assertThat(ingredientCategory.getName()).isEqualTo(INGREDIENT_CATEGORY_NAME)
		);
	}
}
