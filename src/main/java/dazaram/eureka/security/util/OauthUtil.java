package dazaram.eureka.security.util;

import java.util.Optional;

import dazaram.eureka.security.dto.LoginTokenDto;
import dazaram.eureka.security.dto.LoginUserInfoDto;
import dazaram.eureka.user.domain.User;

public interface OauthUtil {
	Optional<LoginUserInfoDto> requestUserInfo(LoginTokenDto token);

	User createEntity(LoginUserInfoDto loginUserInfoDto);
}
