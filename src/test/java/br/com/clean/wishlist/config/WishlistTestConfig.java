package br.com.clean.wishlist.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.clean.wishlist.infrastructure.config.WishlistConfigurationProvider;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@ImportAutoConfiguration(
    exclude = {
      DataSourceAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class,
      MongoAutoConfiguration.class,
      DataMongoAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class
    })
public class WishlistTestConfig {

  @Bean
  @Primary
  public WishlistConfigurationProvider wishlistConfigurationProvider() {
    WishlistConfigurationProvider mock = mock(WishlistConfigurationProvider.class);
    when(mock.getMaxProductsPerWishlist()).thenReturn(3);
    return mock;
  }
}
