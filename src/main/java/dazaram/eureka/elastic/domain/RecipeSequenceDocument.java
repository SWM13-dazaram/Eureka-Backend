package dazaram.eureka.elastic.domain;

import dazaram.eureka.recipe.domain.RecipeSequence;
import lombok.Getter;

@Getter
public class RecipeSequenceDocument {
	private String content;
	private int sequence;

	public RecipeSequenceDocument(RecipeSequence recipeSequence) {
		this.content = recipeSequence.getContent();
		this.sequence = recipeSequence.getSequence();
	}
}
