package dazaram.eureka.ingredient.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.CustomIngredient;

@SpringBootTest
@Transactional
class CustomIngredientRepositoryTest extends IngredientTest {

	@Autowired
	CustomIngredientRepository customIngredientRepository;

	private CustomIngredient customIngredient = createCustomIngredient(null);

	@Test
	public void 직접입력_식재료를_저장한다() {
		CustomIngredient savedCustomIngredient = customIngredientRepository.save(customIngredient);

		assertAll(
			() -> assertThat(savedCustomIngredient.getId()).isNotNull(),
			() -> assertThat(savedCustomIngredient).isEqualTo(customIngredient)
		);
	}

	@Test
	public void 직접입력_식재료를_id로_조회한다(){
		CustomIngredient savedCustomIngredient = customIngredientRepository.save(customIngredient);

		CustomIngredient findByCustomIngredientId = customIngredientRepository.findById(savedCustomIngredient.getId()).get();

		assertAll(
			() -> assertThat(findByCustomIngredientId).isEqualTo(savedCustomIngredient)
		);
	}
}
