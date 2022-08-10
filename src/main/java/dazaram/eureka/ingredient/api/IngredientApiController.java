package dazaram.eureka.ingredient.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.dto.CustomIngredientRequest;
import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.service.IngredientCategoryService;
import dazaram.eureka.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IngredientApiController {
	private final IngredientService ingredientService;
	private final IngredientCategoryService ingredientCategoryService;

	@GetMapping("/api/v1/ingredients/categories")
	public List<FindAllCategoryIngredientResponse> findAllIngredientsByCategoryId(
		@RequestParam(required = false) Long categoryId) {
		if (categoryId == null) {
			return ingredientCategoryService.findAllCategoryIngredient();
		}
		IngredientCategory ingredientCategory = ingredientCategoryService.findById(categoryId);

		return new ArrayList<>(List.of(new FindAllCategoryIngredientResponse(ingredientCategory)));
	}

	@PostMapping("/api/v1/ingredients/selected")
	public List<GetSelectedIngredientInfoResponse> getSelectedIngredientInfo(
		@RequestBody @Valid List<Long> selectedIngredientIds) {
		return ingredientService.getSelectedIngredientInfo(selectedIngredientIds);
	}

	@PostMapping("/api/v1/ingredients/store")
	public List<UserIngredientDetailsDto> setSelectedIngredient(
		@RequestBody @Valid List<UserIngredientDetailsDto> userIngredientDetails) {
		ingredientService.StoreUserIngredient(userIngredientDetails);
		return userIngredientDetails;
	}

	@PostMapping("/api/v1/custom-ingredients")
	public CustomIngredientRequest createCustomIngredient(
		@RequestBody @Valid CustomIngredientRequest customIngredientRequest) {
		ingredientService.StoreCustomIngredient(customIngredientRequest);
		return customIngredientRequest;
	}
}
