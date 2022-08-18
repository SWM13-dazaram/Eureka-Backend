package dazaram.eureka.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import dazaram.eureka.elastic.domain.RecipeDocument;

public interface RecipeElasticRepository extends ElasticsearchRepository<RecipeDocument, Long> {
}
