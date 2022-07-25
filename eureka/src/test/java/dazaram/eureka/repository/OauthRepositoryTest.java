package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.domain.Gender;
import dazaram.eureka.domain.Oauth;
import dazaram.eureka.domain.User;

@SpringBootTest
class OauthRepositoryTest {

	@Autowired OauthRepository oauthRepository;
	@Autowired UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("Oauth 추가 테스트")
	public void createOauthTest(){
		User user = User.create(
			"test",
			"010-1234-5678",
			false,
			"src/test_profile.jpg",
			Gender.M,
			new ArrayList<>(),
			new ArrayList<>(),
			new ArrayList<>(),
			new ArrayList<>()
		);
		userRepository.save(user);
		Oauth oauth = Oauth.create("admin", "abcd0123", user);
		oauthRepository.save(oauth);

		Assertions.assertEquals(user.getOauths().get(0), oauth);
	}
}