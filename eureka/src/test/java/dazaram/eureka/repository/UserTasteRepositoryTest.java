package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.domain.Gender;
import dazaram.eureka.domain.Taste;
import dazaram.eureka.domain.User;
import dazaram.eureka.domain.UserTaste;

@SpringBootTest
class UserTasteRepositoryTest {

	@Autowired UserRepository userRepository;
	@Autowired TasteRepository tasteRepository;
	@Autowired UserTasteRepository userTasteRepository;

	@Test
	@Transactional
	@DisplayName("유저 취향 추가 테스트")
	public void createUserTasteTest(){
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

		Taste taste = Taste.create("양식", new ArrayList<>());
		tasteRepository.save(taste);

		UserTaste userTaste = UserTaste.create(user, taste);
		UserTaste newUserTaste = userTasteRepository.save(userTaste);

		Assertions.assertEquals(newUserTaste.getTaste(), taste);
		Assertions.assertEquals(newUserTaste.getUser(), user);
	}
}