package dazaram.eureka.elastic.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.elastic.repository.RecipeElasticQueryRepository;
import dazaram.eureka.elastic.repository.RecipeElasticRepository;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import dazaram.eureka.recipe.domain.dto.RecipeDto;
import dazaram.eureka.recipe.repository.ExistingRecipeRepository;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeElasticService {
	private final RecipeElasticRepository recipeElasticRepository;
	private final ExistingRecipeRepository existingRecipeRepository;

	private final RecipeElasticQueryRepository recipeElasticQueryRepository;
	private final UserIngredientRepository userIngredientRepository;
	private final UserRepository userRepository;

	@Transactional
	public void initWithExistingRecipe(int bulkSize) {
		recipeElasticRepository.deleteAll();
		double pageContentSize = bulkSize * 1.0;
		IntStream.rangeClosed(0, (int)Math.ceil(existingRecipeRepository.findAll().size() / (pageContentSize)))
			.forEach(i -> {
				List<RecipeDocument> recipeDocuments = existingRecipeRepository.findAllExistingRecipeForElastic(
						PageRequest.of(i, (int)pageContentSize)).getContent().stream()
					.map(RecipeDocument::new)
					.collect(Collectors.toList());
				recipeElasticRepository.saveAll(recipeDocuments);
			});
	}

	public List<RecipeDto> recommendExpireDateRecipes(Long userId) {

		User user = getCurrentUser(userId);

		List<UserIngredient> userIngredients = userIngredientRepository.findAllByUser(user);

		String nameList = getStringBuffer(userIngredients);

		List<RecipeDocument> queryResults = recipeElasticQueryRepository.findByIngredientsNameList(nameList);

		Set<Long> userIngredientsIds = getUserIngredientsIds(userIngredients);

		HashMap<Long, Double> ingredientIdAndExpireScore = getExpireScoreMap(userIngredients);

		HashMap<Integer, Double> indexAndScore = getIntegerDoubleHashMap(userIngredients.size(),
			userIngredientsIds, queryResults, ingredientIdAndExpireScore);

		List<Map.Entry<Integer, Double>> indexAndScoreEntry = getIndexAndScoreEntry(indexAndScore);

		return getRecipeDtos(queryResults, indexAndScoreEntry);
	}

	private List<RecipeDto> getRecipeDtos(List<RecipeDocument> queryResults,
		List<Map.Entry<Integer, Double>> indexAndScoreEntry) {
		List<RecipeDto> recipeDtos = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			recipeDtos.add(RecipeDto.fromDocument(queryResults.get(indexAndScoreEntry.get(i).getKey())));
		}
		return recipeDtos;
	}

	private List<Map.Entry<Integer, Double>> getIndexAndScoreEntry(HashMap<Integer, Double> indexAndScore) {
		return indexAndScore.entrySet().stream()
			.sorted(Map.Entry.comparingByValue())
			.collect(Collectors.toList());
	}

	private HashMap<Integer, Double> getIntegerDoubleHashMap(int userIngredientsSize,
		Set<Long> userIngredientsIds, List<RecipeDocument> queryResults,
		HashMap<Long, Double> ingredientIdAndExpireScore) {

		double score = 0;
		int index = 0;

		int ingredientsMaxSize = getIngredientsMaxSize(queryResults);

		HashMap<Integer, Double> indexAndScore = new HashMap<>();

		for (RecipeDocument recipeDocument : queryResults) {
			Set<Long> recipeIngredientsIds = new HashSet<>(recipeDocument.getAllIngredientsIds());

			// 차집합
			int originalSize = recipeIngredientsIds.size();
			recipeIngredientsIds.removeAll(userIngredientsIds);
			int removedSize = recipeIngredientsIds.size();

			score = getUsedScore(userIngredientsSize, ingredientsMaxSize, originalSize, removedSize)
				+ getQuantityScore(ingredientsMaxSize, originalSize)
				+ getExpireScore(userIngredientsIds, ingredientIdAndExpireScore, recipeDocument.getAllIngredientsIds());
			//내림차순을 위한 음수화
			score = -score;
			indexAndScore.put(index, score);
			index += 1;
		}
		return indexAndScore;
	}

	// 하나라도 있으면 score가 1/200 보다 커야한다 다 더해도 ingredientsMaxSize*userIngredientsSize 보다 작아야한다
	// 이유 : 1순위: 레시피의 필요 재료를 만족하는가
	// 		2순위: 유통기한 임박 식재료가 쓰였는가
	// 		3순위: 얼마나 많은 재료가 레시피에 필요한가
	private double getExpireScore(Set<Long> userIngredientsIds, HashMap<Long, Double> ingredientIdAndExpireScore,
		List<Long> ingredientsIds) {
		double expireScore = 0;
		Set<Long> intersection = new HashSet<>(ingredientsIds);
		intersection.retainAll(userIngredientsIds);
		for (Long ingredientId : intersection) {
			expireScore += ingredientIdAndExpireScore.get(ingredientId);
		}
		return expireScore;
	}

	// 재료가 많이 필요한 레시피일수록 score 높게 설정
	private double getQuantityScore(int ingredientsMaxSize, int originalSize) {
		return originalSize / ingredientsMaxSize / 200.0;
	}

	// score 계산 이 레시피에 사용된 내 식재료 퍼센트(0~100) * ingredientsMaxSize * 100(최소값 : 0, 최대값 ingredientsMaxSize *100)
	// + 레시피에 필요한 재료 갯수/ ingredientsMaxSize(최대값 1/200, 모두 사용한거라면 똑같은 값 나오므로 많은 재료 필요한 레시피 우선순위 높게)
	private double getUsedScore(int userIngredientsSize, int ingredientsMaxSize, int originalSize, int removedSize) {
		return ((originalSize - removedSize) / (double)originalSize) * 100 * ingredientsMaxSize * userIngredientsSize;
	}

	/*
	유통기한 임박일자 score 매기기
	한 score 최소 0 최대 1 하나 있으면 1/100
	각 재료들의 최대 score 다 더한 최대값은 userIngredientsSize
	 */
	private HashMap<Long, Double> getExpireScoreMap(List<UserIngredient> userIngredients) {
		double score;
		HashMap<Long, Double> ingredientIdAndScore = new HashMap<>();

		for (UserIngredient userIngredient : userIngredients) {
			long expire = ChronoUnit.DAYS.between(LocalDate.now(), userIngredient.getExpireDate());
			score = 0.0;
			if (expire < 100) {
				score = ((100 - expire) / 100.0);
			}
			ingredientIdAndScore.put(userIngredient.getIngredientId(), score);
		}
		return ingredientIdAndScore;
	}

	private int getIngredientsMaxSize(List<RecipeDocument> queryResults) {
		return queryResults.stream()
			.map(recipeDocument -> recipeDocument.getIngredients().size())
			.max(Integer::compare).orElse(1000);
	}

	private HashSet<Long> getUserIngredientsIds(List<UserIngredient> userIngredients) {
		return userIngredients.stream()
			.map(UserIngredient::getIngredientId)
			.collect(Collectors.toCollection(HashSet::new));
	}

	private String getStringBuffer(List<UserIngredient> sortedUserIngredients) {
		StringBuffer stringBuffer = new StringBuffer();

		sortedUserIngredients.stream()
			.map(UserIngredient::getIngredient)
			.map(Ingredient::getName)
			.forEach(name -> stringBuffer.append(name).append(" "));
		return stringBuffer.toString().strip();
	}

	private User getCurrentUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다"));
	}

}
