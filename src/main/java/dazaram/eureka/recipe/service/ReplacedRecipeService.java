package dazaram.eureka.recipe.service;

import static dazaram.eureka.common.error.ErrorCode.*;
import static dazaram.eureka.security.util.SecurityUtil.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.common.exception.CustomException;
import dazaram.eureka.elastic.domain.RecipeDocument;
import dazaram.eureka.elastic.repository.RecipeElasticQueryRepository;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import dazaram.eureka.ingredient.repository.IngredientRepository;
import dazaram.eureka.ingredient.repository.UserIngredientRepository;
import dazaram.eureka.ingredient.service.IngredientService;
import dazaram.eureka.recipe.domain.Ingr2Vec;
import dazaram.eureka.recipe.dto.ReplacedRecipeDto;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplacedRecipeService {

	private final IngredientService ingredientService;
	private final UserIngredientRepository userIngredientRepository;
	private final RecipeElasticQueryRepository recipeElasticQueryRepository;
	private final IngredientRepository ingredientRepository;
	private final UserRepository userRepository;
	private final Ingr2Vec ingr2Vec;

	// TODO : 코드 모듈화
	public List<ReplacedRecipeDto> getReplacedRecipes() {
		User currentUser = userRepository.findById(getCurrentUserId()).get();

		List<UserIngredient> userIngredients = userIngredientRepository.findAllByUser(currentUser);
		String nameList = getStringBuffer(userIngredients);
		List<RecipeDocument> queryResult = recipeElasticQueryRepository.findByIngredientsNameList(nameList);

		Set<Long> userIngredientsIds = userIngredients.stream()
			.map(UserIngredient::getIngredientId)
			.collect(Collectors.toCollection(HashSet::new));

		List<ReplacedRecipeDto> recommendResult = new ArrayList<>();
		for (RecipeDocument recipeDocument : queryResult) {
			Set<Long> recipeIngredientsIds = new HashSet<>(recipeDocument.getAllIngredientsIds());

			if (recipeIngredientsIds.size() <= 3)
				continue;

			recipeIngredientsIds.removeAll(userIngredientsIds);

			if (recipeIngredientsIds.size() == 1) {
				Long missingIngredientId = recipeIngredientsIds.iterator().next();
				Ingredient missingIngredient = ingredientRepository.findById(missingIngredientId).get();

				Map.Entry<Long, Double> replacedIngredientInfo = getReplacedIngredient(missingIngredientId,
					userIngredientsIds, recipeDocument.getAllIngredientsIds());

				Long replacedIngredientId = replacedIngredientInfo.getKey();
				if (replacedIngredientId == 0L)
					continue;
				Ingredient replacedIngredient = ingredientRepository.findById(replacedIngredientId).get();

				Double similarity = replacedIngredientInfo.getValue();

				ReplacedRecipeDto replacedRecipeDto = ReplacedRecipeDto.fromRecipeDocument(recipeDocument,
					missingIngredient, replacedIngredient, similarity);
				recommendResult.add(replacedRecipeDto);
			}
		}

		recommendResult = recommendResult.stream()
			.sorted(Comparator.comparing(o -> o.getReplacement().getSimilarity()))
			.collect(Collectors.toList());
		Collections.reverse(recommendResult);

		if (recommendResult.size() > 3) {
			recommendResult = recommendResult.subList(0, 3);
		}

		validateRecommendResult(recommendResult);

		return recommendResult;
	}

	private void validateRecommendResult(List<ReplacedRecipeDto> recommendResult) {
		if (recommendResult.isEmpty()) {
			throw new CustomException(RECIPE_USERINGREDIENT_NO_CONTENT);
		}
	}

	private Map.Entry<Long, Double> getReplacedIngredient(Long missingIngredientId, Set<Long> userIngredientIds,
		List<Long> recipeIngredientsIds) {
		Map.Entry<Long, Double> ret = new AbstractMap.SimpleEntry<>(0L, 0.0);
		Ingredient missingIngredient = ingredientRepository.findById(missingIngredientId).get();

		for (Long ingredientId : userIngredientIds) {
			if (recipeIngredientsIds.contains(ingredientId)) {
				continue;
			}
			Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
			Double similarity = getCosineSimilarity(missingIngredient, ingredient);
			if (similarity > ret.getValue()) {
				ret = new AbstractMap.SimpleEntry<>(ingredientId, similarity);
			}
		}
		return ret;
	}

	private Double getCosineSimilarity(Ingredient missingIngredient, Ingredient compareIngredient) {
		List<Double> missingIngredientVec = getIngredientVector(missingIngredient.getName());
		List<Double> compareIngredientVec = getIngredientVector(compareIngredient.getName());
		Double NormA = getL2Norm(missingIngredientVec);
		Double NormB = getL2Norm(compareIngredientVec);
		if (NormA == 0 || NormB == 0)
			return 0.0;
		return getDotProduct(missingIngredientVec, compareIngredientVec) / (NormA * NormB) * 100;
	}

	private List<Double> getIngredientVector(String ingredientName) {
		if (ingr2Vec.getKeyset().contains(ingredientName))
			return ingr2Vec.getValue(ingredientName);
		return DoubleStream.of(new double[250])
			.boxed()
			.collect(Collectors.toList());
	}

	private Double getDotProduct(List<Double> a, List<Double> b) {
		double ret = 0.0;
		for (int i = 0; i < a.size(); i++) {
			ret += a.get(i) * b.get(i);
		}
		return ret;
	}

	private Double getL2Norm(List<Double> v) {
		double ret = 0.0;
		for (int i = 0; i < v.size(); i++) {
			ret += v.get(i) * v.get(i);
		}
		return Math.sqrt(ret);
	}

	private String getStringBuffer(List<UserIngredient> userIngredients) {
		StringBuffer stringBuffer = new StringBuffer();

		userIngredients.stream()
			.map(UserIngredient::getIngredientName)
			.forEach(name -> stringBuffer.append(name).append(" "));
		return stringBuffer.toString().strip();
	}
}
