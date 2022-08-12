package dazaram.eureka.ingredient.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IngredientApiController {
	private final IngredientService ingredientService;

	@GetMapping("/api/v1/ingredients/categories")
	public List<FindAllCategoryIngredientResponse> findAllIngredientsByCategoryId(
		@RequestParam(required = false) String categoryId) {
		if (categoryId.isEmpty()) {
			return ingredientService.findAllCategoryIngredient();
		}
		return ingredientService.findCategoryIngredient(categoryId);
	}

	@PostMapping("/api/v1/ingredients/selected")
	public List<GetSelectedIngredientInfoResponse> getSelectedIngredientInfo(
		@RequestBody @Valid List<Long> selectedIngredientIds) {
		return ingredientService.getSelectedIngredientInfo(selectedIngredientIds);
	}

	@PostMapping("/api/v1/ingredients/store")
	public List<Long> setSelectedIngredient(
		@RequestBody @Valid List<UserIngredientDetailsDto> userIngredientDetails) {
		return ingredientService.StoreUserIngredient(userIngredientDetails);
	}

	@GetMapping("/api/v1/user-ingredients")
	public List<UserIngredientDetailsDto> findAllUserIngredientsByUserId() {
		return ingredientService.getAllUserIngredientDetails();
	}
}
