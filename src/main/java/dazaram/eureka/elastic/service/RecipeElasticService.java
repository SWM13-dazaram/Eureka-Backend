package dazaram.eureka.elastic.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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
	public void initWithExistingRecipe() {
		recipeElasticRepository.deleteAll();
		double pageContentSize = 5000.0;
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

		User user = userRepository.findById(userId).get();
		List<UserIngredient> sortedUserIngredients = userIngredientRepository.findAllByUser(user).stream()
			.sorted(Comparator.comparing(UserIngredient::getExpireDate))
			.collect(Collectors.toList());

		Double score;
		int originalSize;
		int removedSize;
		StringBuffer stringBuffer = new StringBuffer();
		sortedUserIngredients.stream()
			.map(UserIngredient::getIngredient)
			.map(Ingredient::getName)
			.forEach(name -> stringBuffer.append(name).append(" "));

		Set<Long> userIngredientsIds = new HashSet<>(sortedUserIngredients.stream()
			.map(UserIngredient::getIngredient)
			.map(Ingredient::getId)
			.collect(Collectors.toList()));

		String nameList = stringBuffer.toString().strip();

		System.out.println(nameList);

		List<RecipeDocument> byIngredientsNameList = recipeElasticQueryRepository.findByIngredientsNameList(nameList);
		//도큐먼트 돌리면서 유저가 보유한 식재료랑 차집합 0인애들을 위로 올린다
		//그다음에 레시피 재료량 높을수록 위쪽에
		// recipeDocument.sort();

		Integer ingredientsMaxSize = byIngredientsNameList.stream()
			.map(recipeDocument -> recipeDocument.getIngredients().size())
			.max(Integer::compare).orElse(10000);
		System.out.println("ingredientsMaxSize");
		System.out.println(ingredientsMaxSize);

		//상위 임박일자 점수 매기기 /  이거 다 더한 최대값은 userIngredientsSize
		// 최소 0 최대 1 , 하나라도 있으면 1/100
		HashMap<Long, Double> ingredientIdAndScore = new HashMap<>();
		for (UserIngredient userIngredient : sortedUserIngredients) {
			long expire = ChronoUnit.DAYS.between(LocalDate.now(), userIngredient.getExpireDate());
			score = 0.0;
			if (expire < 100) {
				score = ((100 - expire) / 100.0);
			}
			ingredientIdAndScore.put(userIngredient.getIngredientId(), score);
		}

		int userIngredientsSize = sortedUserIngredients.size();

		System.out.println("userIngredientsSize");
		System.out.println(userIngredientsSize);

		HashMap<Integer, Double> indexAndScore = new HashMap<>();
		int index = 0;
		for (RecipeDocument recipeDocument : byIngredientsNameList) {
			Set<Long> recipeIngredientsIds = new HashSet<>(recipeDocument.getAllIngredientsIds());
			originalSize = recipeIngredientsIds.size();
			// 차집합 계산
			recipeIngredientsIds.removeAll(userIngredientsIds);
			removedSize = recipeIngredientsIds.size();

			// score 계산 이 레시피에 사용된 내 식재료 퍼센트(0~100) * ingredientsMaxSize (최소값 : 0, 최대값 ingredientsMaxSize *100)
			// + 레시피에 필요한 재료 갯수/ ingredientsMaxSize(최대값 1/2,모두 사용한거라면 똑같은 값 나오므로 많은 재료 필요한 레시피 우선순위 높게)
			// 많이 필요한 레시피일수록 값이 높게 설정
			// 정렬을 위해 음수화

			// 여기서 임박날짜 얼마 남았느지 계산하기

			score =
				(((originalSize - removedSize) / (double)originalSize) * 100 * ingredientsMaxSize * userIngredientsSize)
					+ (originalSize / ingredientsMaxSize / 200.0);
			System.out.println(score);
			// 교집합 계산
			Set<Long> intersection = new HashSet<>(recipeDocument.getAllIngredientsIds());
			intersection.retainAll(userIngredientsIds);
			// 하나라도 있으면 1/200 보다 커야한다  다 더해도 ingredientsMaxSize*userIngredientsSize 보다 작아야한다
			// 하나라도 있으면 1
			for (Long ingredientId : intersection) {
				score += ingredientIdAndScore.get(ingredientId);
			}
			//내림차순을 위한 음수화
			score = -score;
			indexAndScore.put(index, score);
			index += 1;
		}

		List<Map.Entry<Integer, Double>> indexAndScoreEntry = indexAndScore.entrySet().stream()
			.sorted(Map.Entry.comparingByValue())
			.collect(Collectors.toList());

		// to DTO짜기
		List<RecipeDto> recipeDtos = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			recipeDtos.add(RecipeDto.fromDocument(byIngredientsNameList.get(indexAndScoreEntry.get(i).getKey())));
		}

		return recipeDtos;
	}

}
