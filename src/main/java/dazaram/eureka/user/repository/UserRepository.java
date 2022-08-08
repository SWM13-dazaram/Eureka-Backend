package dazaram.eureka.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
