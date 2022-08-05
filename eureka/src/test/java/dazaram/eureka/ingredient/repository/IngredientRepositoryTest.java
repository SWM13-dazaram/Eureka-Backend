package dazaram.eureka.ingredient.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.Ingredient;

@SpringBootTest
@Transactional
class IngredientRepositoryTest extends IngredientTest {

	@Autowired
	IngredientRepository ingredientRepository;

	@Test
	public void 식재료를_저장한다() {
		Ingredient savedIngredient = ingredientRepository.save(createIngredient(null));

		assertAll(
			() -> assertThat(savedIngredient.getName()).isEqualTo(INGREDIENT_NAME),
			() -> assertThat(savedIngredient.getExpirePeriod()).isEqualTo(INGREDIENT_EXPIRE_PERIOD),
			() -> assertThat(savedIngredient.getIcon()).isEqualTo(INGREDIENT_ICON)
		);
	}

	@Test
	public void 식재료를_id로_조회한다() {
		Ingredient savedIngredient = ingredientRepository.save(createIngredient(null));

		Ingredient findbyIngredientId = ingredientRepository.findById(savedIngredient.getId()).get();

		assertAll(
			() -> assertThat(findbyIngredientId).isEqualTo(savedIngredient)
		);
	}
}
