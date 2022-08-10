package dazaram.eureka.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.connect.domain.AiRecipeLog;
import dazaram.eureka.user.domain.User;

public interface AiRecipeLogRepository extends JpaRepository<AiRecipeLog, Long> {
	List<AiRecipeLog> findAllByUser(User user);
}
