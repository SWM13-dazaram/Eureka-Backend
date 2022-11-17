package dazaram.eureka.recipe.dto;

import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplacementDto {
	private BasicIngredientDto missingIngredient;
	private BasicIngredientDto ownIngredient;
	private Double similarity;

	@Builder
	public ReplacementDto(BasicIngredientDto missingIngredient, BasicIngredientDto ownIngredient, Double similarity) {
		this.missingIngredient = missingIngredient;
		this.ownIngredient = ownIngredient;
		this.similarity = similarity;
	}
}
