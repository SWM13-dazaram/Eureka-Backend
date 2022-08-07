package dazaram.eureka.ingredient.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.ingredient.IngredientTest;

class UserIngredientTest extends IngredientTest {

	@Test
	public void 선택식재료를_생성한다() {
		UserIngredient userIngredient = createUserIngredient(null);

		assertAll(
			() -> assertThat(userIngredient.getName()).isEqualTo(USER_INGREDIENT_NAME),
			() -> assertThat(userIngredient.getInsertDate()).isEqualTo(USER_INGREDIENT_INSERT_DATE),
			() -> assertThat(userIngredient.getExpireDate()).isEqualTo(USER_INGREDIENT_EXPIRE_DATE),
			() -> assertThat(userIngredient.getMemo()).isEqualTo(USER_INGREDIENT_MEMO)
		);
	}
}
