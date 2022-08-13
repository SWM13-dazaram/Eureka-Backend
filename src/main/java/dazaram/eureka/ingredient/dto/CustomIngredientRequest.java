package dazaram.eureka.ingredient.dto;

import lombok.Data;

@Data
public class CustomIngredientRequest {
	private Long categoryId;

	private CustomIngredientDetailsDto userIngredient;
}
