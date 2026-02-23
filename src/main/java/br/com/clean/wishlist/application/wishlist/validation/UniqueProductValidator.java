package br.com.clean.wishlist.application.wishlist.validation;

import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.validation.ValidationHandler;

public class UniqueProductValidator implements ValidationHandler<String> {

  private final WishlistRepository wishlistRepository;

  public UniqueProductValidator(WishlistRepository wishlistRepository) {
    this.wishlistRepository = wishlistRepository;
  }

  @Override
  public void validate(String input) {

  }
}
