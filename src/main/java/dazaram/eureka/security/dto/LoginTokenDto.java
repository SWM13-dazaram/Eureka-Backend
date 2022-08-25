package dazaram.eureka.security.dto;

import javax.validation.constraints.NotNull;

import dazaram.eureka.user.enums.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginTokenDto {
	@NotNull(message = "no")
	private String accessToken;
	@NotNull
	private ProviderType providerType;
}
