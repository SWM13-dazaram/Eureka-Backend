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

import dazaram.eureka.connect.domain.ReplaceIngredient;
import dazaram.eureka.connect.repository.ReplaceIngredientRepository;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.AiRecipe;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.domain.RecipeSequence;

@SpringBootTest
// @DataJpaTest
// @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional
class RecipeRepositoryTest extends RecipeTest {
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ExistingRecipeRepository existingRecipeRepository;
	@Autowired
	AiRecipeRepository aiRecipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	ReplaceIngredientRepository replaceIngredientRepository;
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
	void existingRecipe를_저장한다() {
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
	void aiRecipe를_저장한다() {
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
	void url로_ExistingRecipe을_찾는온() {
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
	void AiRecipe가_참고한_레시피의_아이디로_그_레시피를_찾는다() {
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

	@Test
	void id로_ExistingRecipe을_찾고_대체된_식재료를_조회한다() {
		final String replaceIngredientName = "replace";
		final String missingIngredientName = "missing";
		//given
		ExistingRecipe savedExisting = existingRecipeRepository.save(existingRecipe);
		AiRecipe aiRecipe = makeAiRecipe(savedExisting.getId());

		Ingredient replace = Ingredient.builder()
			.name(replaceIngredientName)
			.build();
		Ingredient missing = Ingredient.builder()
			.name(missingIngredientName)
			.build();

		ReplaceIngredient replaceIngredient = ReplaceIngredient.builder()
			.replaceIngredient(replace)
			.missingIngredient(missing)
			.similarity(1.2f)
			.build();

		aiRecipe.setReplaceIngredient(replaceIngredient);

		ingredientRepository.save(replace);
		ingredientRepository.save(missing);
		AiRecipe savedAiRecipe = aiRecipeRepository.save(aiRecipe);
		replaceIngredientRepository.save(replaceIngredient);

		//when
		AiRecipe byId = aiRecipeRepository.findById(savedAiRecipe.getId()).get();
		//then
		assertAll(
			() -> assertThat(byId.getReplaceIngredient()).isEqualTo(replaceIngredient),
			() -> assertThat(byId.getReplaceIngredient().getReplaceIngredient()).isEqualTo(replace),
			() -> assertThat(byId.getReplaceIngredient().getMissingIngredient().getName()).isEqualTo(
				missingIngredientName)

		);
	}

}
