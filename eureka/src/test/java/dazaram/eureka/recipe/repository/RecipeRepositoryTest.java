package dazaram.eureka.recipe.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.domain.RecipeSequence;

@SpringBootTest
// @DataJpaTest
// @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional(readOnly = true)
class RecipeRepositoryTest extends RecipeTest {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ExistingRecipeRepository existingRecipeRepository;
	@Autowired
	AiRecipeRepository aiRecipeRepository;
	private ExistingRecipe existingRecipe;
	private AiRecipe aiRecipe;
	private RecipeSequence sequence1;
	private RecipeSequence sequence2;

	@BeforeEach
	void setUp() {
		existingRecipe = makeExistingRecipe();
		sequence1 = makeSequence(SEQ_1, CONTENT_1, existingRecipe);
		sequence2 = makeSequence(SEQ_2, CONTENT_2, existingRecipe);
		existingRecipe.setRecipeSequences(Arrays.asList(sequence1, sequence2));
	}

	@Test
	void existingRecipe를_저장합니다() {
		// given

		// when
		ExistingRecipe saved = existingRecipeRepository.save(existingRecipe);
		//then
		assertAll(
			() -> assertThat(saved.getId()).isNotNull(),
			() -> assertThat(saved).isEqualTo(existingRecipe)
		);
	}

	@Test
	void aiRecipe를_저장합니다() {
		//given

		ExistingRecipe savedExisting = existingRecipeRepository.save(existingRecipe);
		//when
		aiRecipe = makeAiRecipe(savedExisting.getId());
		AiRecipe savedAi = aiRecipeRepository.save(aiRecipe);
		//then
		assertAll(
			() -> assertThat(savedAi.getId()).isNotNull(),
			() -> assertThat(savedAi).isEqualTo(aiRecipe)
		);
	}

	@Test
	void url로_ExistingRecipe을_찾습니다() {
		//given
		ExistingRecipe savedExisting = existingRecipeRepository.save(existingRecipe);
		// 영속성 컨텍스트를 DB에 반영합니다 -> 아래에서 URL로 찾기 위해서
		entityManager.flush();
		//when
		Optional<ExistingRecipe> byUrl = existingRecipeRepository.findByUrl(URL);
		//then
		assertAll(
			() -> assertThat(byUrl.get().getUrl()).isEqualTo(savedExisting.getUrl()),
			() -> assertThat(byUrl.get()).isEqualTo(savedExisting)
		);
	}

	@Test
	void AiRecipe가_참고한_레시피의_아이디로_그_레시피를_찾습니다() {
		//given
		ExistingRecipe savedExisting = existingRecipeRepository.save(existingRecipe);
		aiRecipe = makeAiRecipe(savedExisting.getId());
		AiRecipe savedAi = aiRecipeRepository.save(aiRecipe);
		//when
		ExistingRecipe byReferenceRecipeId = existingRecipeRepository.findById(savedAi.getReferenceRecipeId()).get();

		assertAll(
			() -> assertThat(byReferenceRecipeId).isEqualTo(savedExisting)
		);
	}

}
