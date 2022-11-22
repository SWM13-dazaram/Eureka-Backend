package dazaram.eureka.ingredient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.CustomIngredient;
import dazaram.eureka.user.domain.User;

public interface CustomIngredientRepository extends JpaRepository<CustomIngredient, Long> {
	List<CustomIngredient> findAllByUser(User user);
}
