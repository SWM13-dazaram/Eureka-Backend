package dazaram.eureka.ingredient.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.domain.IngredientCategory;

@SpringBootTest
@Transactional(readOnly = true)
class IngredientCategoryServiceTest {
	@Autowired
	IngredientCategoryService ingredientCategoryService;

	@Test
	public void 존재하지_않는_식재료_카테고리id를_조회한다() {
		IngredientCategory byId = ingredientCategoryService.findById("TST");

		assertAll(
			() -> assertThat(byId).isEqualTo(null)
		);
	}
}
