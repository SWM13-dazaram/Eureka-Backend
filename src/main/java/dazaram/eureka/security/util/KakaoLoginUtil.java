package dazaram.eureka.security.util;

import static dazaram.eureka.common.error.ErrorCode.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dazaram.eureka.common.exception.CustomException;
import dazaram.eureka.security.dto.LoginTokenDto;
import dazaram.eureka.security.dto.LoginUserInfoDto;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.ProviderType;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KakaoLoginUtil implements OauthUtil {
	@Override
	public User createEntity(LoginUserInfoDto loginUserInfoDto) {
		return User.fromKaKaoDto(loginUserInfoDto);
	}

	@Override
	public Optional<LoginUserInfoDto> requestUserInfo(LoginTokenDto loginTokenDto) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		Optional<LoginUserInfoDto> loginUserInfoDto;
		try {
			HttpURLConnection conn = getHttpURLConnection(loginTokenDto, reqURL);

			if (conn.getResponseCode() != 200) {
				throw new CustomException(PROVIDER_NOT_IMPLEMENTED);
			}

			String result = getResult(conn.getInputStream());
			loginUserInfoDto = parseUserInfo(result, loginTokenDto.getProviderType());

		} catch (IOException e) {
			throw new CustomException(PROVIDER_NOT_IMPLEMENTED);
		}
		return loginUserInfoDto;
	}

	private HttpURLConnection getHttpURLConnection(LoginTokenDto loginTokenDto, String reqURL) throws IOException {
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer " + loginTokenDto.getAccessToken());
		return conn;
	}

	private String getResult(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		String line = "";
		String result = "";

		while ((line = br.readLine()) != null) {
			result += line;
		}
		br.close();
		return result;
	}

	private Optional<LoginUserInfoDto> parseUserInfo(String result, ProviderType providerType) {
		JsonObject element = JsonParser.parseString(result).getAsJsonObject();

		JsonObject properties = element.get("properties").getAsJsonObject();
		JsonObject kakao_account = element.get("kakao_account").getAsJsonObject();

		String nickname = properties.get("nickname").getAsString();
		String profileImage = properties.get("profile_image").getAsString();
		String loginId = element.get("id").getAsString();
		String email = kakao_account.get("email").getAsString();

		return Optional.of(LoginUserInfoDto.builder()
			.loginId(loginId)
			.providerType(providerType)
			.nickName(nickname)
			.profileImage(profileImage)
			.email(email)
			.build());
	}

}
