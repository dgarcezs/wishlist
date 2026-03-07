package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import br.com.clean.wishlist.application.wishlist.config.WishlistConfigurationProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WishlistConfigurationProviderMongoDB implements WishlistConfigurationProvider {

  private static final Logger LOGGER =
      LogManager.getLogger(WishlistConfigurationProviderMongoDB.class);

  private final WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;
  private final int defaultMaxProductPerWishlist;

  public WishlistConfigurationProviderMongoDB(
      WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB,
      @Value("${wishlist.validation.max-products-per-wishlist}") int defaultMaxProductPerWishlist) {
    this.wishlistConfigurationSpringDataMongoDB = wishlistConfigurationSpringDataMongoDB;
    this.defaultMaxProductPerWishlist = defaultMaxProductPerWishlist;
  }

  public int getMaxProductsPerWishlist() {
    return wishlistConfigurationSpringDataMongoDB
        .findByKey("max-products-per-wishlist")
        .map(item -> getMaxProductsPerWishlist(item.getValue()))
        .orElse(defaultMaxProductPerWishlist);
  }

  private int getMaxProductsPerWishlist(String maxProductsPerWishlist) {
    try {
      return Integer.parseInt(maxProductsPerWishlist);
    } catch (NumberFormatException ex) {
      LOGGER.warn(
          "Invalid number format for MaxProductsPerWishList property. Value found: {}",
          maxProductsPerWishlist);
      return defaultMaxProductPerWishlist;
    }
  }
}
