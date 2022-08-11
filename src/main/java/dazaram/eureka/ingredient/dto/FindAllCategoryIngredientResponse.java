package dazaram.eureka.ingredient.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import lombok.Data;

@Data
public class FindAllCategoryIngredientResponse {
	private IngredientCategory category;
	private List<BasicIngredientDto> ingredients;

	public FindAllCategoryIngredientResponse(IngredientCategory ingredientCategory, List<Ingredient> ingredients) {
		this.category = ingredientCategory;
		this.ingredients = ingredients.stream()
			.map(BasicIngredientDto::new)
			.collect(Collectors.toList());
	}
}
