package dazaram.eureka.recipe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.recipe.domain.ExistingRecipe;

public interface ExistingRecipeRepository extends JpaRepository<ExistingRecipe, Long> {
	Optional<ExistingRecipe> findByUrl(String url);
}
