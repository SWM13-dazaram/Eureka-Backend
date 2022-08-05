package dazaram.eureka.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.ingredient.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
