package br.com.clean.wishlist.application.wishlist.config;

public interface WishlistConfigurationProvider {

  int getMaxProductsPerWishlist();

  void setMaxProductsPerWishlist(int maxProductsPerWishlist);
}
