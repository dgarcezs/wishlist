package br.com.clean.wishlist.application.wishlist.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ValidationExecutor {

  public List<ValidationResult> validate(List<Supplier<ValidationResult>> steps) {
    List<ValidationResult> resultList = new ArrayList<>();
    for (var step : steps) {
      ValidationResult stepResult = step.get();
      if (!stepResult.isOk()) {
        resultList.add(stepResult);
      }
    }
    return resultList;
  }
}
