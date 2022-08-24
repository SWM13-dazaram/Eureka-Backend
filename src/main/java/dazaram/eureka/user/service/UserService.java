package dazaram.eureka.user.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.security.dto.LoginResponse;
import dazaram.eureka.security.dto.LoginTokenDto;
import dazaram.eureka.security.dto.LoginUserInfoDto;
import dazaram.eureka.security.jwt.TokenProvider;
import dazaram.eureka.security.util.KakaoLoginUtil;
import dazaram.eureka.security.util.OauthUtil;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.ProviderType;
import dazaram.eureka.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final KakaoLoginUtil kakaoLoginUtil;
	private OauthUtil oauthUtil;

	@Transactional
	public LoginResponse loginOrSignUp(LoginTokenDto loginTokenDto) {
		Boolean isSignUp = false;
		oauthUtil = setLoginUtil(loginTokenDto.getProviderType());

		LoginUserInfoDto loginUserInfoDto = oauthUtil.requestUserInfo(loginTokenDto)
			.orElseThrow(() -> new NoSuchElementException("Provider에게서 정보를 받아올 수 없습니다"));

		Optional<User> byProviderTypeAndLoginId = userRepository.findByProviderTypeAndLoginId(
			loginUserInfoDto.getProviderType(), loginUserInfoDto.getLoginId());
		// 정보 없으면 회원가입하기
		if (byProviderTypeAndLoginId.isEmpty()) {
			isSignUp = true;
		}
		User user = byProviderTypeAndLoginId
			.orElseGet(() -> signUp(oauthUtil.createEntity(loginUserInfoDto)));

		return makeJwtResponse(user.getId().toString(), isSignUp);
	}

	private User signUp(User user) {
		return userRepository.save(user);
	}

	private OauthUtil setLoginUtil(ProviderType providerType) {
		// 카카오
		if (providerType.equals(ProviderType.KAKAO)) {
			return kakaoLoginUtil;
		} else if (providerType.equals(ProviderType.APPLE)) {
			return null;
		}
		throw new NoSuchElementException("주어진 Provider 정보가 없습니다");
	}

	private LoginResponse makeJwtResponse(String userId, Boolean isSignup) {
		int statusCode = Response.SC_OK;
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			userId, "password");

		// loadUserByUsername실행됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (isSignup) {
			statusCode = Response.SC_CREATED;
		}

		return new LoginResponse(tokenProvider.createToken(authentication), statusCode);
	}

}
