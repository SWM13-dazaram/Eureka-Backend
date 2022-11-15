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
import dazaram.eureka.recipe.dto.ExpireDateRecipeDto;
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
		saveUserIngredient("양파", saveduser, 2022, 8, 21);
		saveUserIngredient("고구마", saveduser, 2022, 8, 21);
		saveUserIngredient("감자", saveduser, 2022, 8, 21);
		saveUserIngredient("오일", saveduser, 2023, 9, 21);
		saveUserIngredient("튀김가루", saveduser, 2022, 8, 25);
		saveUserIngredient("물", saveduser, 2022, 8, 21);
		saveUserIngredient("간장", saveduser, 2022, 8, 21);
		saveUserIngredient("양조간장", saveduser, 2022, 8, 21);
		saveUserIngredient("맛술", saveduser, 2022, 8, 21);
		saveUserIngredient("다진마늘", saveduser, 2022, 8, 21);
		saveUserIngredient("후춧가루", saveduser, 2022, 8, 21);
		saveUserIngredient("깨", saveduser, 2022, 8, 21);
		saveUserIngredient("대파", saveduser, 2022, 8, 21);
		saveUserIngredient("고추장", saveduser, 2022, 8, 21);
		saveUserIngredient("고춧가루", saveduser, 2022, 8, 21);
		saveUserIngredient("닭", saveduser, 2022, 8, 21);

		List<ExpireDateRecipeDto> recipeDtos = recipeElasticService.recommendExpireDateRecipes(saveduser.getId(), 3);

		recipeDtos.forEach(recipeDto -> {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(recipeDto.getTitle());
			recipeDto.getIngredients().forEach(
				i -> System.out.println(i.getName())
			);
		});
	}

	private void saveUserIngredient(String name, User saveduser, int year, int month, int dayOfMonth) {
		userIngredientRepository.save(UserIngredient.builder()
			.ingredient(ingredientRepository.findByName(name).get())
			.user(saveduser)
			.expireDate(LocalDate.of(year, month, dayOfMonth))
			.build());
	}

}
