package dazaram.eureka.repository;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.Taste.TasteRepository;
import dazaram.eureka.User.UserRepository;
import dazaram.eureka.UserTaste.UserTasteRepository;
import dazaram.eureka.User.Gender;
import dazaram.eureka.Taste.Taste;
import dazaram.eureka.User.User;
import dazaram.eureka.UserTaste.UserTaste;

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
	public void createUserTasteTest(){
		User user = User.builder()
			.name("test")
			.phoneNumber("010-1234-5678")
			.pushAlarmAllow(false)
			.profileImage("src/test_profile.jpg")
			.gender(Gender.M)
			.build();
		userRepository.save(user);

		Taste taste = Taste.create("양식", new ArrayList<>());
		tasteRepository.save(taste);

		UserTaste userTaste = UserTaste.create(user, taste);
		UserTaste newUserTaste = userTasteRepository.save(userTaste);

		Assertions.assertEquals(newUserTaste.getTaste(), taste);
		Assertions.assertEquals(newUserTaste.getUser(), user);
	}
}