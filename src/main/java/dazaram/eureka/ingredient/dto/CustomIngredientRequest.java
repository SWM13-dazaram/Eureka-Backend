package dazaram.eureka.ingredient.dto;

import lombok.Data;

@Data
public class CustomIngredientRequest {
	private String categoryId;

	private CustomIngredientDetailsDto userIngredient;
}
