package dazaram.eureka.elastic;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.elastic.repository.RecipeElasticQueryRepository;
import dazaram.eureka.elastic.service.RecipeElasticService;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import dazaram.eureka.recipe.domain.dto.RecipeDto;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.Gender;
import dazaram.eureka.user.repository.UserRepository;

@SpringBootTest
@Transactional(readOnly = true)
class RecipeElasticServiceTest {

	@Autowired
	RecipeElasticQueryRepository recipeElasticQueryRepository;
	@Autowired
	UserIngredientRepository userIngredientRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	RecipeElasticService recipeElasticService;

	@Transactional
	@Test
	void 레시피추천_유통기한_임박_식재료_위주로() {
		User saveduser = userRepository.save(User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build());

		userIngredientRepository.save(UserIngredient.builder()
			.ingredient(ingredientRepository.findByName("감자").get())
			.user(saveduser)
			.expireDate(LocalDate.of(2022, 8, 21))
			.build());

		userIngredientRepository.save(UserIngredient.builder()
			.ingredient(ingredientRepository.findByName("고구마").get())
			.user(saveduser)
			.expireDate(LocalDate.of(2023, 9, 21))
			.build());

		userIngredientRepository.save(UserIngredient.builder()
			.ingredient(ingredientRepository.findByName("튀김가루").get())
			.user(saveduser)
			.expireDate(LocalDate.of(2022, 8, 25))
			.build());

		List<RecipeDto> recipeDtos = recipeElasticService.recommendExpireDateRecipes(saveduser.getId());

		recipeDtos.forEach(recipeDto -> {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(recipeDto.getTitle());
			recipeDto.getIngredients().forEach(
				i -> System.out.println(i.getName())
			);
		});
	}

}
