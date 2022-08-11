package dazaram.eureka.ingredient.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
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

	@Enumerated(EnumType.STRING)
	private IngredientCategory ingredientCategory;

	private String icon;

	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<UserIngredient> userIngredients;

	@Builder
	public Ingredient(Long id, String name, Long expirePeriod, IngredientCategory ingredientCategory, String icon) {
		this.id = id;
		this.name = name;
		this.expirePeriod = expirePeriod;
		this.ingredientCategory = ingredientCategory;
		this.icon = icon;
		this.userIngredients = new ArrayList<>();
	}
}
