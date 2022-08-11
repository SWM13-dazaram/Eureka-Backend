package dazaram.eureka.ingredient.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.repository.IngredientRepository;

@SpringBootTest
@Transactional(readOnly = true)
class IngredientServiceTest extends IngredientTest {
	@Autowired
	IngredientRepository ingredientRepository;
	@Autowired
	IngredientService ingredientService;

	@Test
	@Transactional
	public void 존재하지_않는_식재료id를_조회한다() {
		Ingredient byId = ingredientService.findIngredientById(1L);

		assertAll(
			() -> assertThat(byId).isEqualTo(null)
		);
	}
}
