package dazaram.eureka.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityUtil {
	public static Long getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
		}

		Long userId = null;

		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails)authentication.getPrincipal();
			userId = Long.valueOf(springSecurityUser.getUsername());
		} else if (authentication.getPrincipal() instanceof String) {
			userId = (Long)authentication.getPrincipal();
		} else if (authentication.getPrincipal() instanceof Long) {
			userId = (Long)authentication.getPrincipal();
		}

		if (userId == null) {
			throw new RuntimeException("userId가 존재하지 않습니다");
		}

		return userId;
	}
}
