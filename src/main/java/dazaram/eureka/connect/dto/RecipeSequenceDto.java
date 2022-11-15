package dazaram.eureka.connect.dto;

import java.util.ArrayList;
import java.util.List;

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
	private List<Long> highlightIndexList;

	@Builder
	public RecipeSequenceDto(String content, Long sequence, List<Long> highlightIndexList) {
		this.content = content;
		this.sequence = sequence;
		this.highlightIndexList = highlightIndexList;
	}

	public static RecipeSequenceDto fromDocument(RecipeSequenceDocument recipeSequenceDocument) {
		// TODO : highlightIndexes 채우기
		List<Long> highlightIndexes = new ArrayList<>();
		return RecipeSequenceDto.builder()
			.content(recipeSequenceDocument.getContent())
			.sequence(recipeSequenceDocument.getSequence())
			.highlightIndexList(highlightIndexes)
			.build();
	}
}
