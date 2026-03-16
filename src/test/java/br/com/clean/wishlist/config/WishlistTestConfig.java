package br.com.clean.wishlist.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.infrastructure.config.WishlistConfigurationProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class WishlistTestConfig {

  @Bean
  @Primary
  public WishlistConfigurationProvider wishlistConfigurationProvider() {
    WishlistConfigurationProvider mock = mock(WishlistConfigurationProvider.class);
    when(mock.getMaxProductsPerWishlist()).thenReturn(3);
    return mock;
  }
}
