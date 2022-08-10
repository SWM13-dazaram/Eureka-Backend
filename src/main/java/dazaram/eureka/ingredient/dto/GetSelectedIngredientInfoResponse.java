package dazaram.eureka.ingredient.dto;

import dazaram.eureka.ingredient.domain.Ingredient;
import lombok.Data;

@Data
public class GetSelectedIngredientInfoResponse {
	private Long id;
	private String name;
	private String icon;
	private Long expirePeriod;

	public GetSelectedIngredientInfoResponse(Ingredient ingredient) {
		id = ingredient.getId();
		name = ingredient.getName();
		icon = ingredient.getIcon();
		expirePeriod = ingredient.getExpirePeriod();
	}
}
