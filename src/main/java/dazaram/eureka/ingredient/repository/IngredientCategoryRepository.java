package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
}
