package dazaram.eureka.ingredient.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.IngredientTest;
import dazaram.eureka.ingredient.domain.Category;

@SpringBootTest
@Transactional
class CategoryRepositoryTest extends IngredientTest {

	@Autowired
	CategoryRepository categoryRepository;

	private Category category = createCategory();

	@Test
	public void 카테고리를_저장한다() {
		Category savedCategory = categoryRepository.save(category);

		assertAll(
			() -> assertThat(savedCategory.getId()).isNotNull(),
			() -> assertThat(savedCategory).isEqualTo(category)
		);
	}

	@Test
	public void 카테고리를_id로_조회한다() {
		Category savedCategory = categoryRepository.save(category);

		Category findByCategoryId = categoryRepository.findById(savedCategory.getId()).get();

		assertAll(
			() -> assertThat(findByCategoryId).isEqualTo(savedCategory)
		);
	}
}
