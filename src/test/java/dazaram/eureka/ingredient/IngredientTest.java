package dazaram.eureka.ingredient;

import java.time.LocalDate;

import dazaram.eureka.ingredient.domain.CustomIngredient;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.ingredient.domain.IngredientCategory;
import dazaram.eureka.ingredient.domain.UserIngredient;

public abstract class IngredientTest {

	public static final IngredientCategory INGREDIENT_CATEGORY = IngredientCategory.VEGETABLE;

	public static final String CUSTOM_INGREDIENT_NAME = "상어고기";
	public static final String CUSTOM_INGREDIENT_ICON = "src/shark_meat.jpg";
	public static final String CUSTOM_INGREDIENT_MEMO = "손질 방법은 삼촌에게 여쭤보기";

	public static final String INGREDIENT_NAME = "양파";
	public static final Long INGREDIENT_EXPIRE_PERIOD = 6L;
	public static final String INGREDIENT_ICON = "src/onion.jpg";

	public static final String USER_INGREDIENT_NAME = "저번주에 산 양파";
	public static final LocalDate USER_INGREDIENT_INSERT_DATE = LocalDate.of(2022, 8, 2);
	public static final LocalDate USER_INGREDIENT_EXPIRE_DATE = LocalDate.of(2022, 8, 8);
	public static final String USER_INGREDIENT_MEMO = "이마트에서 삼";

	public CustomIngredient createCustomIngredient() {
		return CustomIngredient.builder()
			.name(CUSTOM_INGREDIENT_NAME)
			.icon(CUSTOM_INGREDIENT_ICON)
			.memo(CUSTOM_INGREDIENT_MEMO)
			.ingredientCategory(INGREDIENT_CATEGORY)
			.build();
	}

	public Ingredient createIngredient() {
		return Ingredient.builder()
			.name(INGREDIENT_NAME)
			.expirePeriod(INGREDIENT_EXPIRE_PERIOD)
			.icon(INGREDIENT_ICON)
			.ingredientCategory(INGREDIENT_CATEGORY)
			.build();
	}

	public UserIngredient createUserIngredient(Ingredient ingredient) {
		return UserIngredient.builder()
			.name(USER_INGREDIENT_NAME)
			.insertDate(USER_INGREDIENT_INSERT_DATE)
			.expireDate(USER_INGREDIENT_EXPIRE_DATE)
			.memo(USER_INGREDIENT_MEMO)
			.ingredient(ingredient)
			.build();
	}
}
