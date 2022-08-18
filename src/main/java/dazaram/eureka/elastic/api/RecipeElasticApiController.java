package dazaram.eureka.elastic.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.elastic.service.RecipeElasticService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RecipeElasticApiController {
	private final RecipeElasticService recipeElasticService;

	@PostMapping("/api/v1/elastic/recipes/init")
	public void initRecipeElastic() {
		recipeElasticService.initWithExistingRecipe();
	}
}
