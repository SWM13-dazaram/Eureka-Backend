package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.ingredient.Ingredient;
import dazaram.eureka.ingredient.IngredientRepository;
import dazaram.eureka.user.Gender;
import dazaram.eureka.user.User;
import dazaram.eureka.user.UserRepository;
import dazaram.eureka.useringredient.UserIngredient;
import dazaram.eureka.useringredient.UserIngredientRepository;

@SpringBootTest
@Transactional
class UserIngredientRepositoryTest {

	@Autowired
	UserIngredientRepository userIngredientRepository;
	@Autowired
	IngredientRepository ingredientRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("유저 보유 식재료 추가 테스트")
	public void createUserIngredientTest(){
		Ingredient ingredient = Ingredient.builder()
			.name("가지")
			.expirePeriod(7L)
			.icon("src/eggplant.jpg")
			.build();
		Ingredient saveIngredient = ingredientRepository.save(ingredient);
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		User saveUser = userRepository.save(user);
		UserIngredient userIngredient = UserIngredient.builder()
			.user(user)
			.ingredient(ingredient)
			.memo("test ingredient")
			.build();
		UserIngredient saveUserIngredient = userIngredientRepository.save(userIngredient);

		Assertions.assertEquals(saveUserIngredient.getUser(), saveUser);
		Assertions.assertEquals(saveUserIngredient.getIngredient(), saveIngredient);
	}
}
