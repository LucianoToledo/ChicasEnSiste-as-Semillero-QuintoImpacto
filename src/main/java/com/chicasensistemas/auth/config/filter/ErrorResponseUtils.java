package com.chicasensistemas.auth.config.filter;

import com.chicasensistemas.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;

public class ErrorResponseUtils {

  private ErrorResponseUtils(){}

  public static void setCustomResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(getGenericErrorResponse()));
  }

  private static ErrorResponse getGenericErrorResponse() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
    errorResponse.setTimestamp(Timestamp.from(Instant.now()));
    errorResponse.add("Access denied. Please, try to login again or contact your admin.");
    return errorResponse;
  }
}
