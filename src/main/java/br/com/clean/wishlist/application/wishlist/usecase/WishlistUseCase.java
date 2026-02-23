package br.com.clean.wishlist.application.wishlist.usecase;

import br.com.clean.wishlist.domain.exception.BusinessException;
import br.com.clean.wishlist.domain.model.Wishlist;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import br.com.clean.wishlist.domain.vo.ProductId;

public class WishlistUseCase {

  private final WishlistRepository wishlistRepository;

  public WishlistUseCase(WishlistRepository wishlistRepository) {
    this.wishlistRepository = wishlistRepository;
  }

  public void addProduct(String customerId, String productId) {
    Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
        .orElseGet(() -> new Wishlist(null, customerId));

    if (wishlist.getProducts().contains(new ProductId(productId))) {
      throw new BusinessException("Product already in wishlist");
    }

    wishlist.getProducts().add(new ProductId(productId));
    wishlistRepository.save(wishlist);
  }

}
