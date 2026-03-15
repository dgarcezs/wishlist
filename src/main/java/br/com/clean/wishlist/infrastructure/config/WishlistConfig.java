package br.com.clean.wishlist.infrastructure.config;

import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationProviderMongoDB;
import br.com.clean.wishlist.adapters.output.persistence.mongodb.WishlistConfigurationSpringDataMongoDB;
import br.com.clean.wishlist.application.wishlist.service.*;
import br.com.clean.wishlist.application.wishlist.service.core.*;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class WishlistConfig {

  @Bean
  public WishlistService wishlistService(
      WishlistRepository wishlistRepository,
      WishlistConfigurationProvider wishlistConfigurationProvider) {
    return new DefaultWishlistService(
        wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());
  }

  @Bean
  @Primary
  public WishlistConfigurationProvider wishlistConfigurationProvider(
      WishlistConfigurationSpringDataMongoDB wishlistConfigurationSpringDataMongoDB,
      @Value("${wishlist.validation.max-products-per-wishlist}") int defaultMaxProductPerWishlist) {
    return new WishlistConfigurationProviderMongoDB(
        wishlistConfigurationSpringDataMongoDB, defaultMaxProductPerWishlist);
  }
}
