package dazaram.eureka.user.repository;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.user.domain.Gender;
import dazaram.eureka.user.domain.Taste;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.domain.UserTaste;

@SpringBootTest
class UserTasteRepositoryTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	TasteRepository tasteRepository;
	@Autowired
	UserTasteRepository userTasteRepository;

	@Test
	@Transactional
	@DisplayName("유저 취향 추가 테스트")
	public void createUserTasteTest() {
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		User saveUser = userRepository.save(user);

		Taste taste = Taste.create("양식", new ArrayList<>());
		Taste saveTaste = tasteRepository.save(taste);

		UserTaste userTaste = UserTaste.create(saveUser, saveTaste);
		UserTaste newUserTaste = userTasteRepository.save(userTaste);

		Assertions.assertEquals(newUserTaste.getTaste(), taste);
		Assertions.assertEquals(newUserTaste.getUser(), user);
	}
}
