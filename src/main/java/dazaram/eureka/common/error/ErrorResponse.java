package dazaram.eureka.common.error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private final int statusCode;
	private final String code;
	private final String message;

	@Builder
	public ErrorResponse(int statusCode, String code, String message) {
		this.statusCode = statusCode;
		this.code = code;
		this.message = message;
	}

}
