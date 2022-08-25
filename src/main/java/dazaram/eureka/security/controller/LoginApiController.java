package dazaram.eureka.security.controller;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dazaram.eureka.security.dto.LoginResponse;
import dazaram.eureka.security.dto.LoginTokenDto;
import dazaram.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginApiController {

	private final UserService userService;

	@PostMapping("")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginTokenDto loginTokenDto) {
		LoginResponse loginResponse = userService.loginOrSignUp(loginTokenDto);
		if (loginResponse.getStatusCode() == Response.SC_CREATED) {
			return ResponseEntity.status(Response.SC_CREATED)
				.body(loginResponse);
		}
		return ResponseEntity.ok(loginResponse);
	}
}
