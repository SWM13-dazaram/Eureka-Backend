package dazaram.eureka.recipe.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.connect.dto.RecipeSequenceDto;
import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpireDateRecipeDto {
	private Long id;
	private String url;
	private String title;
	private String image;
	private List<BasicIngredientDto> ingredients;
	private BasicIngredientDto imminentIngredient;
	private List<RecipeSequenceDto> recipeSequences;

	@Builder
	public ExpireDateRecipeDto(Long id, String url, String title, String image, List<BasicIngredientDto> ingredients,
		BasicIngredientDto imminentIngredient, List<RecipeSequenceDto> recipeSequences) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.image = image;
		this.ingredients = ingredients;
		this.imminentIngredient = imminentIngredient;
		this.recipeSequences = recipeSequences;
	}

	// TODO : Imminent Ingredient 채워넣기
	public static ExpireDateRecipeDto fromDocument(RecipeDocument recipeDocument, Ingredient imminentIngredient) {
		return ExpireDateRecipeDto.builder()
			.id(recipeDocument.getId())
			.url(recipeDocument.getUrl())
			.title(recipeDocument.getTitle())
			.image(recipeDocument.getImage())
			.ingredients(recipeDocument.getIngredients().stream()
				.map(BasicIngredientDto::fromDocument).collect(Collectors.toList()))
			.imminentIngredient(BasicIngredientDto.fromEntity(imminentIngredient))
			.recipeSequences(recipeDocument.getRecipeSequences().stream()
				.map(RecipeSequenceDto::fromDocument)
				.collect(Collectors.toList()))
			.build();
	}
}
