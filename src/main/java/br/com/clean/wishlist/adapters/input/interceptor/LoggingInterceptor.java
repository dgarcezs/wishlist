package br.com.clean.wishlist.adapters.input.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

  public static final Logger LOGGER = LogManager.getLogger(LoggingInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String method = request.getMethod();
    String uri = request.getRequestURI();
    String clientIp = getClientIp(request);
    String userAgent = request.getHeader("User-Agent");

    LOGGER.info("Request: {} {} | Client: {} | User-Agent: {}", method, uri, clientIp, userAgent);

    request.setAttribute("startTime", System.currentTimeMillis());

    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {

    long startTime = (Long) request.getAttribute("startTime");
    long duration = System.currentTimeMillis() - startTime;

    String method = request.getMethod();
    String uri = request.getRequestURI();
    int status = response.getStatus();

    if (ex != null) {
      LOGGER.error(
          "Request completed with error: {} {} | Status: {} | Duration: {}ms | Error: {}",
          method,
          uri,
          status,
          duration,
          ex.getMessage());
    } else {
      LOGGER.info(
          "Request completed: {} {} | Status: {} | Duration: {}ms", method, uri, status, duration);
    }
  }

  private String getClientIp(HttpServletRequest request) {

    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }

    String xRealIp = request.getHeader("X-Real-IP");
    if (xRealIp != null && !xRealIp.isEmpty()) {
      return xRealIp;
    }

    return request.getRemoteAddr();
  }
}
