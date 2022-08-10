package dazaram.eureka.ingredient.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomIngredientDetailsDto {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
	private LocalDate insertDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Asia/Seoul")
	private LocalDate expireDate;

	private String memo;

	private BasicIngredientDto ingredient;
}
