package dazaram.eureka.ingredient.dto;

import dazaram.eureka.ingredient.domain.Ingredient;
import lombok.Data;

@Data
public class GetSelectedIngredientInfoResponse {
	private BasicIngredientDto ingredient;
	private Long expirePeriod;

	public GetSelectedIngredientInfoResponse(Ingredient ingredient) {
		this.ingredient = BasicIngredientDto.fromEntity(ingredient);
		this.expirePeriod = ingredient.getExpirePeriod();
	}
}
