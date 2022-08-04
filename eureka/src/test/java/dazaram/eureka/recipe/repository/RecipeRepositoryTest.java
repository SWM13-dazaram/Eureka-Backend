package dazaram.eureka.recipe.repository;

import static dazaram.eureka.recipe.domain.RecipeTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.domain.RecipeSequence;

@SpringBootTest
// @DataJpaTest
// @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional(readOnly = true)
class RecipeRepositoryTest {

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
		sequence1 = makeSequence(SEQ1, CONTENT1, existingRecipe);
		sequence2 = makeSequence(SEQ2, CONTENT2, existingRecipe);
		existingRecipe.setRecipeSequences(Arrays.asList(sequence1, sequence2));
	}

	@Test
	@Transactional
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
	@Transactional
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
	@Transactional
	void url로_ExistingRecipe을_찾습니다() {
		//given
		ExistingRecipe savedExisting = existingRecipeRepository.save(existingRecipe);
		//when
		ExistingRecipe byUrl = existingRecipeRepository.findByUrl(URL).get();
		//then
		assertAll(
			() -> assertThat(byUrl.getUrl()).isEqualTo(savedExisting.getUrl()),
			() -> assertThat(byUrl).isEqualTo(savedExisting)
		);
	}

	@Test
	@Transactional
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
