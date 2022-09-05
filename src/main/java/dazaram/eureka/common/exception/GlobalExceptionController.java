package dazaram.eureka.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dazaram.eureka.common.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException ex) {
		return ResponseEntity
			.status(ex.getStatusCode())
			.body(ex.getErrorResponse());
	}
}
