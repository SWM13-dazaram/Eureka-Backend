package dazaram.eureka.ingredient.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import dazaram.eureka.ingredient.domain.UserIngredient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIngredientDetailsDto {
	private Long id;
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
	private LocalDate insertDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
	private LocalDate expireDate;

	private String memo;
	private BasicIngredientDto ingredient;

	public UserIngredientDetailsDto(UserIngredient userIngredient){
		id = userIngredient.getId();
		name = userIngredient.getName();
		insertDate = userIngredient.getInsertDate();
		expireDate = userIngredient.getExpireDate();
		memo = userIngredient.getMemo();
		ingredient = new BasicIngredientDto(userIngredient.getIngredient());
	}
}
