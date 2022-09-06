package dazaram.eureka.security.service;

import static dazaram.eureka.common.error.ErrorCode.*;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dazaram.eureka.common.exception.CustomException;
import dazaram.eureka.user.domain.User;
import dazaram.eureka.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// authenticate가 실행될 때 같이 실행됨
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userId) {
		User user = userRepository.findById(Long.valueOf(userId))
			.orElseThrow(() -> new CustomException(UNAUTHORIZED_USER));
		return createSecurityUser(userId, user);
	}

	private org.springframework.security.core.userdetails.User createSecurityUser(String userId, User user) {
		if (!user.isActivate()) {
			throw new CustomException(INACTIVATED_USER);
		}
		return new org.springframework.security.core.userdetails.User(
			userId, passwordEncoder.encode("password"),
			Collections.singleton(new SimpleGrantedAuthority(user.getRoleType().getCode())));
	}

}
