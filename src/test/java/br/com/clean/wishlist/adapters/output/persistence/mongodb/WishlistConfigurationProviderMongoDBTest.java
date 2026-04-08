package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishlistConfigurationProviderMongoDBTest {

  @Mock WishlistConfigurationRepositoryMongoDB wishlistConfigurationRepositoryMongoDB;

  final int MAX_PRODUCTS_PER_WISHLIST = 10;
  final int DEFAULT_MAX_PRODUCTS_PER_WISHLIST = 15;

  @Test
  void WhenGetMaxProductsPerWishlist_WhenValidConfig_ShouldReturnValue() {
    WishlistConfigurationProviderMongoDB provider =
        new WishlistConfigurationProviderMongoDB(
            wishlistConfigurationRepositoryMongoDB, DEFAULT_MAX_PRODUCTS_PER_WISHLIST);

    when(wishlistConfigurationRepositoryMongoDB.findValueByKey("max-products-per-wishlist"))
        .thenReturn(Optional.of("10"));

    int maxProductsPerWishlist = provider.getMaxProductsPerWishlist();

    assertThat(maxProductsPerWishlist).isEqualTo(MAX_PRODUCTS_PER_WISHLIST);
  }

  @Test
  void WhenGetMaxProductsPerWishlist_WhenInvalidConfig_ShouldReturnDefaultMaxProductsPerWishlist() {
    WishlistConfigurationProviderMongoDB provider =
        new WishlistConfigurationProviderMongoDB(
            wishlistConfigurationRepositoryMongoDB, DEFAULT_MAX_PRODUCTS_PER_WISHLIST);

    when(wishlistConfigurationRepositoryMongoDB.findValueByKey("max-products-per-wishlist"))
        .thenReturn(Optional.of("A"));

    int maxProductsPerWishlist = provider.getMaxProductsPerWishlist();

    assertThat(maxProductsPerWishlist).isEqualTo(DEFAULT_MAX_PRODUCTS_PER_WISHLIST);
  }
}
