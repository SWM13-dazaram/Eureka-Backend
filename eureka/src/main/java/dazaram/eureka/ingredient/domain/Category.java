package dazaram.eureka.ingredient.domain;

import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "category")
	private List<Ingredient> ingredients;

	@OneToMany(mappedBy = "category")
	private List<CustomIngredient> customIngredients;

	public static Category create(String name) {
		return new Category(null, name, new ArrayList<>(), new ArrayList<>());
	}

	public void addIngredient(Ingredient ingredient){
		ingredients.add(ingredient);
	}

	public void addCustomIngredient(CustomIngredient customIngredient){
		customIngredients.add(customIngredient);
	}
}
