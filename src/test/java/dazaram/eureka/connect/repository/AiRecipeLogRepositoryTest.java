package dazaram.eureka.connect.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.connect.domain.AiRecipeLog;
import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.repository.AiRecipeRepository;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.Gender;
import dazaram.eureka.user.repository.UserRepository;

@SpringBootTest
@Transactional
class AiRecipeLogRepositoryTest extends RecipeTest {
	@Autowired
	AiRecipeLogRepository aiRecipeLogRepository;
	@Autowired
	AiRecipeRepository aiRecipeRepository;
	@Autowired
	UserRepository userRepository;
	AiRecipe aiRecipe;
	AiRecipeLog aiRecipeLog;
	User user;
	final String userName = "testUser";

	@BeforeEach
	void setUp() {
		aiRecipe = makeAiRecipe(makeExistingRecipe().getId());
		user = User.builder()
			.name(userName)
			.gender(Gender.M)
			.pushAlarmAllow(true)
			.build();
		// when
		userRepository.save(user);
		aiRecipeRepository.save(aiRecipe);
		aiRecipeLog = AiRecipeLog.create(aiRecipe, user);
	}

	@Test
	void AiRecipeLog를_DB에_저장한다() {
		// given

		// when
		AiRecipeLog saved = aiRecipeLogRepository.save(aiRecipeLog);
		// then
		assertAll(
			() -> assertThat(saved.getId()).isNotNull(),
			() -> assertThat(saved.getUser()).isEqualTo(user),
			() -> assertThat(saved.getUser().getId()).isNotNull()
		);
	}

	@Test
	void 유저가_생성한_AiRecipeLog를_조회한다() {
		// given
		AiRecipe savedAiRecipe2 = aiRecipeRepository.save(makeAiRecipe(2L));

		AiRecipeLog saved = aiRecipeLogRepository.save(aiRecipeLog);
		AiRecipeLog saved2 = aiRecipeLogRepository.save(AiRecipeLog.create(savedAiRecipe2, user));
		// when
		List<AiRecipeLog> allByUser = aiRecipeLogRepository.findAllByUser(user);
		// then
		assertAll(
			() -> assertThat(allByUser.size()).isEqualTo(2),
			() -> assertThat(allByUser.get(0)).isEqualTo(saved)

		);
	}
}
