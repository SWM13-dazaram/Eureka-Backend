package dazaram.eureka.ingredient.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.repository.IngredientCategoryRepository;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.ingredient.service.IngredientCategoryService;
import dazaram.eureka.ingredient.service.IngredientService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IngredientApiController {
	private final IngredientCategoryRepository ingredientCategoryRepository;
	private final IngredientService ingredientService;
	private final IngredientCategoryService ingredientCategoryService;

	@GetMapping("/api/v1/ingredients/categories")
	public List<AllCategoryIngredientDto> findAllIngredientsByCategoryId(
		@RequestParam(required = false) Long categoryId) {
		if (categoryId == null) {
			return ingredientCategoryRepository.findAll().stream()
				.map(AllCategoryIngredientDto::new)
				.collect(Collectors.toList());
		}
		IngredientCategory ingredientCategory = ingredientCategoryService.findById(categoryId);

		return new ArrayList<>(List.of(new AllCategoryIngredientDto(ingredientCategory)));
	}

	@PostMapping("/api/v1/ingredients/selected")
	public List<SelectedIngredientDto> getSelectedIngredientInfo(@RequestBody @Valid List<Long> selectedIngredientIds) {
		if (selectedIngredientIds.isEmpty())
			return null;

		return selectedIngredientIds.stream()
			.map(o -> new SelectedIngredientDto(ingredientService.findById(o)))
			.collect(Collectors.toList());
	}

	@PostMapping("/api/v1/ingredients/store")
	public List<UserIngredientDetailsDto> setSelectedIngredient(
		@RequestBody @Valid List<UserIngredientDetailsDto> userIngredientDetails) {
		userIngredientDetails.stream()
			.map(o -> ingredientService.UserIngredient(o.getName(), o.getInsertDate(), o.getExpireDate(), o.getMemo(),
				o.getIngredient()));
		return userIngredientDetails;
	}

	@Data
	static class AllCategoryIngredientDto {
		private Long categoryId;
		private String categoryName;
		private List<BasicIngredientDto> ingredients;

		public AllCategoryIngredientDto(IngredientCategory ingredientCategory) {
			if (ingredientCategory != null) {
				categoryId = ingredientCategory.getId();
				categoryName = ingredientCategory.getName();
				ingredients = ingredientCategory.getIngredients().stream()
					.map(BasicIngredientDto::new)
					.collect(Collectors.toList());
			}
		}
	}

	@Data
	static class SelectedIngredientDto {
		private Long id;
		private String name;
		private String icon;
		private Long expirePeriod;

		public SelectedIngredientDto(Ingredient ingredient) {
			id = ingredient.getId();
			name = ingredient.getName();
			icon = ingredient.getIcon();
			expirePeriod = ingredient.getExpirePeriod();
		}
	}

}
