package dazaram.eureka.ingredient.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.IngredientCategory;

@SpringBootTest
@Transactional
class IngredientCategoryRepositoryTest extends IngredientTest {

	@Autowired
	IngredientCategoryRepository ingredientCategoryRepository;

	private IngredientCategory ingredientCategory = createIngredientCategory();

	@Test
	public void 카테고리를_저장한다() {
		IngredientCategory savedIngredientCategory = ingredientCategoryRepository.save(ingredientCategory);

		assertAll(
			() -> assertThat(savedIngredientCategory.getId()).isNotNull(),
			() -> assertThat(savedIngredientCategory).isEqualTo(ingredientCategory)
		);
	}

	@Test
	public void 카테고리를_id로_조회한다() {
		IngredientCategory savedIngredientCategory = ingredientCategoryRepository.save(ingredientCategory);

		IngredientCategory findByIngredientCategoryId = ingredientCategoryRepository.findById(savedIngredientCategory.getId()).get();

		assertAll(
			() -> assertThat(findByIngredientCategoryId).isEqualTo(savedIngredientCategory)
		);
	}
}
