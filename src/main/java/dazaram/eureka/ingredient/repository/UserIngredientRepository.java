package dazaram.eureka.ingredient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.user.domain.User;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {
	void deleteUserIngredientById(Long id);

	List<UserIngredient> findAllByUser(User user);
}
