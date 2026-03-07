package br.com.clean.wishlist.adapters.input.interceptor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoggingInterceptorTest {

  @Mock private HttpServletRequest request;
  @Mock private HttpServletResponse response;

  private LoggingInterceptor loggingInterceptor;
  private Object handler;

  @BeforeEach
  void setUp() {
    loggingInterceptor = new LoggingInterceptor();
    handler = new Object();
  }

  @Test
  void preHandle_WhenCalled_ShouldReturnTrueAndSetStartTime() throws Exception {
    String method = "GET";
    String uri = "/api/test";
    String clientIp = "192.168.1.1";
    String userAgent = "Mozilla/5.0";

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(request.getHeader("User-Agent")).thenReturn(userAgent);
    when(request.getHeader("X-Forwarded-For")).thenReturn(null);
    when(request.getHeader("X-Real-IP")).thenReturn(null);
    when(request.getRemoteAddr()).thenReturn(clientIp);

    boolean result = loggingInterceptor.preHandle(request, response, handler);

    assertTrue(result);
    verify(request).setAttribute(eq("startTime"), anyLong());
  }

  @Test
  void afterCompletion_WhenNoException_ShouldCompleteSuccessfully() throws Exception {

    String method = "POST";
    String uri = "/api/wishlists";
    int status = 201;
    long startTime = System.currentTimeMillis() - 100;

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(response.getStatus()).thenReturn(status);
    when(request.getAttribute("startTime")).thenReturn(startTime);

    loggingInterceptor.afterCompletion(request, response, handler, null);

    verify(request).getMethod();
    verify(request).getRequestURI();
    verify(response).getStatus();
    verify(request).getAttribute("startTime");
  }

  @Test
  void afterCompletion_WhenException_ShouldCompleteSuccessfully() throws Exception {

    String method = "GET";
    String uri = "/api/test";
    int status = 500;
    long startTime = System.currentTimeMillis() - 200;
    String errorMessage = "Internal server error";
    Exception exception = new RuntimeException(errorMessage);

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(response.getStatus()).thenReturn(status);
    when(request.getAttribute("startTime")).thenReturn(startTime);

    loggingInterceptor.afterCompletion(request, response, handler, exception);

    verify(request).getMethod();
    verify(request).getRequestURI();
    verify(response).getStatus();
    verify(request).getAttribute("startTime");
  }

  @Test
  void getClientIp_WhenXForwardedForPresent_ShouldReturnFirstIp() throws Exception {

    String method = "GET";
    String uri = "/api/test";
    String userAgent = "Mozilla/5.0";
    String xForwardedFor = "192.168.1.1, 10.0.0.1";

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(request.getHeader("User-Agent")).thenReturn(userAgent);
    when(request.getHeader("X-Forwarded-For")).thenReturn(xForwardedFor);

    boolean result = loggingInterceptor.preHandle(request, response, handler);

    assertTrue(result);
    verify(request).getHeader("X-Forwarded-For");
  }

  @Test
  void getClientIp_WhenXRealIpPresent_ShouldUseXRealIp() throws Exception {

    String method = "POST";
    String uri = "/api/wishlists";
    String userAgent = "Chrome/91.0";
    String xRealIp = "10.0.0.1";

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(request.getHeader("User-Agent")).thenReturn(userAgent);
    when(request.getHeader("X-Forwarded-For")).thenReturn(null);
    when(request.getHeader("X-Real-IP")).thenReturn(xRealIp);

    boolean result = loggingInterceptor.preHandle(request, response, handler);

    assertTrue(result);
    verify(request).getHeader("X-Real-IP");
  }

  @Test
  void getClientIp_WhenNoHeadersPresent_ShouldUseRemoteAddress() throws Exception {

    String method = "DELETE";
    String uri = "/api/wishlists/1";
    String userAgent = "Firefox/89.0";
    String remoteAddress = "127.0.0.1";

    when(request.getMethod()).thenReturn(method);
    when(request.getRequestURI()).thenReturn(uri);
    when(request.getHeader("User-Agent")).thenReturn(userAgent);
    when(request.getHeader("X-Forwarded-For")).thenReturn(null);
    when(request.getHeader("X-Real-IP")).thenReturn(null);
    when(request.getRemoteAddr()).thenReturn(remoteAddress);

    boolean result = loggingInterceptor.preHandle(request, response, handler);

    assertTrue(result);
    verify(request).getRemoteAddr();
  }
}
