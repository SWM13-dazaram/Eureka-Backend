package dazaram.eureka.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.domain.Taste;

@SpringBootTest
class TasteRepositoryTest {

	@Autowired
	TasteRepository tasteRepository;

	@Test
	@Transactional
	@DisplayName("기호 추가 테스트")
	public void createTasteTest() {
		Taste taste = Taste.create("양식", new ArrayList<>());
		Taste saveTaste = tasteRepository.save(taste);

		Optional<Taste> findTaste = tasteRepository.findById(saveTaste.getId());

		Assertions.assertEquals(taste, findTaste.get());
		Assertions.assertEquals(saveTaste, findTaste.get());
	}
}