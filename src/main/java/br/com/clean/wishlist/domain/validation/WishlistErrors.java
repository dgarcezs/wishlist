package br.com.clean.wishlist.domain.validation;

public class WishlistErrors {

  public static ValidationResult productIdAlreadyExists() {
    return ValidationResult.of(1, "Product is already in your wishlist.", true);
  }

  public static ValidationResult maxProductsPerWishlistReached(int maxProductsPerWishlist) {
    return ValidationResult.of(
        2,
        String.format("Maximum of %d products per wishlist reached.", maxProductsPerWishlist),
        true);
  }
}
