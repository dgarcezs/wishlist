package br.com.clean.wishlist.application.wishlist.validation;

import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.vo.ProductId;

public class WishlistValidations {

  private WishlistValidations() {
    // Hide constructor
  }

  public static ValidationResult validateUniqueProductId(Wishlist wishlist, String productId) {
    if (wishlist.getProducts().contains(new ProductId(productId))) {
      return WishlistErrors.productIdAlreadyExists();
    }
    return ValidationResult.ofSuccess();
  }

  public static ValidationResult validateMaxProductsPerWishlist(Wishlist wishlist,
      int maxProductsPerWishlist) {
    if (wishlist.getProducts().size() >= maxProductsPerWishlist) {
      return WishlistErrors.maxProductsPerWishlistReached(maxProductsPerWishlist);
    }
    return ValidationResult.ofSuccess();
  }
}
