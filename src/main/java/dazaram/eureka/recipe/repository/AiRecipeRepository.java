package dazaram.eureka.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.recipe.domain.AiRecipe;

public interface AiRecipeRepository extends JpaRepository<AiRecipe, Long> {
}
