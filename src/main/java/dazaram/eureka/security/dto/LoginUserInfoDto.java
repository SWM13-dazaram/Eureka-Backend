package dazaram.eureka.security.dto;

import dazaram.eureka.user.enums.ProviderType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUserInfoDto {
	private String loginId;
	private ProviderType providerType;
	private String nickName;
	private String email;
	private String profileImage;

	@Builder
	public LoginUserInfoDto(String loginId, ProviderType providerType, String nickName, String email,
		String profileImage) {
		this.loginId = loginId;
		this.providerType = providerType;
		this.nickName = nickName;
		this.email = email;
		this.profileImage = profileImage;
	}
}
