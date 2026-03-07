package br.com.clean.wishlist.domain.validation;

public class WishlistInputValidations {

  private WishlistInputValidations() {}

  public static void validateCustomerId(String customerId) {
    if (customerId == null || customerId.trim().isEmpty()) {
      throw new IllegalArgumentException("Customer ID cannot be null or empty");
    }
  }

  public static void validateProductId(String productId) {
    if (productId == null || productId.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }
  }
}
