package dazaram.eureka.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.enums.ProviderType;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByProviderTypeAndLoginId(ProviderType providerType, String loginId);
}
