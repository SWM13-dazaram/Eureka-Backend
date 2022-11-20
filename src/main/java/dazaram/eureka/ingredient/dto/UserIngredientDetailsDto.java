package dazaram.eureka.ingredient.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import dazaram.eureka.ingredient.domain.CustomIngredient;
import dazaram.eureka.ingredient.domain.UserIngredient;
import lombok.AccessLevel;
import lombok.Builder;
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

	@Builder
	public UserIngredientDetailsDto(Long id, String name, LocalDate insertDate, LocalDate expireDate, String memo,
		BasicIngredientDto ingredient) {
		this.id = id;
		this.name = name;
		this.insertDate = insertDate;
		this.expireDate = expireDate;
		this.memo = memo;
		this.ingredient = ingredient;
	}

	public static UserIngredientDetailsDto fromUserIngredient(UserIngredient userIngredient) {
		return UserIngredientDetailsDto.builder()
			.id(userIngredient.getId())
			.ingredient(BasicIngredientDto.fromEntity(userIngredient.getIngredient()))
			.name(userIngredient.getName())
			.memo(userIngredient.getMemo())
			.insertDate(userIngredient.getInsertDate())
			.expireDate(userIngredient.getExpireDate())
			.build();
	}

	public static UserIngredientDetailsDto fromCustomIngredient(CustomIngredient customIngredient) {
		return UserIngredientDetailsDto.builder()
			.id(customIngredient.getId())
			.name(customIngredient.getName())
			.memo(customIngredient.getMemo())
			.insertDate(customIngredient.getInsertDate())
			.expireDate(customIngredient.getExpireDate())
			.ingredient(BasicIngredientDto.builder().icon(customIngredient.getIcon()).build())
			.build();
	}
}
