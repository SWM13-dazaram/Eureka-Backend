package dazaram.eureka.elastic.domain;

import dazaram.eureka.ingredient.domain.Ingredient;
import lombok.Getter;

@Getter
public class IngredientDocument {
	private Long id;
	private String name;
	private String categoryName;
	private String categoryId;

	public IngredientDocument(Ingredient ingredient) {
		this.id = ingredient.getId();
		this.name = ingredient.getName();
		this.categoryName = ingredient.getIngredientCategory().getName();
		this.categoryId = ingredient.getIngredientCategory().getId();
	}
}
