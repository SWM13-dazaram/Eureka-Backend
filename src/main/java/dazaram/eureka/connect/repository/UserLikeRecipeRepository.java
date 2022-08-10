package dazaram.eureka.connect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.connect.domain.UserLikeRecipe;
import dazaram.eureka.recipe.domain.Recipe;
import dazaram.eureka.user.domain.User;

public interface UserLikeRecipeRepository extends JpaRepository<UserLikeRecipe, Long> {
	Optional<UserLikeRecipe> findByUserAndRecipe(User user, Recipe recipe);

	List<UserLikeRecipe> findAllByRecipe(Recipe recipe);
}
