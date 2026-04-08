package br.com.clean.wishlist.domain.repository;

import java.util.Optional;

public interface WishlistConfigurationRepository {
  Optional<String> findValueByKey(String key);
}
