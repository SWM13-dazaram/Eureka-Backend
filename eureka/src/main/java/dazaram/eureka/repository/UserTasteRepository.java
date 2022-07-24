package dazaram.eureka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.domain.UserTaste;

public interface UserTasteRepository extends JpaRepository<UserTaste, Long> {

}
