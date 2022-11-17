package dazaram.eureka.ingredient.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class PrimaryIngredientServiceTest {
	@Autowired
	PrimaryIngredientService primaryIngredientService;

	@Test
	void getAllIcon() {
		System.out.println(primaryIngredientService.getAllIcon());
		;
	}
}
