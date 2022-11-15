package dazaram.eureka.recipe.controller;

import static dazaram.eureka.security.util.SecurityUtil.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.elastic.service.RecipeElasticService;
import dazaram.eureka.recipe.dto.ExpireDateRecipeDto;
import dazaram.eureka.recipe.dto.ReplacedRecipeDto;
import dazaram.eureka.recipe.service.ReplacedRecipeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipes")
public class RecipeApiController {
	private final RecipeElasticService recipeElasticService;
	private final ReplacedRecipeService replacedRecipeService;

	@GetMapping("/replacement")
	public ResponseEntity<List<ReplacedRecipeDto>> recommendReplacedRecipes() {
		return ResponseEntity.ok(replacedRecipeService.getReplacedRecipes());
	}

	@GetMapping("/expire-date")
	public ResponseEntity<List<ExpireDateRecipeDto>> recommendExpireDateRecipes() {
		final int topRank = 3;
		return ResponseEntity.ok(recipeElasticService.recommendExpireDateRecipes(getCurrentUserId(), topRank));
	}
}
