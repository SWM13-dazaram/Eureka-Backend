package dazaram.eureka.recipe.domain.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.connect.domain.dto.RecipeSequenceDto;
import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecipeDto {
	private Long id;
	private String url;
	private String title;
	private String image;
	private List<BasicIngredientDto> ingredients;
	private List<RecipeSequenceDto> recipeSequences;

	@Builder
	public RecipeDto(Long id, String url, String title, String image, List<BasicIngredientDto> ingredients,
		List<RecipeSequenceDto> recipeSequences) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.image = image;
		this.ingredients = ingredients;
		this.recipeSequences = recipeSequences;
	}

	public static RecipeDto fromDocument(RecipeDocument recipeDocument) {
		return RecipeDto.builder()
			.id(recipeDocument.getId())
			.url(recipeDocument.getUrl())
			.title(recipeDocument.getTitle())
			.image(recipeDocument.getImage())
			.ingredients(recipeDocument.getIngredients().stream()
				.map(BasicIngredientDto::fromDocument).collect(Collectors.toList()))
			.recipeSequences(recipeDocument.getRecipeSequences().stream()
				.map(RecipeSequenceDto::fromDocument)
				.collect(Collectors.toList()))
			.build();
	}
}
