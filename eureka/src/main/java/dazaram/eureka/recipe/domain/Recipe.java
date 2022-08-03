package dazaram.eureka.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dazaram.eureka.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Recipe extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_category_id")
	private RecipeCategory recipeCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_platform_id")
	private RecipePlatform recipePlatform;

	public Recipe(
		String name,
		String image,
		RecipeCategory recipeCategory,
		RecipePlatform recipePlatform) {
		this.name = name;
		this.image = image;
		this.recipeCategory = recipeCategory;
		this.recipePlatform = recipePlatform;
	}

}
