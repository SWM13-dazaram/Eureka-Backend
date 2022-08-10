package dazaram.eureka.ingredient.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.repository.IngredientCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientCategoryService {
	private final IngredientCategoryRepository ingredientCategoryRepository;

	public IngredientCategory findById(Long id){
		Optional<IngredientCategory> byId = ingredientCategoryRepository.findById(id);

		return byId.isEmpty() ? null : byId.get();
	}
}