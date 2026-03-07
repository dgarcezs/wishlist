package br.com.clean.wishlist.application.wishlist.usecase;

import static br.com.clean.wishlist.domain.validation.WishlistValidations.validateMaxProductsPerWishlist;
import static br.com.clean.wishlist.domain.validation.WishlistValidations.validateUniqueProductId;

import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;
import br.com.clean.wishlist.domain.exception.NotFoundException;
import br.com.clean.wishlist.domain.exception.ValidationException;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.validation.ValidationExecutor;
import br.com.clean.wishlist.domain.validation.ValidationResult;
import br.com.clean.wishlist.domain.validation.WishlistInputValidations;
import br.com.clean.wishlist.domain.vo.ProductId;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistUseCase {

  private static final ValidationExecutor wishListValidator = new ValidationExecutor();
  private final WishlistRepository wishlistRepository;
  private final int maxProductsPerWishlist;

  public WishlistUseCase(WishlistRepository wishlistRepository, int maxProductsPerWishlist) {
    this.wishlistRepository = wishlistRepository;
    this.maxProductsPerWishlist = maxProductsPerWishlist;
  }

  public WishlistResponseDTO getWishlist(String customerId) {

    WishlistInputValidations.validateCustomerId(customerId);

    Wishlist wishlist =
        wishlistRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new NotFoundException("Wishlist not found."));

    return new WishlistResponseDTO(
        wishlist.getCustomerId(),
        wishlist.getProducts().stream().map(ProductId::toString).collect(Collectors.toSet()));
  }

  public void addProduct(String customerId, String productId) {

    WishlistInputValidations.validateCustomerId(customerId);
    WishlistInputValidations.validateProductId(productId);

    Wishlist wishlist =
        wishlistRepository
            .findByCustomerId(customerId)
            .orElseGet(() -> new Wishlist(null, customerId, new HashSet<>()));

    List<ValidationResult> validations =
        wishListValidator.validate(
            List.of(
                () -> validateUniqueProductId(wishlist, productId),
                () -> validateMaxProductsPerWishlist(wishlist, maxProductsPerWishlist)));

    if (!validations.isEmpty()) {
      throw new ValidationException(validations);
    }

    wishlist.getProducts().add(new ProductId(productId));
    wishlistRepository.save(wishlist);
  }

  public void removeProduct(String customerId, String productId) {

    WishlistInputValidations.validateCustomerId(customerId);
    WishlistInputValidations.validateProductId(productId);

    Wishlist wishlist =
        wishlistRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new NotFoundException("Wishlist not found."));

    if (!wishlist.getProducts().contains(new ProductId(productId))) {
      throw new NotFoundException("Product not found in wishlist.");
    }

    wishlist.getProducts().remove(new ProductId(productId));

    if (!wishlist.getProducts().isEmpty()) {
      wishlistRepository.save(wishlist);
    } else {
      wishlistRepository.delete(wishlist);
    }
  }

  public void removeWishlist(String customerId) {

    WishlistInputValidations.validateCustomerId(customerId);

    Wishlist wishlist =
        wishlistRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new NotFoundException("Wishlist not found."));

    wishlistRepository.delete(wishlist);
  }
}
