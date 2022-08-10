package dazaram.eureka.ingredient.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_category_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "ingredientCategory")
	private List<Ingredient> ingredients;

	@OneToMany(mappedBy = "ingredientCategory")
	private List<CustomIngredient> customIngredients;

	public static IngredientCategory create(String name) {
		return new IngredientCategory(null, name, new ArrayList<>(), new ArrayList<>());
	}

	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

	public void addCustomIngredient(CustomIngredient customIngredient) {
		customIngredients.add(customIngredient);
	}
}
