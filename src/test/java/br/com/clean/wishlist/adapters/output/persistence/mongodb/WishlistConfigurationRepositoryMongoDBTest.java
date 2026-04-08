package br.com.clean.wishlist.adapters.output.persistence.mongodb;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WishlistConfigurationRepositoryMongoDBTest {

  @Mock WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB;
  WishlistConfigurationRepositoryMongoDB wishlistConfigurationMongoDB;

  @BeforeEach
  void setUp() {
    wishlistConfigurationMongoDB =
        new WishlistConfigurationRepositoryMongoDB(wishlistConfigurationSpringDataMongoDB);
  }

  final String KEY = "max-products-per-wishlist";
  final String VALUE = "10";

  @Test
  void getValueByKey_WhenValidConfig_ShouldReturnValue() {
    WishlistConfigurationDocument wishlistConfigurationDocument =
        new WishlistConfigurationDocument(KEY, VALUE);

    when(wishlistConfigurationSpringDataMongoDB.findByConfigKey(KEY))
        .thenReturn(Optional.of(wishlistConfigurationDocument));

    String maxProductsPerWishlist =
        wishlistConfigurationMongoDB
            .findValueByKey(KEY)
            .orElseThrow(() -> new NoSuchElementException("Configuration not found."));

    assertThat(Integer.parseInt(maxProductsPerWishlist)).isEqualTo(Integer.parseInt(VALUE));
  }

  @Test
  void getValueByKey_WhenConfigDoesNotExist_ShouldReturnEmptyOptional() {

    when(wishlistConfigurationSpringDataMongoDB.findByConfigKey(KEY)).thenReturn(Optional.empty());

    Optional<String> maxProductsPerWishlist = wishlistConfigurationMongoDB.findValueByKey(KEY);

    assertThat(maxProductsPerWishlist).isEmpty();
  }
}
