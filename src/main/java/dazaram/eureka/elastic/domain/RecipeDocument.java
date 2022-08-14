package dazaram.eureka.elastic.domain;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// @Builder
// @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "recipe")
@Mapping(mappingPath = "elastic/recipe-mapping.json")
@Setting(settingPath = "elastic/recipe-setting.json")
public class RecipeDocument {
	@Id
	private Long id;
	private String url;
	private String title;
	private List<IngredientDocument> ingredients;
	private List<RecipeSequenceDocument> recipeSequences;
}
