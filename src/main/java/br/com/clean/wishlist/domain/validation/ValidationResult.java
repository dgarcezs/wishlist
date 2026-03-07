package br.com.clean.wishlist.domain.validation;

public record ValidationResult(int errorCode, String formattedMessage, boolean fatal) {

  public static ValidationResult of(int errorCode, String formattedMessage, boolean fatal) {
    return new ValidationResult(errorCode, formattedMessage, fatal);
  }

  public static ValidationResult ofSuccess() {
    return new ValidationResult(0, "OK", false);
  }

  public static ValidationResult ofFatalError(int errorCode, String formattedMessage) {
    return new ValidationResult(errorCode, formattedMessage, true);
  }

  public boolean isOk() {
    return errorCode == 0;
  }

  public boolean isFatal() {
    return fatal;
  }

  public int getErrorCode() {
    return this.errorCode;
  }

  public String getFormattedMessage() {
    return this.formattedMessage;
  }
}
