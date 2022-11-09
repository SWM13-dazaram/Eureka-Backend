package dazaram.eureka.security.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dazaram.eureka.security.config.FeignClientConfig;
import dazaram.eureka.security.dto.ApplePublicKeyResponse;
import dazaram.eureka.security.dto.AppleToken;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth", configuration = FeignClientConfig.class)
public interface AppleClient {
	@GetMapping(value = "/keys")
	ApplePublicKeyResponse getAppleAuthPublicKey();

	@PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
	AppleToken.Response getToken(AppleToken.Request request);

	@PostMapping(value = "/revoke", consumes = "application/x-www-form-urlencoded")
	void revoke(AppleToken.RevokeRequest request);
}
