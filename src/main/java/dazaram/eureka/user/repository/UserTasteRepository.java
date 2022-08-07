package dazaram.eureka.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.user.domain.UserTaste;

public interface UserTasteRepository extends JpaRepository<UserTaste, Long> {

}
