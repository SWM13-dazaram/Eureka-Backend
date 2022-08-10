package dazaram.eureka.ingredient.dto;

import dazaram.eureka.ingredient.domain.Ingredient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicIngredientDto {
	private Long id;
	private String name;
	private String icon;

	public BasicIngredientDto(Ingredient ingredient) {
		id = ingredient.getId();
		name = ingredient.getName();
		icon = ingredient.getIcon();
	}
}
