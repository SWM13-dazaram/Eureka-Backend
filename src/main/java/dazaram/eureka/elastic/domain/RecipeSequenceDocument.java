package dazaram.eureka.elastic.domain;

import dazaram.eureka.recipe.domain.RecipeSequence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeSequenceDocument {
	private String content;
	private Long sequence;

	public RecipeSequenceDocument(RecipeSequence recipeSequence) {
		this.content = recipeSequence.getContent();
		this.sequence = recipeSequence.getSequence();
	}
}
