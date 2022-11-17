package dazaram.eureka.ingredient.service;

import java.util.ArrayList;
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

	public List<String> getAllIcon() {
		String url = "https://eureka-data.s3.ap-northeast-2.amazonaws.com/icon/";
		int maxIconNumber = 106;
		List<String> iconList = new ArrayList<>();
		for (int icon = 1; icon <= maxIconNumber; icon++) {
			iconList.add(url + icon + ".png");
		}
		return iconList;
	}
}
