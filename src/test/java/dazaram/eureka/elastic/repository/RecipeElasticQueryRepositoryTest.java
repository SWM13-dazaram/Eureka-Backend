package dazaram.eureka.elastic.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dazaram.eureka.elastic.domain.RecipeDocument;

@SpringBootTest
class RecipeElasticQueryRepositoryTest {
	@Autowired
	RecipeElasticQueryRepository recipeElasticQueryRepository;

	@Test
	void 선택한_재료가_하나라도_포함된_레시피_모두_검색() {
		String nameList = "튀김가루 감자 고구마";

		List<RecipeDocument> recipeDocuments = recipeElasticQueryRepository.findByIngredientsNameList(nameList);

		System.out.println(recipeDocuments.size());
	}

}
