package dazaram.eureka.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dazaram.eureka.user.domain.Oauth;

public interface OauthRepository extends JpaRepository<Oauth, Long> {
}
