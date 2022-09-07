package dazaram.eureka.common.error;

import org.apache.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	// user
	UNAUTHORIZED_USER(HttpStatus.SC_UNAUTHORIZED, "USER-401", "등록된 사용자가 아닙니다"),
	INVALID_USER_ACTION(HttpStatus.SC_FORBIDDEN, "USER-403", "권한이 없는 사용자입니다"),
	INACTIVATED_USER(HttpStatus.SC_FORBIDDEN, "USER-INACTIVATED-403", "비활성화된 사용자입니다"),
	USER_DUPLICATION(HttpStatus.SC_CONFLICT, "USER-409", "이미 존재하는 사용자입니다"),
	USER_NAME_DUPLICATION(HttpStatus.SC_CONFLICT, "USER-NAME-409", "존재하는 이름 입니다"),
	USER_EMAIL_DUPLICATION(HttpStatus.SC_CONFLICT, "USER-EMAIL-409", "존재하는 이메일 입니다"),
	// recipe
	RECIPE_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "RECIPE-404", "존재하지 않는 레시피 입니다"),
	RECIPE_USERINGREDIENT_NO_CONTENT(HttpStatus.SC_NOT_FOUND, "RECIPE-USERINGREDIENT-404",
		"보유한 식재료로 만들 수 있는 레시피가 없습니다"),
	// ingredient
	INGREDIENT_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "INGREDIENT-404", "존재하지 않는 재료 입니다"),
	// userIngredient
	USERINGREDIENT_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "USERINGREDIENT-404", "유저가 저장한 재료가 없습니다"),
	// elastic
	ELASTIC_RESULT_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "ELASTIC-RESULT-404", "검색결과가 없습니다"),
	// provider
	PROVIDER_NOT_IMPLEMENTED(HttpStatus.SC_NOT_IMPLEMENTED, "PROVIDER-501", "Provider에게서 정보를 받아올 수 없습니다"),
	PROVIDER_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "PROVIDER-404", "존재하지 않는 Provider 입니다");

	private final int statusCode;
	private final String code;
	private final String message;

	public ErrorResponse toErrorResponse() {
		return ErrorResponse.builder()
			.statusCode(statusCode)
			.code(code)
			.message(message)
			.build();
	}

}
