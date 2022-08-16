package dazaram.eureka.elastic.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import dazaram.eureka.elastic.domain.RecipeDocument;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RecipeElasticQueryRepository {
	private final ElasticsearchOperations elasticsearchOperations;
	private final ElasticsearchRestTemplate template;

	// public List<RecipeDocument> example(String nameList) {
	// 	Query query = new NativeSearchQueryBuilder()
	// 		.withQuery(new BoolQueryBuilder()
	// 			.must(QueryBuilders.matchQuery("ingredients.name", nameList).operator(Operator.OR)))
	// 		.build();
	//
	// 	SearchHits<RecipeDocument> search = elasticsearchOperations.search(query, RecipeDocument.class);
	// 	return search.stream()
	// 		.map(SearchHit::getContent)
	// 		.collect(Collectors.toList());
	// }

	public List<RecipeDocument> findByIngredientsNameList(String nameList) {

		IndexCoordinates index = IndexCoordinates.of("recipe");

		NativeSearchQuery query = new NativeSearchQueryBuilder()
			.withQuery(new BoolQueryBuilder()
				.must(QueryBuilders.matchQuery("ingredients.name", nameList).operator(Operator.OR)))
			.withPageable(PageRequest.of(0, 10000))
			.build();

		SearchScrollHits<RecipeDocument> scroll = template.searchScrollStart(1000, query, RecipeDocument.class,
			index);

		List<String> scrollIds = new ArrayList<>();
		List<RecipeDocument> recipeDocuments = new ArrayList<>();
		String scrollId = scroll.getScrollId();

		while (scroll.hasSearchHits()) {
			scrollIds.add(scrollId);
			recipeDocuments.addAll(
				scroll.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList()));
			scrollId = scroll.getScrollId();
			scroll = template.searchScrollContinue(scrollId, 1000, RecipeDocument.class, index);
		}
		scrollIds.add(scrollId);
		template.searchScrollClear(scrollIds);

		return recipeDocuments;
	}

}
