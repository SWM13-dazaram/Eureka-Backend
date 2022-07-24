package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.domain.Gender;
import dazaram.eureka.domain.User;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@Transactional
	@DisplayName("유저 추가 테스트")
	public void createTest() {
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
		User newUser = userRepository.save(user);

		Optional<User> findUser = userRepository.findById(newUser.getId());

		Assertions.assertFalse(newUser.equals(findUser));
	}
}