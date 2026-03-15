package br.com.clean.wishlist.application.wishlist.service;

import br.com.clean.wishlist.application.wishlist.dto.WishlistResponseDTO;

public interface WishlistService {

  WishlistResponseDTO getWishlist(String customerId);

  void addProduct(String customerId, String productId);

  void removeProduct(String customerId, String productId);

  void removeWishlist(String customerId);
}
