package dazaram.eureka.user.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.security.dto.AppleExitRequest;
import dazaram.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {
	private final UserService userService;

	@DeleteMapping()
	public ResponseEntity<String> exitUser(@RequestBody AppleExitRequest request) throws IOException {
		return ResponseEntity.ok(userService.deleteUser(request));
	}
}
