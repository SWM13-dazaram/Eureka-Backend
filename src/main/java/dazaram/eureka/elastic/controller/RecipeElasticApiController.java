package dazaram.eureka.elastic.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.elastic.service.RecipeElasticService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic")
public class RecipeElasticApiController {
	private final RecipeElasticService recipeElasticService;

	@PostMapping("/recipes/init")
	public void initRecipeElastic(@RequestParam(defaultValue = "5000") int bulkSize) {
		recipeElasticService.initWithExistingRecipe(bulkSize);
	}
}
