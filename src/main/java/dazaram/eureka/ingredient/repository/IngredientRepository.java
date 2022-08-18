package dazaram.eureka.ingredient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	List<Ingredient> findIngredientsByIngredientCategory(IngredientCategory ingredientCategory);

	Optional<Ingredient> findByName(String name);
}
