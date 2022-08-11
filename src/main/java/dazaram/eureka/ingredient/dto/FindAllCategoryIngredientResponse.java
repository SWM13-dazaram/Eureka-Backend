package dazaram.eureka.ingredient.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import lombok.Data;

@Data
public class FindAllCategoryIngredientResponse {
	private String categoryId;
	private String categoryName;
	private List<BasicIngredientDto> ingredients;

	public FindAllCategoryIngredientResponse(IngredientCategory ingredientCategory, List<Ingredient> ingredients) {
		this.categoryId = ingredientCategory.getId();
		this.categoryName = ingredientCategory.getName();
		this.ingredients = ingredients.stream()
			.map(BasicIngredientDto::new)
			.collect(Collectors.toList());
	}
}
