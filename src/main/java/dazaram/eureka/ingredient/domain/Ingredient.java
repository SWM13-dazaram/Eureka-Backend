package dazaram.eureka.ingredient.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.connect.domain.RecipeIngredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Long id;

	private String name;

	private Long expirePeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_category_id")
	private IngredientCategory ingredientCategory;
	private String icon;

	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<UserIngredient> userIngredients = new ArrayList<>();

	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

	@Builder
	public Ingredient(Long id, String name, Long expirePeriod, IngredientCategory ingredientCategory, String icon) {
		this.id = id;
		this.name = name;
		this.expirePeriod = expirePeriod;
		this.ingredientCategory = ingredientCategory;
		this.icon = icon;

		if (ingredientCategory != null) {
			ingredientCategory.addIngredient(this);
		}
	}
}
