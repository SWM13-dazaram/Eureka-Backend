package dazaram.eureka.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.oauth.Oauth;
import dazaram.eureka.oauth.OauthRepository;
import dazaram.eureka.user.Gender;
import dazaram.eureka.user.User;
import dazaram.eureka.user.UserRepository;

@SpringBootTest
class OauthRepositoryTest {

	@Autowired
	OauthRepository oauthRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("Oauth 추가 테스트")
	public void createOauthTest() {

		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		userRepository.save(user);
		Oauth oauth = Oauth.builder()
			.provider("admin")
			.accessToken("abcd0123")
			.user(user)
			.build();
		oauthRepository.save(oauth);

		Assertions.assertEquals(user.getOauths().get(0), oauth);
	}
}
