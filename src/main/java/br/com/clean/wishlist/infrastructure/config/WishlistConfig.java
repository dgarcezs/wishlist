package br.com.clean.wishlist.infrastructure.config;

import br.com.clean.wishlist.application.wishlist.usecase.WishlistUseCase;
import br.com.clean.wishlist.domain.repository.WishlistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishlistConfig {
  
  @Bean
  public WishlistUseCase wishlistUseCase(WishlistRepository wishlistRepository) {
    return new WishlistUseCase(wishlistRepository);
  }
}
