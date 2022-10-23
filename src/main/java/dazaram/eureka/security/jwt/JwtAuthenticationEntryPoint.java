package dazaram.eureka.security.jwt;

import static dazaram.eureka.security.jwt.JwtCommonErrorHandler.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import dazaram.eureka.common.error.ErrorCode;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		setResponse(response, ErrorCode.UNAUTHORIZED_USER);
	}
}
