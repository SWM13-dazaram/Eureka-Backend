package dazaram.eureka.ingredient.api;

import static dazaram.eureka.security.util.SecurityUtil.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import dazaram.eureka.ingredient.dto.CustomIngredientRequest;
import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.IngredientCategoryDto;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.service.IngredientCategoryService;
import dazaram.eureka.ingredient.service.IngredientService;
import dazaram.eureka.ingredient.service.PrimaryIngredientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class IngredientApiController {
	private final IngredientService ingredientService;
	private final IngredientCategoryService ingredientCategoryService;
	private final PrimaryIngredientService primaryIngredientService;

	@GetMapping("/ingredients/categories")
	public List<FindAllCategoryIngredientResponse> findAllIngredientsByCategoryId(
		@RequestParam(required = false) String categoryId) {
		if (categoryId.isEmpty()) {
			return ingredientService.findAllCategoryIngredient();
		}
		return ingredientService.findCategoryIngredient(categoryId);
	}

	@PostMapping("/ingredients/selected")
	public List<GetSelectedIngredientInfoResponse> getSelectedIngredientInfo(
		@RequestBody @Valid List<Long> selectedIngredientIds) {
		return ingredientService.getSelectedIngredientInfo(selectedIngredientIds);
	}

	@PostMapping("/ingredients/store")
	public List<Long> setSelectedIngredient(
		@RequestBody @Valid List<UserIngredientDetailsDto> userIngredientDetails) {
		return ingredientService.storeUserIngredient(getCurrentUserId(), userIngredientDetails);
	}

	@PostMapping("/custom-ingredients")
	public CustomIngredientRequest createCustomIngredient(
		@RequestBody @Valid CustomIngredientRequest customIngredientRequest) {
		ingredientService.storeCustomIngredient(getCurrentUserId(), customIngredientRequest);
		return customIngredientRequest;
	}

	@GetMapping("/user-ingredients")
	public List<UserIngredientDetailsDto> findAllUserIngredientsByUserId() {
		return ingredientService.getAllUserIngredientDetails(getCurrentUserId());
	}

	@PutMapping("/user-ingredients")
	public Long updateUserIngredient(@RequestBody @Valid UserIngredientDetailsDto userIngredientDetailsDto) {
		return ingredientService.updateUserIngredient(getCurrentUserId(), userIngredientDetailsDto);
	}

	@DeleteMapping("/user-ingredients/{userIngredientId}")
	public String deleteUserIngredient(@PathVariable("userIngredientId") Long userIngredientId) {
		return ingredientService.deleteUserIngredient(getCurrentUserId(), userIngredientId);
	}

	@GetMapping("/ingredients-categories")
	public List<IngredientCategoryDto> getIngredientCategories() {
		return ingredientCategoryService.getAllIngredientCategories();
	}

	@GetMapping("/ingredients/primary")
	public List<BasicIngredientDto> getPrimaryIngredient() {
		return primaryIngredientService.getAllPrimaryIngredients();
	}
}
