package dazaram.eureka.ingredient.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.dto.BasicIngredientDto;
import dazaram.eureka.ingredient.repository.PrimaryIngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrimaryIngredientService {
	private final PrimaryIngredientRepository primaryIngredientRepository;

	public List<BasicIngredientDto> getAllPrimaryIngredients() {
		return primaryIngredientRepository.findAll().stream()
			.map(BasicIngredientDto::fromPrimaryIngredient)
			.collect(Collectors.toList());
	}
}
