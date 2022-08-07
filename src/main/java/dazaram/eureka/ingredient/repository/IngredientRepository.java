package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
