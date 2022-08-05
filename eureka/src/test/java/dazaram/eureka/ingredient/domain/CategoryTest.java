package dazaram.eureka.ingredient.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.IngredientTest;

public class CategoryTest extends IngredientTest {

	@Test
	public void 카테고리를_생성한다(){
		Category category = createCategory();

		assertAll(
			() -> assertThat(category.getName()).isEqualTo(CATEGORY_NAME)
		);
	}
}
