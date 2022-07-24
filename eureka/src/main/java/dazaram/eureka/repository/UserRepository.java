package dazaram.eureka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
