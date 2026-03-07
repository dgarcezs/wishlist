package br.com.clean.wishlist.domain.exception;

import br.com.clean.wishlist.domain.validation.ValidationResult;
import java.util.List;

public class ValidationException extends RuntimeException {

  private final List<ValidationResult> validationResults;

  public ValidationException(List<ValidationResult> validationResults) {
    super("Validation failed");
    this.validationResults = validationResults;
  }

  public List<ValidationResult> getValidationResults() {
    return this.validationResults;
  }
}
