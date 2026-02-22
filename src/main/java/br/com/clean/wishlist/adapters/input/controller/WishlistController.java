package br.com.clean.wishlist.adapters.input.controller;

import br.com.clean.wishlist.application.wishlist.usecase.WishlistUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

  private final WishlistUseCase wishlistUseCase;

  public WishlistController(WishlistUseCase wishlistUseCase) {
    this.wishlistUseCase = wishlistUseCase;
  }

  @PostMapping("/{customerId}/products/{productId}")
  public ResponseEntity<Void> addProductToWishlist(@PathVariable String customerId,
      @PathVariable String productId) {
    wishlistUseCase.addProduct(customerId, productId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
