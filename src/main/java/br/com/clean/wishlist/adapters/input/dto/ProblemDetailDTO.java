package br.com.clean.wishlist.adapters.input.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public record ProblemDetailDTO(
    String type,
    String title,
    int status,
    String detail,
    String instance,
    Instant timestamp,
    Map<String, Object> errors) {

  public static ProblemDetailDTO of(int status, String title, String detail, String instance) {
    return new ProblemDetailDTO(
        "about:blank", title, status, detail, instance, Instant.now(), null);
  }

  public static ProblemDetailDTO withErrors(
      int status, String title, String detail, String instance, Map<String, Object> errors) {
    return new ProblemDetailDTO(
        "about:blank", title, status, detail, instance, Instant.now(), errors);
  }
}
