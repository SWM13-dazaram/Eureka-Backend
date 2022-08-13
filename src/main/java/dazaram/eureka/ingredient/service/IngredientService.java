package dazaram.eureka.ingredient.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.domain.CustomIngredient;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.dto.CustomIngredientDetailsDto;
import dazaram.eureka.ingredient.dto.CustomIngredientRequest;
import dazaram.eureka.ingredient.dto.FindAllCategoryIngredientResponse;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.repository.CustomIngredientRepository;
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
	private final CustomIngredientRepository customIngredientRepository;
	private final IngredientCategoryRepository ingredientCategoryRepository;

	private Long createUserIngredient(UserIngredientDetailsDto dto) {
		Ingredient ingredient = findIngredientById(dto.getIngredient().getId());
		UserIngredient userIngredient = UserIngredient.createFromDto(dto, ingredient);
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
	public List<Long> storeUserIngredient(List<UserIngredientDetailsDto> userIngredientDetails) {
		return userIngredientDetails.stream()
			.map(this::createUserIngredient)
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

	public IngredientCategory findIngredientCategoryById(String id) {
		if (id == null) {
			return null;
		}
		Optional<IngredientCategory> byId = ingredientCategoryRepository.findById(id);

		return byId.isEmpty() ? null : byId.get();
	}

	@Transactional
	public void storeCustomIngredient(CustomIngredientRequest customIngredientRequest) {
		CustomIngredientDetailsDto ingredientDetails = customIngredientRequest.getUserIngredient();

		CustomIngredient customIngredient = CustomIngredient.builder()
			.insertDate(ingredientDetails.getInsertDate())
			.expireDate(ingredientDetails.getExpireDate())
			.memo(ingredientDetails.getMemo())
			.name(ingredientDetails.getIngredient().getName())
			.icon(ingredientDetails.getIngredient().getIcon())
			.ingredientCategory(this.findIngredientCategoryById(customIngredientRequest.getCategoryId()))
			.build();

		customIngredientRepository.save(customIngredient);
	}

	public List<UserIngredientDetailsDto> getAllUserIngredientDetails() {
		return userIngredientRepository.findAll().stream()
			.map(UserIngredientDetailsDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public Long updateUserIngredient(UserIngredientDetailsDto dto) {
		Ingredient ingredient = findIngredientById(dto.getIngredient().getId());
		UserIngredient userIngredient = UserIngredient.createFromDto(dto, ingredient);
		return userIngredientRepository.save(userIngredient).getId();
	}
}
