package dazaram.eureka.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.User.UserRepository;
import dazaram.eureka.User.Gender;
import dazaram.eureka.User.User;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("유저 추가 테스트")
	public void createUserTest() {
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		User newUser = userRepository.save(user);

		Optional<User> findUser = userRepository.findById(newUser.getId());

		Assertions.assertEquals(user, findUser.get());
		Assertions.assertEquals(newUser, findUser.get());
	}
}