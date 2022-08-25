package dazaram.eureka.security.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
	@NotNull
	private String jwt;
	private int statusCode;
}
