package dazaram.eureka.ingredient.dto;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.PrimaryIngredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicIngredientDto {
	private Long id;
	private String name;
	private String icon;

	@Builder
	public BasicIngredientDto(Long id, String name, String icon) {
		this.id = id;
		this.name = name;
		this.icon = icon;
	}

	public static BasicIngredientDto fromIngredient(Ingredient ingredient) {
		return BasicIngredientDto.builder()
			.id(ingredient.getId())
			.name(ingredient.getName())
			.icon(ingredient.getIcon())
			.build();
	}

	public static BasicIngredientDto fromPrimaryIngredient(PrimaryIngredient ingredient) {
		return BasicIngredientDto.builder()
			.id(ingredient.getIngredient().getId())
			.name(ingredient.getIngredient().getName())
			.icon(ingredient.getIngredient().getIcon())
			.build();
	}
}
