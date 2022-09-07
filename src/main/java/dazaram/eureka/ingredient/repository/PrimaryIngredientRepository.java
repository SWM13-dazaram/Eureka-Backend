package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.PrimaryIngredient;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {
}
