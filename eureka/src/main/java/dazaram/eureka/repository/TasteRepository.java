package dazaram.eureka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dazaram.eureka.domain.Taste;
import lombok.RequiredArgsConstructor;

public interface TasteRepository extends JpaRepository<Taste, Long> {

}
