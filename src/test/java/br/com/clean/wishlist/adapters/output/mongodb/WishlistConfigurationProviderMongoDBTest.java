package br.com.clean.wishlist.adapters.output.mongodb;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationDocument;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationProviderMongoDB;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationSpringDataMongoDB;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishlistConfigurationProviderMongoDBTest {

  @Mock WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;
  int defaultMaxProductsPerWishlist = 6;
  WishlistConfigurationProviderMongoDB wishlistConfigurationProviderMongoDB;

  @BeforeEach
  void setUp() {
    wishlistConfigurationProviderMongoDB =
        new WishlistConfigurationProviderMongoDB(
            wishlistConfigurationSpringDataMongoDB, defaultMaxProductsPerWishlist);
  }

  final String KEY = "max-products-per-wishlist";
  final String VALUE = "10";
  final String INVALID_VALUE = "A";

  @Test
  void getMaxProductsPerWishlist_WhenValidConfig_ShouldReturnMaxProductsPerWishlist() {
    WishlistConfigurationDocument wishlistConfigurationDocument =
        new WishlistConfigurationDocument(KEY, VALUE);

    when(wishlistConfigurationSpringDataMongoDB.findByKey(KEY))
        .thenReturn(Optional.of(wishlistConfigurationDocument));

    int maxProductsPerWishlist = wishlistConfigurationProviderMongoDB.getMaxProductsPerWishlist();

    assertThat(maxProductsPerWishlist).isEqualTo(Integer.parseInt(VALUE));
  }

  @Test
  void
      getMaxProductsPerWishlist_WhenConfigDoesNotExist_ShouldReturnDefaultMaxProductsPerWishlist() {

    when(wishlistConfigurationSpringDataMongoDB.findByKey(KEY)).thenReturn(Optional.empty());

    int maxProductsPerWishlist = wishlistConfigurationProviderMongoDB.getMaxProductsPerWishlist();

    assertThat(maxProductsPerWishlist).isEqualTo(defaultMaxProductsPerWishlist);
  }

  @Test
  void getMaxProductsPerWishlist_WhenInvalidConfig_ShouldReturnDefaultMaxProductsPerWishlist() {
    WishlistConfigurationDocument wishlistConfigurationDocument =
        new WishlistConfigurationDocument(KEY, INVALID_VALUE);

    when(wishlistConfigurationSpringDataMongoDB.findByKey(KEY))
        .thenReturn(Optional.of(wishlistConfigurationDocument));

    int maxProductsPerWishlist = wishlistConfigurationProviderMongoDB.getMaxProductsPerWishlist();

    assertThat(maxProductsPerWishlist).isEqualTo(defaultMaxProductsPerWishlist);
  }
}
