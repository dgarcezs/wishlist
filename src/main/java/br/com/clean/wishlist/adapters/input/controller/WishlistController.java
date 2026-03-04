package br.com.clean.wishlist.adapters.input.controller;

import br.com.clean.wishlist.adapters.input.dto.RestResponseDTO;
import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.application.wishlist.usecase.WishlistUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/{customerId}")
  public ResponseEntity<RestResponseDTO<WishlistResponseDTO>> getWishlist(
      @PathVariable String customerId) {

    WishlistResponseDTO wishlistResponseDTO = wishlistUseCase.getWishlist(customerId);

    RestResponseDTO<WishlistResponseDTO> restResponseDTO =
        new RestResponseDTO<>(wishlistResponseDTO, "Success", HttpStatus.OK.value());

    return ResponseEntity.status(HttpStatus.OK).body(restResponseDTO);
  }

  @PostMapping("/{customerId}/products/{productId}")
  public ResponseEntity<Void> addProductToWishlist(
      @PathVariable String customerId, @PathVariable String productId) {
    wishlistUseCase.addProduct(customerId, productId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{customerId}/products/{productId}")
  public ResponseEntity<Void> removeProductFromWishlist(
      @PathVariable String customerId, @PathVariable String productId) {
    wishlistUseCase.removeProduct(customerId, productId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> removeWishlist(@PathVariable String customerId) {
    wishlistUseCase.removeWishlist(customerId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
