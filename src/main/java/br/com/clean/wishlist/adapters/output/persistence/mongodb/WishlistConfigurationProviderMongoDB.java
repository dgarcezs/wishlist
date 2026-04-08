package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import br.com.clean.wishlist.infrastructure.config.WishlistConfigurationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "wishlist.repository.type", havingValue = "mongodb")
public class WishlistConfigurationProviderMongoDB implements WishlistConfigurationProvider {

  private final WishlistConfigurationRepositoryMongoDB wishlistConfigurationMongoDB;
  private final int defaultMaxProductPerWishlist;

  public WishlistConfigurationProviderMongoDB(
      WishlistConfigurationRepositoryMongoDB wishlistConfigurationMongoDB,
      @Value("${wishlist.validation.max-products-per-wishlist}") int defaultMaxProductPerWishlist) {
    this.wishlistConfigurationMongoDB = wishlistConfigurationMongoDB;
    this.defaultMaxProductPerWishlist = defaultMaxProductPerWishlist;
  }

  @Override
  public int getMaxProductsPerWishlist() {
    String maxProductsPerWishlist =
        wishlistConfigurationMongoDB
            .findValueByKey("max-products-per-wishlist")
            .orElse(String.valueOf(defaultMaxProductPerWishlist));
    try {
      return Integer.parseInt(maxProductsPerWishlist);
    } catch (NumberFormatException e) {
      return defaultMaxProductPerWishlist;
    }
  }
}
