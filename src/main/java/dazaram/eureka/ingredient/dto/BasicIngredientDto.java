package dazaram.eureka.ingredient.dto;

import dazaram.eureka.elastic.domain.IngredientDocument;
import dazaram.eureka.ingredient.domain.Ingredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

	public static BasicIngredientDto fromEntity(Ingredient ingredient) {
		return BasicIngredientDto.builder()
			.id(ingredient.getId())
			.name(ingredient.getName())
			.icon(ingredient.getIcon())
			.build();
	}

	public static BasicIngredientDto fromDocument(IngredientDocument ingredient) {
		return BasicIngredientDto.builder()
			.id(ingredient.getId())
			.name(ingredient.getName())
			.icon(ingredient.getIcon())
			.build();
	}
}
