package dazaram.eureka.recipe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dazaram.eureka.recipe.domain.ExistingRecipe;

public interface ExistingRecipeRepository extends JpaRepository<ExistingRecipe, Long> {
	Optional<ExistingRecipe> findByUrl(String url);

	@Query(value = "select r from ExistingRecipe r ")
	Page<ExistingRecipe> findAllExistingRecipeForElastic(Pageable pageable);
}
