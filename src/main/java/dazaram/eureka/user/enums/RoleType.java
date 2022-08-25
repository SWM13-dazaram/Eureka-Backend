package dazaram.eureka.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN"),
	GUEST("ROLE_GUEST");

	private final String code;
}
