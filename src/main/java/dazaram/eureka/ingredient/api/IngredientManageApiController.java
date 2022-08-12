package dazaram.eureka.ingredient.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.ingredient.dto.UserIngredientDetailsDto;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IngredientManageApiController {
	private final UserIngredientRepository userIngredientRepository;

	@GetMapping("/api/v1/user-ingredients")
	public List<UserIngredientDetailsDto> findAllUserIngredientsByUserId(){
		return userIngredientRepository.findAll().stream()
			.map(UserIngredientDetailsDto::new)
			.collect(Collectors.toList());
	}
}
