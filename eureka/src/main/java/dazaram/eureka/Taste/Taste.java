package dazaram.eureka.Taste;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dazaram.eureka.UserTaste.UserTaste;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Taste {

	@Id
	@GeneratedValue
	@Column(name = "taste_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "taste")
	private List<UserTaste> userTastes;

	public static Taste create(String name, List<UserTaste> userTastes){
		return new Taste(null, name, userTastes);
	}

	public void addUserTaste(UserTaste userTaste){
		userTastes.add(userTaste);
	}
}