package dazaram.eureka.common.exception;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dazaram.eureka.common.error.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

	private final String INTERNAL_ERROR_CODE = "SERVER-500";

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		return ResponseEntity
			.status(ex.getStatusCode())
			.body(ex.getErrorResponse());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		return ResponseEntity
			.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
			.body(makeInternalErrorResponse(ex));
	}

	private ErrorResponse makeInternalErrorResponse(Exception ex) {
		return ErrorResponse.builder()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
			.code(INTERNAL_ERROR_CODE)
			.message(ex.getMessage())
			.build();
	}
}
