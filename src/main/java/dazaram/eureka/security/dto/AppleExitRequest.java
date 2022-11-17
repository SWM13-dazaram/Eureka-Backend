package dazaram.eureka.security.dto;

import dazaram.eureka.user.enums.ProviderType;
import lombok.Getter;

@Getter
public class AppleExitRequest {
	private ProviderType providerType;
	private String accessToken;
}
