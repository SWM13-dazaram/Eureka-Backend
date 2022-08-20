package dazaram.eureka.ingredient.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.dto.CustomIngredientRequest;
import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.IngredientCategoryDto;
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
		return ingredientService.storeUserIngredient(userIngredientDetails);
	}

	@PostMapping("/api/v1/custom-ingredients")
	public CustomIngredientRequest createCustomIngredient(
		@RequestBody @Valid CustomIngredientRequest customIngredientRequest) {
		ingredientService.storeCustomIngredient(customIngredientRequest);
		return customIngredientRequest;
	}

	@GetMapping("/api/v1/user-ingredients")
	public List<UserIngredientDetailsDto> findAllUserIngredientsByUserId() {
		return ingredientService.getAllUserIngredientDetails();
	}

	@PutMapping("/api/v1/user-ingredients")
	public Long updateUserIngredient(@RequestBody @Valid UserIngredientDetailsDto userIngredientDetailsDto) {
		return ingredientService.updateUserIngredient(userIngredientDetailsDto);
	}

	@DeleteMapping("/api/v1/user-ingredients/{userIngredientId}")
	public String deleteUserIngredient(@PathVariable("userIngredientId") Long id) {
		return ingredientService.deleteUserIngredient(id);
	}

	@GetMapping("/api/v1/ingredients-categories")
	public List<IngredientCategoryDto> getIngredientCategories() {
		return ingredientCategoryService.getAllIngredientCategories();
	}
}
