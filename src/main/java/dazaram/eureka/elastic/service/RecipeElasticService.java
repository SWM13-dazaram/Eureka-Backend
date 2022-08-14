package dazaram.eureka.elastic.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.elastic.repository.RecipeElasticRepository;
import dazaram.eureka.recipe.repository.ExistingRecipeRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeElasticService {
	private final RecipeElasticRepository recipeElasticRepository;
	private final ExistingRecipeRepository existingRecipeRepository;

	@Transactional
	public void initWithExistingRecipe() {
		recipeElasticRepository.deleteAll();
		double pageContentSize = 5000.0;
		IntStream.rangeClosed(0, (int)Math.ceil(existingRecipeRepository.findAll().size() / (pageContentSize)))
			.forEach(i -> {
				List<RecipeDocument> recipeDocuments = existingRecipeRepository.findAllExistingRecipeForElastic(
						PageRequest.of(i, (int)pageContentSize)).getContent().stream()
					.map(RecipeDocument::new)
					.collect(Collectors.toList());
				recipeElasticRepository.saveAll(recipeDocuments);
			});
	}

}
