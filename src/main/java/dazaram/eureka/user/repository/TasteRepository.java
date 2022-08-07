package dazaram.eureka.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.user.domain.Taste;

public interface TasteRepository extends JpaRepository<Taste, Long> {

}
