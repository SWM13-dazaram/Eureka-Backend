package dazaram.eureka.ingredient.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.ingredient.domain.IngredientCategory;
import lombok.Data;

@Data
public class FindAllCategoryIngredientResponse {
	private Long categoryId;
	private String categoryName;
	private List<BasicIngredientDto> ingredients;

	public FindAllCategoryIngredientResponse(IngredientCategory ingredientCategory) {
		if (ingredientCategory != null) {
			categoryId = ingredientCategory.getId();
			categoryName = ingredientCategory.getName();
			ingredients = ingredientCategory.getIngredients().stream()
				.map(BasicIngredientDto::new)
				.collect(Collectors.toList());
		}
	}
}
