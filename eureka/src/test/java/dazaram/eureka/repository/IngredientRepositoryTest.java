package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.Ingredient;
import dazaram.eureka.ingredient.IngredientRepository;
import dazaram.eureka.ingredient.Type;

@SpringBootTest
@Transactional
class IngredientRepositoryTest {

	@Autowired
	IngredientRepository ingredientRepository;

	@Test
	@DisplayName("식재료 추가 테스트")
	public void createIngredientTest() {
		Ingredient ingredient = Ingredient.builder()
			.name("가지")
			.expirePeriod(7L)
			.icon("src/eggplant.jpg")
			.type(Type.Category)
			.build();
		Ingredient saveIngredient = ingredientRepository.save(ingredient);

		Ingredient findIngredient = ingredientRepository.findById(ingredient.getId()).get();

		Assertions.assertEquals(ingredient, saveIngredient);
		Assertions.assertEquals(ingredient, findIngredient);
	}
}
