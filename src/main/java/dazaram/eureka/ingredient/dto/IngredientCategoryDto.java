package dazaram.eureka.ingredient.dto;

import dazaram.eureka.ingredient.domain.IngredientCategory;
import lombok.Data;

@Data
public class IngredientCategoryDto {
	private String id;
	private String name;

	public static IngredientCategoryDto createFromEntity(IngredientCategory ingredientCategory) {
		IngredientCategoryDto ret = new IngredientCategoryDto();
		ret.id = ingredientCategory.getId();
		ret.name = ingredientCategory.getName();
		return ret;
	}
}
