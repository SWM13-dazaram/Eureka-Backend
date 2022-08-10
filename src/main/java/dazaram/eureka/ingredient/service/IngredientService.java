package dazaram.eureka.ingredient.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import dazaram.eureka.ingredient.dto.GetSelectedIngredientInfoResponse;
import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {
	private final IngredientRepository ingredientRepository;
	private final UserIngredientRepository userIngredientRepository;

	@Transactional
	public Long UserIngredient(String name, LocalDate insertDate, LocalDate expireDate, String memo,
		BasicIngredientDto basicIngredientDto) {
		Ingredient ingredient = this.findById(basicIngredientDto.getId());

		UserIngredient userIngredient = UserIngredient.builder()
			.name(name)
			.insertDate(insertDate)
			.expireDate(expireDate)
			.memo(memo)
			.ingredient(ingredient)
			.build();
		UserIngredient savedUserIngredient = userIngredientRepository.save(userIngredient);

		return savedUserIngredient.getId();
	}

	public Ingredient findById(Long id) {
		if (id == null) {
			return null;
		}
		Optional<Ingredient> byId = ingredientRepository.findById(id);

		return byId.isEmpty() ? null : byId.get();
	}

	public List<GetSelectedIngredientInfoResponse> getSelectedIngredientInfo(List<Long> selectedIngredientIds) {
		return selectedIngredientIds.stream()
			.map(o -> new GetSelectedIngredientInfoResponse(this.findById(o)))
			.collect(Collectors.toList());
	}

	public void StoreUserIngredient(List<UserIngredientDetailsDto> userIngredientDetails) {
		userIngredientDetails.stream()
			.map(o -> this.UserIngredient(o.getName(), o.getInsertDate(), o.getExpireDate(), o.getMemo(),
				o.getIngredient()));
	}
}
