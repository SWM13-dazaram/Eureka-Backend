package dazaram.eureka.ingredient.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import lombok.Data;

@Data
public class FindAllCategoryIngredientResponse {
	private IngredientCategoryDto category;
	private List<BasicIngredientDto> ingredients;

	public FindAllCategoryIngredientResponse(IngredientCategory ingredientCategory, List<Ingredient> ingredients) {
		this.category = IngredientCategoryDto.createFromEntity(ingredientCategory);
		this.ingredients = ingredients.stream()
			.map(BasicIngredientDto::fromIngredient)
			.collect(Collectors.toList());
	}
}
