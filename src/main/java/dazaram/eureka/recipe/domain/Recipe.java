package dazaram.eureka.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import dazaram.eureka.connect.domain.RecipeIngredient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Recipe extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Long id;

	private String name;

	private String image;

	private String size;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_category_id")
	private RecipeCategory recipeCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_platform_id")
	private RecipePlatform recipePlatform;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeSequence> recipeSequences = new ArrayList<>();

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

	public Recipe(
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform
	) {
		this.name = name;
		this.image = image;
		this.recipeCategory = recipeCategory;
		this.recipePlatform = recipePlatform;
	}

	public void setRecipeSequences(List<RecipeSequence> recipeSequences) {
		this.recipeSequences = recipeSequences;
	}
}
