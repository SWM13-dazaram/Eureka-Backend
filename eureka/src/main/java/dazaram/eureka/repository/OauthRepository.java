package dazaram.eureka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.domain.Oauth;

public interface OauthRepository extends JpaRepository<Oauth, Long> {
}
