package dazaram.eureka.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.connect.domain.RecipeIngredient;
import dazaram.eureka.recipe.domain.Recipe;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
	List<RecipeIngredient> findAllByRecipe(Recipe recipe);
}
