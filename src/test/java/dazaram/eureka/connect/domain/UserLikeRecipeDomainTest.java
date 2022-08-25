package dazaram.eureka.connect.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.Gender;

class UserLikeRecipeDomainTest extends RecipeTest {

	@Test
	void ExistingRecipe의_좋아요를_1개_생성한다() {
		// given
		ExistingRecipe existingRecipe = makeExistingRecipe();
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		// when
		UserLikeRecipe userLikeRecipe = UserLikeRecipe.create(existingRecipe, user);
		// then
		assertAll(
			() -> assertThat(userLikeRecipe.getRecipe()).isEqualTo(existingRecipe),
			() -> assertThat(userLikeRecipe.getUser()).isEqualTo(user)
		);
	}

	@Test
	void AiRecipe의_좋아요를_1개_생성한다() {
		// given
		AiRecipe aiRecipe = makeAiRecipe(makeExistingRecipe().getId());
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		// when
		UserLikeRecipe userLikeRecipe = UserLikeRecipe.create(aiRecipe, user);
		// then
		assertAll(
			() -> assertThat(userLikeRecipe.getRecipe()).isEqualTo(aiRecipe),
			() -> assertThat(userLikeRecipe.getUser()).isEqualTo(user)
		);
	}
}
