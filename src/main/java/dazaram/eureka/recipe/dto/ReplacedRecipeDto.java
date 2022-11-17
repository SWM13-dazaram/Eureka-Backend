package dazaram.eureka.recipe.dto;

import java.util.List;
import java.util.stream.Collectors;

import dazaram.eureka.connect.dto.RecipeSequenceDto;
import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplacedRecipeDto {
	private Long id;
	private String name;
	private String image;
	private List<BasicIngredientDto> ingredients;
	private ReplacementDto replacement;
	private List<RecipeSequenceDto> recipeSequences;

	@Builder
	public ReplacedRecipeDto(Long id, String name, String image, List<BasicIngredientDto> ingredients,
		ReplacementDto replacement, List<RecipeSequenceDto> recipeSequences) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.ingredients = ingredients;
		this.replacement = replacement;
		this.recipeSequences = recipeSequences;
	}

	public static ReplacedRecipeDto fromRecipeDocument(RecipeDocument recipeDocument, Ingredient missingIngredient,
		Ingredient replacedIngredient, Double similarity) {
		ReplacementDto replacement = ReplacementDto.builder()
			.missingIngredient(BasicIngredientDto.fromEntity(missingIngredient))
			.ownIngredient(BasicIngredientDto.fromEntity(replacedIngredient))
			.similarity(similarity)
			.build();

		return ReplacedRecipeDto.builder()
			.id(recipeDocument.getId())
			.name(recipeDocument.getTitle())
			.image(recipeDocument.getImage())
			.ingredients(recipeDocument.getIngredients().stream().map(BasicIngredientDto::fromDocument).collect(
				Collectors.toList()))
			.replacement(replacement)
			.recipeSequences(recipeDocument.getRecipeSequences().stream().map(RecipeSequenceDto::fromDocument).collect(
				Collectors.toList()))
			.build();
	}
}
