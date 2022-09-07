package dazaram.eureka.security.jwt;

import static dazaram.eureka.security.jwt.JwtCommonErrorHandler.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import dazaram.eureka.common.error.ErrorCode;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		response.sendError(HttpStatus.SC_FORBIDDEN);
		setResponse(response, ErrorCode.INVALID_USER_ACTION);
	}

}
