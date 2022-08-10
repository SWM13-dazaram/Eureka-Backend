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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplaceIngredient extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "replace_ingredient_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replace_id")
	private Ingredient replaceIngredient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "missing_id")
	private Ingredient missingIngredient;

	private float similarity;

	@Builder
	public ReplaceIngredient(Ingredient replaceIngredient, Ingredient missingIngredient,
		float similarity) {
		this.replaceIngredient = replaceIngredient;
		this.missingIngredient = missingIngredient;
		this.similarity = similarity;
	}
}
