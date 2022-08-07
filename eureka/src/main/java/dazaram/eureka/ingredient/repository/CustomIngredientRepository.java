package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.CustomIngredient;

public interface CustomIngredientRepository extends JpaRepository<CustomIngredient, Long> {
}
