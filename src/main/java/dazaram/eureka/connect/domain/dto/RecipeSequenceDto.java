package dazaram.eureka.connect.domain.dto;

import dazaram.eureka.elastic.domain.RecipeSequenceDocument;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeSequenceDto {
	private String content;
	private Long sequence;

	@Builder
	public RecipeSequenceDto(String content, Long sequence) {
		this.content = content;
		this.sequence = sequence;
	}

	public static RecipeSequenceDto fromDocument(RecipeSequenceDocument recipeSequenceDocument) {
		return RecipeSequenceDto.builder()
			.content(recipeSequenceDocument.getContent())
			.sequence(recipeSequenceDocument.getSequence())
			.build();
	}
}
