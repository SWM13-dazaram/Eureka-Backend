package dazaram.eureka.ingredient.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.repository.IngredientCategoryRepository;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {
	private final IngredientRepository ingredientRepository;
	private final UserIngredientRepository userIngredientRepository;
	private final IngredientCategoryRepository ingredientCategoryRepository;

	private Long CreateUserIngredient(UserIngredientDetailsDto dto) {
		Ingredient ingredient = findIngredientById(dto.getIngredient().getId());
		UserIngredient userIngredient = UserIngredient.builder()
			.id(dto.getId())
			.name(dto.getName())
			.insertDate(dto.getInsertDate())
			.expireDate(dto.getExpireDate())
			.memo(dto.getMemo())
			.ingredient(ingredient)
			.build();
		return userIngredientRepository.save(userIngredient).getId();
	}

	public Ingredient findIngredientById(Long id) {
		if (id == null) {
			return null;
		}
		Optional<Ingredient> byId = ingredientRepository.findById(id);

		return byId.isEmpty() ? null : byId.get();
	}

	public List<GetSelectedIngredientInfoResponse> getSelectedIngredientInfo(List<Long> selectedIngredientIds) {
		return selectedIngredientIds.stream()
			.map(o -> new GetSelectedIngredientInfoResponse(findIngredientById(o)))
			.collect(Collectors.toList());
	}

	@Transactional
	public List<Long> StoreUserIngredient(List<UserIngredientDetailsDto> userIngredientDetails) {
		return userIngredientDetails.stream()
			.map(this::CreateUserIngredient)
			.collect(Collectors.toList());
	}

	public List<FindAllCategoryIngredientResponse> findAllCategoryIngredient() {
		Map<IngredientCategory, List<Ingredient>> map = new HashMap<>();
		List<FindAllCategoryIngredientResponse> ret = new ArrayList<>();

		List<Ingredient> allIngredients = ingredientRepository.findAll();
		for (Ingredient ing : allIngredients) {
			IngredientCategory category = ing.getIngredientCategory();
			if (!map.containsKey(category)) {
				map.put(category, new ArrayList<>());
			}
			map.get(category).add(ing);
		}

		for (IngredientCategory category : map.keySet()) {
			ret.add(new FindAllCategoryIngredientResponse(category, map.get(category)));
		}
		return ret;
	}

	public List<FindAllCategoryIngredientResponse> findCategoryIngredient(String categoryId) {
		Optional<IngredientCategory> byId = ingredientCategoryRepository.findById(categoryId);
		if (byId.isEmpty()) {
			return null;
		}
		IngredientCategory ingredientCategory = byId.get();

		return new ArrayList<>(List.of(
			new FindAllCategoryIngredientResponse(ingredientCategory,
				ingredientRepository.findIngredientsByIngredientCategory(ingredientCategory))));
	}
}
