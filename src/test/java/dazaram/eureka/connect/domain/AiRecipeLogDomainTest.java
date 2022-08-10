package dazaram.eureka.connect.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.user.domain.Gender;
import dazaram.eureka.user.domain.User;

class AiRecipeLogDomainTest extends RecipeTest {
	final String userName = "testUser";

	@Test
	void AiRecipeLog를_1개_생성한다() {
		// given
		AiRecipe aiRecipe = makeAiRecipe(makeExistingRecipe().getId());
		User user = User.builder()
			.name(userName)
			.gender(Gender.M)
			.build();
		// when
		AiRecipeLog aiRecipeLog = AiRecipeLog.create(aiRecipe, user);
		// then
		assertAll(
			() -> assertThat(aiRecipeLog.getRecipe()).isEqualTo(aiRecipe),
			() -> assertThat(aiRecipeLog.getUser()).isEqualTo(user)
		);
	}
}
