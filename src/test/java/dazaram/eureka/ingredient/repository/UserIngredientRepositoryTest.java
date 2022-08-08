package dazaram.eureka.ingredient.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.UserIngredient;

@SpringBootTest
@Transactional
class UserIngredientRepositoryTest extends IngredientTest {

	@Autowired
	UserIngredientRepository userIngredientRepository;

	private Ingredient ingredient = createIngredient(null);
	private UserIngredient userIngredient = createUserIngredient(ingredient);

	@Test
	public void 선택식재료를_저장한다() {
		UserIngredient savedUserIngredient = userIngredientRepository.save(userIngredient);

		assertAll(
			() -> assertThat(savedUserIngredient.getId()).isNotNull(),
			() -> assertThat(savedUserIngredient).isEqualTo(userIngredient)
		);
	}

	@Test
	public void 선택식재료를_id로_조회한다() {
		UserIngredient savedUserIngredient = userIngredientRepository.save(userIngredient);

		UserIngredient findByUserIngredientId = userIngredientRepository.findById(savedUserIngredient.getId()).get();

		assertAll(
			() -> assertThat(findByUserIngredientId).isEqualTo(savedUserIngredient)
		);
	}
}
