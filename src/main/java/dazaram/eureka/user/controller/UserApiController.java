package dazaram.eureka.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {
	private final UserService userService;

	@DeleteMapping()
	public ResponseEntity<String> exitUser() {
		return ResponseEntity.ok(userService.deleteUser());
	}
}
