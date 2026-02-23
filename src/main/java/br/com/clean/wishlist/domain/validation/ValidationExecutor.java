package br.com.clean.wishlist.domain.validation;

import java.util.Arrays;
import java.util.List;

public class ValidationExecutor {

  public <T> void executeValidations(T input, List<ValidationHandler<T>> validators) {
    validators.forEach(validator -> validator.validate(input));
  }

  public <T> void executeValidations(T input, ValidationHandler<T>... validators) {
    Arrays.stream(validators).forEach(validator -> validator.validate(input));
  }
}
