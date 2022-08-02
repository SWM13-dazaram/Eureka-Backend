package dazaram.eureka.ingredient;

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

	public static Category create(String name){
		return new Category(null, name, new ArrayList<>());
	}
}
