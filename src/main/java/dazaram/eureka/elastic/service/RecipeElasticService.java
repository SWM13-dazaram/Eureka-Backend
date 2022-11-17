package dazaram.eureka.elastic.service;

import static dazaram.eureka.common.error.ErrorCode.*;

import java.util.AbstractMap;
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

import dazaram.eureka.common.exception.CustomException;
import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.elastic.repository.RecipeElasticQueryRepository;
import dazaram.eureka.elastic.repository.RecipeElasticRepository;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import dazaram.eureka.recipe.dto.ExpireDateRecipeDto;
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

	public List<ExpireDateRecipeDto> recommendExpireDateRecipes(Long userId, int topRank) {
		double minScore;

		User user = getCurrentUser(userId);

		List<UserIngredient> userIngredients = userIngredientRepository.findAllByUser(user);
		validateUserIngredients(userIngredients);

		String nameList = getStringBuffer(userIngredients);
		List<RecipeDocument> queryResults = recipeElasticQueryRepository.findByIngredientsNameList(nameList);
		validateQueryResults(queryResults);

		Set<Long> userIngredientsIds = getUserIngredientsIds(userIngredients);

		HashMap<Integer, Double> indexAndScore = getTotalScore(userIngredients, userIngredientsIds, queryResults);

		List<Map.Entry<Integer, Double>> sortedIndexAndScoreEntry = sortByScore(indexAndScore);
		// 100 * ingredientsMaxSize * userIngredientsSize
		minScore = 100.0 * getIngredientsMaxSize(queryResults) * userIngredients.size();

		return getTopPerfectRecipeDtos(userIngredients, queryResults, sortedIndexAndScoreEntry, topRank, minScore);
	}

	private void validateUserIngredients(List<UserIngredient> userIngredients) {
		if (userIngredients.isEmpty()) {
			throw new CustomException(USERINGREDIENT_NOT_FOUND);
		}
	}

	private void validateQueryResults(List<RecipeDocument> queryResults) {
		if (queryResults.isEmpty()) {
			throw new CustomException(ELASTIC_RESULT_NOT_FOUND);
		}
	}

	private List<ExpireDateRecipeDto> getTopPerfectRecipeDtos(List<UserIngredient> userIngredients,
		List<RecipeDocument> queryResults, List<Map.Entry<Integer, Double>> indexAndScoreEntry, int topRank,
		double minScore) {
		List<ExpireDateRecipeDto> recipeDtos = new ArrayList<>();
		for (int i = 0; i < topRank; i++) {
			Map.Entry<Integer, Double> indexAndScore = indexAndScoreEntry.get(i);
			if (Math.abs(indexAndScore.getValue()) < minScore) {
				break;
			}
			Ingredient imminentIngredient = getImminentIngredient(queryResults.get(indexAndScore.getKey()),
				userIngredients);
			recipeDtos.add(
				ExpireDateRecipeDto.fromDocument(queryResults.get(indexAndScore.getKey()), imminentIngredient));
		}
		validateRecipeDtos(recipeDtos);
		return recipeDtos;
	}

	private Ingredient getImminentIngredient(RecipeDocument recipeDocument, List<UserIngredient> userIngredients) {
		List<Long> ingredientsIds = recipeDocument.getAllIngredientsIds();
		Map.Entry<Ingredient, Long> ret = new AbstractMap.SimpleEntry<>(null, Long.MAX_VALUE);
		for (UserIngredient userIngredient : userIngredients) {
			long expire = userIngredient.calculateExpireFromNow();
			if (ret.getValue() > expire) {
				ret = new AbstractMap.SimpleEntry<>(userIngredient.getIngredient(), expire);
			}
		}
		return ret.getKey();
	}

	private void validateRecipeDtos(List<ExpireDateRecipeDto> recipeDtos) {
		if (recipeDtos.isEmpty()) {
			throw new CustomException(RECIPE_USERINGREDIENT_NO_CONTENT);
		}
	}

	private List<Map.Entry<Integer, Double>> sortByScore(HashMap<Integer, Double> indexAndScore) {
		return indexAndScore.entrySet().stream()
			.sorted(Map.Entry.comparingByValue())
			.collect(Collectors.toList());
	}

	private HashMap<Integer, Double> getTotalScore(List<UserIngredient> userIngredients,
		Set<Long> userIngredientsIds, List<RecipeDocument> queryResults) {

		double score = 0;
		int index = 0;
		int userIngredientsSize = userIngredients.size();
		int ingredientsMaxSize = getIngredientsMaxSize(queryResults);

		HashMap<Long, Double> ingredientIdAndExpireScore = getExpireScorePerIngredient(userIngredients);
		HashMap<Integer, Double> indexAndTotalScore = new HashMap<>();

		for (RecipeDocument recipeDocument : queryResults) {
			Set<Long> recipeIngredientsIds = new HashSet<>(recipeDocument.getAllIngredientsIds());

			int originalSize = recipeIngredientsIds.size();
			//이 레시피의 재료들 중에서 내가 가지고 있는 것 만 남긴다
			recipeIngredientsIds.retainAll(userIngredientsIds);
			int usedSize = recipeIngredientsIds.size();

			score = getUsedScore(userIngredientsSize, ingredientsMaxSize, originalSize, usedSize)
				+ getQuantityScore(ingredientsMaxSize, originalSize)
				+ getExpireScore(ingredientIdAndExpireScore, recipeIngredientsIds);
			//내림차순을 위한 음수화
			score = -score;
			indexAndTotalScore.put(index, score);
			index += 1;
		}
		return indexAndTotalScore;
	}

	/*
		하나라도 있으면 score가 1/200 보다 커야한다 다 더해도 ingredientsMaxSize * userIngredientsSize 보다 작아야한다
		이유 : 1순위: 레시피의 필요 재료를 만족하는가
		2순위: 유통기한 임박 식재료가 쓰였는가
		3순위: 얼마나 많은 재료가 레시피에 필요한가
		만약 모든 재료가 오늘 마감이고 모든 재료를 레시피에서 사용한다면 ExpireScore는  userIngredientsSize 이다.
	*/
	private double getExpireScore(HashMap<Long, Double> ingredientIdAndExpireScore, Set<Long> recipeIngredientsIds) {
		return recipeIngredientsIds.stream()
			.mapToDouble(ingredientIdAndExpireScore::get)
			.sum();
	}

	/*
		QuantityScore (많은 재료가 필요한 레시피 score 높음 범위 : 0~1/200)
		재료가 많이 필요한 레시피일수록 score 높게 설정
		레시피에 필요한 재료 갯수/ ingredientsMaxSize
	 */
	private double getQuantityScore(int ingredientsMaxSize, int originalSize) {
		return originalSize / ingredientsMaxSize / 200.0;
	}

	/*
		UsedScore
		이 레시피에 사용된 내 식재료 퍼센트(0~100) * ingredientsMaxSize * userIngredientsSize
		(최소값 : 0, 최대값 100 * ingredientsMaxSize * userIngredientsSize)
	*/
	private double getUsedScore(int userIngredientsSize, int ingredientsMaxSize, int originalSize, int usedSize) {
		double usedPercent = ((usedSize) / (double)originalSize) * 100;
		return usedPercent * ingredientsMaxSize * userIngredientsSize;
	}

	/*
		유통기한 임박일자 score 매기기
		한 score 최소 0 최대 1 하나 있으면 1/100
	 */
	private HashMap<Long, Double> getExpireScorePerIngredient(List<UserIngredient> userIngredients) {
		double score;
		HashMap<Long, Double> ingredientIdAndScore = new HashMap<>();

		for (UserIngredient userIngredient : userIngredients) {
			long expire = userIngredient.calculateExpireFromNow();
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
			.max(Integer::compare).orElse(10000);
	}

	private HashSet<Long> getUserIngredientsIds(List<UserIngredient> userIngredients) {
		return userIngredients.stream()
			.map(UserIngredient::getIngredientId)
			.collect(Collectors.toCollection(HashSet::new));
	}

	private String getStringBuffer(List<UserIngredient> sortedUserIngredients) {
		StringBuffer stringBuffer = new StringBuffer();

		sortedUserIngredients.stream()
			.map(UserIngredient::getIngredientName)
			.forEach(name -> stringBuffer.append(name).append(" "));
		return stringBuffer.toString().strip();
	}

	private User getCurrentUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(UNAUTHORIZED_USER));
	}

}
