package br.com.clean.wishlist.adapters.input.handler;

import br.com.clean.wishlist.adapters.input.dto.ProblemDetailDTO;
import br.com.clean.wishlist.adapters.input.dto.ValidationErrorDetailDTO;
import br.com.clean.wishlist.application.wishlist.exception.NotFoundException;
import br.com.clean.wishlist.application.wishlist.exception.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ProblemDetailDTO> handleValidationException(
      ValidationException ex, WebRequest request) {

    List<ValidationErrorDetailDTO> validationErrors =
        ex.getValidationResults().stream()
            .map(
                result ->
                    new ValidationErrorDetailDTO(
                        String.valueOf(result.getErrorCode()), result.getFormattedMessage()))
            .collect(Collectors.toList());

    Map<String, Object> errors = new HashMap<>();
    errors.put("validation_errors", validationErrors);

    ProblemDetailDTO problemDetailDTO =
        ProblemDetailDTO.withErrors(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            "One or more validation errors occurred",
            getInstancePath(request),
            errors);
    LOGGER.warn(problemDetailDTO);

    return ResponseEntity.badRequest().body(problemDetailDTO);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ProblemDetailDTO> handleNotFoundException(
      NotFoundException ex, WebRequest request) {
    ProblemDetailDTO problemDetailDTO =
        ProblemDetailDTO.of(
            HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), getInstancePath(request));
    LOGGER.warn(problemDetailDTO);

    return ResponseEntity.badRequest().body(problemDetailDTO);
  }

  private String getInstancePath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "");
  }
}
