package dazaram.eureka.recipe.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.elastic.service.RecipeElasticService;
import dazaram.eureka.recipe.domain.dto.RecipeDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipes")
public class RecipeApiController {
	private final RecipeElasticService recipeElasticService;

	@GetMapping("/expire-date")
	public List<RecipeDto> recommendExpireDateRecipes() {
		return recipeElasticService.recommendExpireDateRecipes(148L);
	}
}
