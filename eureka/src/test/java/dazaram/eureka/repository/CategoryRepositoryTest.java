package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.Category;
import dazaram.eureka.ingredient.CategoryRepository;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	@DisplayName("카테고리 추가 테스트")
	public void createCategoryTest(){
		Category category = Category.create("채소");
		Category saveCategory = categoryRepository.save(category);

		Category findCategory = categoryRepository.findById(saveCategory.getId()).get();

		Assertions.assertEquals(category, findCategory);
		Assertions.assertEquals(saveCategory, findCategory);
	}
}
