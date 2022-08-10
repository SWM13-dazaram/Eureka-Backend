package dazaram.eureka.connect.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.connect.UserLikeRecipe;
import dazaram.eureka.recipe.RecipeTest;
import dazaram.eureka.recipe.domain.ExistingRecipe;
import dazaram.eureka.recipe.repository.ExistingRecipeRepository;
import dazaram.eureka.user.domain.Gender;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.repository.UserRepository;

@SpringBootTest
@Transactional
class UserLikeRecipeRepositoryTest extends RecipeTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserLikeRecipeRepository userLikeRecipeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ExistingRecipeRepository existingRecipeRepository;

	public final String USER_NAME = "testUser";

	private UserLikeRecipe userLikeRecipe;
	private User user;
	private ExistingRecipe existingRecipe;

	public User makeUser() {
		return User.builder()
			.name(USER_NAME)
			.gender(Gender.M)
			.build();
	}

	@BeforeEach
	void setUp() {
		user = makeUser();
		userRepository.save(user);
		existingRecipe = makeExistingRecipe();
		existingRecipeRepository.save(existingRecipe);
	}

	@Test
	void 레시피에_대한_좋아요_정보를_DB에_저장합니다() {
		// given
		userLikeRecipe = UserLikeRecipe.create(existingRecipe, user);
		// when
		UserLikeRecipe saved = userLikeRecipeRepository.save(userLikeRecipe);
		// then
		assertAll(
			() -> assertThat(saved.getId()).isNotNull(),
			() -> assertThat(saved.getUser()).isEqualTo(user),
			() -> assertThat(saved.getRecipe()).isEqualTo(existingRecipe)
		);
	}

	@Test
	void 레시피의_좋아요_갯수를_조회한다() {
		// given
		//좋아요 5개 만들기
		userLikeRecipe = UserLikeRecipe.create(existingRecipe, user);
		userLikeRecipeRepository.save(userLikeRecipe);

		User saved1 = userRepository.save(makeUser());
		userLikeRecipeRepository.save(UserLikeRecipe.create(existingRecipe, saved1));
		saved1 = userRepository.save(makeUser());
		userLikeRecipeRepository.save(UserLikeRecipe.create(existingRecipe, saved1));
		saved1 = userRepository.save(makeUser());
		userLikeRecipeRepository.save(UserLikeRecipe.create(existingRecipe, saved1));
		saved1 = userRepository.save(makeUser());
		userLikeRecipeRepository.save(UserLikeRecipe.create(existingRecipe, saved1));

		// entityManager.flush();
		// when
		List<UserLikeRecipe> userLikeRecipes = userLikeRecipeRepository.findAllByRecipe(existingRecipe);

		//then
		assertAll(
			() -> assertThat(userLikeRecipes.size()).isEqualTo(5)
		);
	}

	@Test
	void 유저가_좋아요_누른_레시피를_확인합니다() {
		// given
		userLikeRecipe = UserLikeRecipe.create(existingRecipe, user);
		UserLikeRecipe saved = userLikeRecipeRepository.save(userLikeRecipe);
		// when
		UserLikeRecipe byUserAndRecipe = userLikeRecipeRepository.findByUserAndRecipe(user, existingRecipe).get();
		// then
		assertAll(
			() -> assertThat(byUserAndRecipe.getId()).isNotNull(),
			() -> assertThat(byUserAndRecipe.getUser()).isEqualTo(user),
			() -> assertThat(byUserAndRecipe.getRecipe()).isEqualTo(existingRecipe)
		);
	}

}
