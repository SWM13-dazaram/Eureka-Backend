package dazaram.eureka.connect.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.ingredient.domain.Ingredient;
import dazaram.eureka.recipe.domain.Recipe;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngredient extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_ingredient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	private String quantity;

	private RecipeIngredient(String quantity) {
		this.quantity = quantity;
	}

	public static RecipeIngredient create(Recipe recipe, Ingredient ingredient, String quantity) {
		RecipeIngredient recipeIngredient = new RecipeIngredient(quantity);
		recipeIngredient.setRecipe(recipe);
		recipeIngredient.setIngredient(ingredient);
		return recipeIngredient;
	}

	private void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		ingredient.getRecipeIngredients().add(this);
	}

	private void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		recipe.getRecipeIngredients().add(this);
	}
}
