package dazaram.eureka.security.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import dazaram.eureka.common.error.ErrorCode;

public class JwtCommonErrorHandler {
	public static void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(errorCode.getStatusCode());

		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("statusCode", errorCode.getStatusCode());
		responseJson.addProperty("code", errorCode.getCode());
		responseJson.addProperty("message", errorCode.getMessage());

		response.getWriter().print(responseJson);
	}
}
