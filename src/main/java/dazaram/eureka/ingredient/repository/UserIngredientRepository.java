package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.UserIngredient;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {
	void deleteUserIngredientById(Long id);
}
