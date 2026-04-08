package br.com.clean.wishlist.infrastructure.config;

import br.com.clean.wishlist.application.wishlist.service.*;
import br.com.clean.wishlist.application.wishlist.service.core.*;
import br.com.clean.wishlist.domain.repository.WishlistConfigurationRepository;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishlistConfig {

  @Bean
  public WishlistService wishlistService(
      WishlistRepository wishlistRepository,
      WishlistConfigurationProvider wishlistConfigurationProvider) {
    return new DefaultWishlistService(
        wishlistRepository, wishlistConfigurationProvider.getMaxProductsPerWishlist());
  }
}
