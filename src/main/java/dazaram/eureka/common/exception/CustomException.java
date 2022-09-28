package dazaram.eureka.common.exception;

import dazaram.eureka.common.error.ErrorCode;
import dazaram.eureka.common.error.ErrorResponse;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public int getStatusCode() {
		return errorCode.getStatusCode();
	}

	public ErrorResponse getErrorResponse() {
		return errorCode.toErrorResponse();
	}
}
