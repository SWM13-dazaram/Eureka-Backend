package dazaram.eureka.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeCategory extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "recipe_category_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "recipeCategory", cascade = CascadeType.ALL)
	private List<Recipe> recipes = new ArrayList<>();

}
