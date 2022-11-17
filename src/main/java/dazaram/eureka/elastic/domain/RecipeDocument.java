package dazaram.eureka.elastic.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import dazaram.eureka.connect.domain.RecipeIngredient;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "recipe")
@Mapping(mappingPath = "elastic/recipe-mapping.json")
@Setting(settingPath = "elastic/recipe-setting.json")
@ToString
public class RecipeDocument {
	@Id
	private Long id;
	private String url;
	private String title;
	private String image;
	private List<IngredientDocument> ingredients;
	private List<RecipeSequenceDocument> recipeSequences;

	public RecipeDocument(ExistingRecipe existingRecipe) {
		this.id = existingRecipe.getId();
		this.url = existingRecipe.getUrl();
		this.title = existingRecipe.getName();
		this.image = existingRecipe.getImage();
		this.ingredients = existingRecipe.getRecipeIngredients().stream()
			.map(RecipeIngredient::getIngredient)
			.map(IngredientDocument::new)
			.collect(Collectors.toList());
		this.recipeSequences = existingRecipe.getRecipeSequences().stream()
			.map(RecipeSequenceDocument::new)
			.collect(Collectors.toList());
	}

	public List<Long> getAllIngredientsIds() {
		return ingredients.stream().map(IngredientDocument::getId)
			.collect(Collectors.toList());
	}
}
